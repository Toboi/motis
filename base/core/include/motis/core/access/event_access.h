#pragma once

#include "motis/core/schedule/event.h"

namespace motis {

template <typename Fn>
void for_each_departure(ev_key const& arr, Fn fn) {
  for (auto const& e : arr.route_edge_->to_->edges_) {
    if (!e.empty()) {
      fn(ev_key{&e, arr.lcon_idx_, event_type::DEP});
    }
  }
}

template <typename Fn>
void for_each_arrival(ev_key const& dep, Fn fn) {
  for (auto const& e : dep.route_edge_->from_->incoming_edges_) {
    if (!e->empty()) {
      fn(ev_key{trip::route_edge{e}, dep.lcon_idx_, event_type::ARR});
    }
  }
}

}  // namespace motis
