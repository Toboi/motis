#pragma once

#include "ctx/ctx.h"

#include "motis/module/ctx_data.h"

namespace motis::module {

template <typename Fn>
auto spawn_job(Fn&& fn) {
  auto const op = ctx::current_op<ctx_data>();
  auto id = ctx::op_id(CTX_LOCATION);
  id.parent_index = op->id_.index;
  return op->sched_.post_work(op->data_, std::forward<Fn>(fn), id);
}

template <typename Fn>
auto spawn_job_void(Fn&& fn) {
  auto const op = ctx::current_op<ctx_data>();
  auto id = ctx::op_id(CTX_LOCATION);
  id.parent_index = op->id_.index;
  return op->sched_.post_void_work(op->data_, std::forward<Fn>(fn), id);
}

}  // namespace motis::module
