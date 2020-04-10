// automatically generated by the FlatBuffers compiler, do not modify

package motis;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class EventInfo extends Table {
  public static EventInfo getRootAsEventInfo(ByteBuffer _bb) { return getRootAsEventInfo(_bb, new EventInfo()); }
  public static EventInfo getRootAsEventInfo(ByteBuffer _bb, EventInfo obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public EventInfo __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public long time() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public long scheduleTime() { int o = __offset(6); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public String track() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer trackAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public String scheduleTrack() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer scheduleTrackAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public boolean valid() { int o = __offset(12); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public byte reason() { int o = __offset(14); return o != 0 ? bb.get(o + bb_pos) : 0; }

  public static int createEventInfo(FlatBufferBuilder builder,
      long time,
      long schedule_time,
      int trackOffset,
      int schedule_trackOffset,
      boolean valid,
      byte reason) {
    builder.startObject(6);
    EventInfo.addScheduleTime(builder, schedule_time);
    EventInfo.addTime(builder, time);
    EventInfo.addScheduleTrack(builder, schedule_trackOffset);
    EventInfo.addTrack(builder, trackOffset);
    EventInfo.addReason(builder, reason);
    EventInfo.addValid(builder, valid);
    return EventInfo.endEventInfo(builder);
  }

  public static void startEventInfo(FlatBufferBuilder builder) { builder.startObject(6); }
  public static void addTime(FlatBufferBuilder builder, long time) { builder.addLong(0, time, 0); }
  public static void addScheduleTime(FlatBufferBuilder builder, long scheduleTime) { builder.addLong(1, scheduleTime, 0); }
  public static void addTrack(FlatBufferBuilder builder, int trackOffset) { builder.addOffset(2, trackOffset, 0); }
  public static void addScheduleTrack(FlatBufferBuilder builder, int scheduleTrackOffset) { builder.addOffset(3, scheduleTrackOffset, 0); }
  public static void addValid(FlatBufferBuilder builder, boolean valid) { builder.addBoolean(4, valid, false); }
  public static void addReason(FlatBufferBuilder builder, byte reason) { builder.addByte(5, reason, 0); }
  public static int endEventInfo(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

