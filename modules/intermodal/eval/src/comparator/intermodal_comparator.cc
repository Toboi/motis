#include <cstring>
#include <ctime>
#include <algorithm>
#include <fstream>
#include <iostream>
#include <memory>
#include <random>
#include <set>
#include <sstream>
#include <tuple>
#include <unordered_map>
#include <unordered_set>

#include "boost/filesystem.hpp"
#include "boost/program_options.hpp"

#include "utl/to_vec.h"

#include "geo/latlng.h"
#include "geo/webmercator.h"

#include "motis/core/schedule/time.h"
#include "motis/core/access/time_access.h"
#include "motis/core/journey/check_journey.h"
#include "motis/core/journey/journey.h"
#include "motis/core/journey/message_to_journeys.h"
#include "motis/module/message.h"
#include "motis/bootstrap/dataset_settings.h"
#include "motis/bootstrap/motis_instance.h"

namespace fs = boost::filesystem;
namespace po = boost::program_options;
using namespace motis;
using namespace motis::bootstrap;
using namespace motis::module;
using namespace motis::routing;
using namespace motis::intermodal;
using namespace flatbuffers;

namespace motis::intermodal::eval {

constexpr auto const TIME_FORMAT = "%d.%m. %H:%M";

std::string format_time(std::time_t t, bool local_time) {
  std::ostringstream out;
  out << std::put_time(local_time ? std::localtime(&t) : std::gmtime(&t),
                       TIME_FORMAT);
  return out.str();
}

inline std::time_t departure_time(journey const& j) {
  return j.stops_.front().departure_.timestamp_;
}

inline std::time_t arrival_time(journey const& j) {
  return j.stops_.back().arrival_.timestamp_;
}

bool has_connection(std::vector<journey> const& connections,
                    journey const& ref_con) {
  return std::any_of(begin(connections), end(connections),
                     [&](auto const& con) {
                       return con.duration_ == ref_con.duration_ &&
                              con.transfers_ == ref_con.transfers_ &&
                              con.accessibility_ == ref_con.accessibility_ &&
                              departure_time(con) == departure_time(ref_con) &&
                              arrival_time(con) == arrival_time(ref_con);
                     });
}

std::string file_identifier(std::string filename) {
  return fs::path{filename}.stem().string();
}

bool check(int id, std::vector<msg_ptr> const& msgs,
           std::vector<std::string> const& files, std::vector<int>& file_errors,
           fs::path const& fail_path, bool local) {
  assert(msgs.size() == files.size());
  assert(msgs.size() > 1);
  auto const file_count = files.size();
  auto match = true;
  std::unordered_set<int> failed_files;

  auto const res = utl::to_vec(
      msgs, [](auto const& m) { return motis_content(RoutingResponse, m); });

  auto const fail = [&](auto const file_idx) -> std::ostream& {
    if (match) {
      std::cout << "\nMismatch at query id " << id << ":" << std::endl;
      match = false;
    }
    failed_files.insert(file_idx);
    std::cout << "  " << files[file_idx] << ": ";
    return std::cout;
  };

  std::ostringstream journey_errors;
  auto const report_journey_error = [&](bool) -> std::ostream& {
    return journey_errors;
  };

  auto const check_journeys = [&](auto const file_idx,
                                  std::vector<journey> const& journeys) {
    for (auto const& j : journeys) {
      if (!check_journey(j, report_journey_error)) {
        fail(file_idx) << "Broken journey" << std::endl;
      }
    }
  };

  auto const ref = message_to_journeys(res[0]);
  auto const ref_cons = ref.size();

  check_journeys(0, ref);

  for (auto i = 1ul; i < file_count; ++i) {
    auto const r = message_to_journeys(res[i]);
    if (r.size() != ref_cons) {
      fail(i) << "Expected " << ref_cons << " connections, has " << r.size()
              << std::endl;
    }

    for (auto const& ref_con : ref) {
      if (!has_connection(r, ref_con)) {
        fail(i) << "Connection with duration=" << std::setw(4)
                << ref_con.duration_ << ", transfers=" << ref_con.transfers_
                << ", accessibility=" << std::setw(2) << ref_con.accessibility_
                << ", start=" << format_time(departure_time(ref_con), local)
                << ", end=" << format_time(arrival_time(ref_con), local)
                << " missing" << std::endl;
      }
    }

    for (auto const& con : r) {
      if (!has_connection(ref, con)) {
        fail(i) << "Connection with duration=" << std::setw(4) << con.duration_
                << ", transfers=" << con.transfers_
                << ", accessibility=" << std::setw(2) << con.accessibility_
                << ", start=" << format_time(departure_time(con), local)
                << ", end=" << format_time(arrival_time(con), local)
                << " unexpected" << std::endl;
      }
    }

    check_journeys(i, r);
  }

  auto const write_file = [&](auto const file_idx) {
    std::ofstream out{
        (fail_path / fs::path{std::to_string(id) + "_" +
                              file_identifier(files[file_idx]) + ".json"})
            .string()};
    out << msgs[file_idx]->to_json() << std::endl;
  };

  if (!match) {
    write_file(0);
    for (auto const& file_idx : failed_files) {
      file_errors[file_idx]++;
      if (file_idx != 0) {
        write_file(file_idx);
      }
    }
  }

  return match;
}

}  // namespace motis::intermodal::eval

