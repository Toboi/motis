#pragma once

#include <map>

#include "gtest/gtest.h"

#include "motis/core/schedule/time.h"
#include "motis/test/motis_instance_test.h"
#include "motis/test/schedule/gtfs_minimal_swiss.h"

using motis::test::schedule::gtfs_minimal_swiss::dataset_opt;

namespace motis::ris::gtfsrt {

struct gtfsrt_itest : public motis::test::motis_instance_test {
  gtfsrt_itest(std::string const& test_sample)
      : motis::test::motis_instance_test{
            dataset_opt,
            {"ris", "rt"},
            {test_sample, "--ris.instant_forward=true",
             "--ris.gtfsrt.is_addition_skip_allowed=true"}} {}

  struct stop_times {
    motis::time arr_, dep_;
  };

  using trip_event_info = std::map<std::string /* station id */, stop_times>;

  inline trip_event_info get_trip_event_info(motis::schedule const& sched,
                                             trip const* trp) {
    trip_event_info ev;
    for (auto const& trip_e : *trp->edges_) {
      auto const e = trip_e.get_edge();
      auto& dep =
          ev[sched.stations_.at(e->from_->get_station()->id_)->eva_nr_.str()];
      auto& arr =
          ev[sched.stations_.at(e->to_->get_station()->id_)->eva_nr_.str()];
      dep.dep_ = e->m_.route_edge_.conns_[trp->lcon_idx_].d_time_;
      arr.arr_ = e->m_.route_edge_.conns_[trp->lcon_idx_].a_time_;
    }
    return ev;
  }

  inline motis::time motis_time(std::time_t const time) {
    return unix_to_motistime(sched().schedule_begin_, time);
  }
};

}  // namespace motis::ris::gtfsrt