#include "motis/paxmon/compact_journey_util.h"

#include <algorithm>
#include <iterator>

#include "utl/verify.h"

#include "motis/core/access/realtime_access.h"
#include "motis/core/access/trip_iterator.h"

namespace motis::paxmon {

compact_journey get_prefix(schedule const& sched, compact_journey const& cj,
                           passenger_localization const& loc) {
  auto prefix = compact_journey{};

  for (auto const& leg : cj.legs_) {
    if (leg.enter_time_ >= loc.schedule_arrival_time_) {
      break;
    }
    auto& new_leg = prefix.legs_.emplace_back(leg);
    if (new_leg.exit_time_ > loc.schedule_arrival_time_) {
      auto const sections = access::sections(new_leg.trip_);
      auto const exit_section_it = std::find_if(
          begin(sections), end(sections), [&](access::trip_section const& sec) {
            return sec.to_station_id() == loc.at_station_->index_ &&
                   get_schedule_time(sched, sec.ev_key_to()) ==
                       loc.schedule_arrival_time_;
          });
      utl::verify(exit_section_it != end(sections),
                  "compact_journey_prefix: exit section not found");
      auto const exit_section = *exit_section_it;
      new_leg.exit_station_id_ = exit_section.to_station_id();
      new_leg.exit_time_ = get_schedule_time(sched, exit_section.ev_key_to());
      break;
    }
  }

  return prefix;
}

compact_journey merge_journeys(compact_journey const& prefix,
                               compact_journey const& suffix) {
  if (prefix.legs_.empty()) {
    return suffix;
  } else if (suffix.legs_.empty()) {
    return prefix;
  }

  auto merged = prefix;
  auto const& last_prefix_leg = prefix.legs_.back();
  auto const& first_suffix_leg = suffix.legs_.front();
  if (last_prefix_leg.trip_ == first_suffix_leg.trip_) {
    auto& merged_leg = merged.legs_.back();
    merged_leg.exit_station_id_ = first_suffix_leg.exit_station_id_;
    merged_leg.exit_time_ = first_suffix_leg.exit_time_;
    std::copy(std::next(begin(suffix.legs_)), end(suffix.legs_),
              std::back_inserter(merged.legs_));
  } else {
    std::copy(begin(suffix.legs_), end(suffix.legs_),
              std::back_inserter(merged.legs_));
  }
  return merged;
}

}  // namespace motis::paxmon