int main(int argc, char** argv) {
  using namespace motis::intermodal::eval;

  bool help = false;
  bool utc = false;
  bool local = false;
  std::vector<std::string> filenames;
  std::string fail_dir;
  po::options_description desc("Intermodal Comparator");
  // clang-format off
  desc.add_options()
      ("help,h", po::bool_switch(&help), "show help")
      ("utc,u", po::bool_switch(&utc), "print timestamps in UTC")
      ("local,l", po::bool_switch(&local), "print timestamps in local time")
      ("i", po::value<std::vector<std::string>>(&filenames), "input file")
      ("fail", po::value<std::string>(&fail_dir)->default_value("fail"),
          "output directory for different responses")
      ;
  // clang-format on
  po::positional_options_description pod;
  pod.add("i", -1);
  po::variables_map vm;
  po::store(
      po::command_line_parser(argc, argv).options(desc).positional(pod).run(),
      vm);
  po::notify(vm);

  if (help || filenames.size() < 2) {
    std::cout << desc << std::endl;
    return 0;
  }

  if (utc) {
    local = false;
  }

  auto const fail_path = fs::path{fail_dir};
  fs::create_directories(fail_path);

  auto in_files = utl::to_vec(filenames, [](std::string const& filename) {
    return std::ifstream{filename};
  });
  auto const file_count = in_files.size();
  std::vector<std::unordered_map<int, msg_ptr>> queued_msgs(file_count);
  std::vector<int> file_errors(file_count);

  auto msg_count = 0;
  auto errors = 0;
  auto done = false;
  std::unordered_set<int> read;
  read.reserve(file_count);
  while (!done) {
    read.clear();
    done = true;
    for (auto i = 0ul; i < file_count; ++i) {
      auto& in = in_files[i];
      if (in.peek() != EOF && !in.eof()) {
        std::string line;
        std::getline(in, line);
        auto const m = make_msg(line);
        if (m->get()->content_type() == MsgContent_RoutingResponse) {
          queued_msgs[i][m->id()] = m;
          read.insert(m->id());
          done = false;
        }
      }
    }
    for (auto const& id : read) {
      std::vector<msg_ptr> msgs;
      msgs.reserve(file_count);
      for (auto i = 0ul; i < file_count; ++i) {
        auto const& q = queued_msgs[i];
        auto it = q.find(id);
        if (it == end(q)) {
          break;
        } else {
          msgs.emplace_back(it->second);
        }
      }
      if (msgs.size() == file_count) {
        ++msg_count;
        auto success =
            check(id, msgs, filenames, file_errors, fail_path, local);
        if (!success) {
          ++errors;
        }
        for (auto& q : queued_msgs) {
          q.erase(id);
        }
      }
    }
  }

  std::cout << "\n\n\n";

  std::set<int> unmatched_msgs;
  for (auto const& q : queued_msgs) {
    for (auto const& m : q) {
      unmatched_msgs.insert(m.first);
    }
  }
  if (!unmatched_msgs.empty()) {
    std::cout << unmatched_msgs.size()
              << " messages are missing in some results:" << std::endl;
    for (auto const& id : unmatched_msgs) {
      std::cout << " query id " << id << " missing in:";
      for (auto file_idx = 0ul; file_idx < file_count; ++file_idx) {
        const auto& q = queued_msgs[file_idx];
        if (q.find(id) == end(q)) {
          std::cout << " " << filenames[file_idx];
        }
      }
      std::cout << std::endl;
    }
    std::cout << "\n\n\n";
  }

  std::cout << "Queries where responses don't match: " << errors << "/"
            << msg_count << std::endl;
  std::cout << "Mismatches by file:" << std::endl;
  std::cout << "  " << filenames[0] << ": Used as reference" << std::endl;
  for (auto i = 1ul; i < filenames.size(); ++i) {
    std::cout << "  " << filenames[i] << ": " << file_errors[i] << std::endl;
  }

  if (errors > 0) {
    std::cout << "\nResponses that don't match written to: "
              << fail_path.string() << std::endl;
  }

  return (errors == 0 && unmatched_msgs.empty()) ? 0 : 1;
}
