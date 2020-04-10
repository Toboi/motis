// automatically generated by the FlatBuffers compiler, do not modify

package motis.routing;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class PeriodicMumoEdge extends Table {
  public static PeriodicMumoEdge getRootAsPeriodicMumoEdge(ByteBuffer _bb) { return getRootAsPeriodicMumoEdge(_bb, new PeriodicMumoEdge()); }
  public static PeriodicMumoEdge getRootAsPeriodicMumoEdge(ByteBuffer _bb, PeriodicMumoEdge obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public PeriodicMumoEdge __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public MumoEdge edge() { return edge(new MumoEdge()); }
  public MumoEdge edge(MumoEdge obj) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public motis.Interval interval() { return interval(new motis.Interval()); }
  public motis.Interval interval(motis.Interval obj) { int o = __offset(6); return o != 0 ? obj.__init(o + bb_pos, bb) : null; }

  public static void startPeriodicMumoEdge(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addEdge(FlatBufferBuilder builder, int edgeOffset) { builder.addOffset(0, edgeOffset, 0); }
  public static void addInterval(FlatBufferBuilder builder, int intervalOffset) { builder.addStruct(1, intervalOffset, 0); }
  public static int endPeriodicMumoEdge(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

