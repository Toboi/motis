// automatically generated by the FlatBuffers compiler, do not modify

package motis;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Trip extends Table {
  public static Trip getRootAsTrip(ByteBuffer _bb) { return getRootAsTrip(_bb, new Trip()); }
  public static Trip getRootAsTrip(ByteBuffer _bb, Trip obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Trip __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public Range range() { return range(new Range()); }
  public Range range(Range obj) { int o = __offset(4); return o != 0 ? obj.__init(o + bb_pos, bb) : null; }
  public motis.TripId id() { return id(new motis.TripId()); }
  public motis.TripId id(motis.TripId obj) { int o = __offset(6); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public String debug() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer debugAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }

  public static void startTrip(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addRange(FlatBufferBuilder builder, int rangeOffset) { builder.addStruct(0, rangeOffset, 0); }
  public static void addId(FlatBufferBuilder builder, int idOffset) { builder.addOffset(1, idOffset, 0); }
  public static void addDebug(FlatBufferBuilder builder, int debugOffset) { builder.addOffset(2, debugOffset, 0); }
  public static int endTrip(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

