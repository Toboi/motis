// automatically generated by the FlatBuffers compiler, do not modify

package motis.routing;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class MumoEdge extends Table {
  public static MumoEdge getRootAsMumoEdge(ByteBuffer _bb) { return getRootAsMumoEdge(_bb, new MumoEdge()); }
  public static MumoEdge getRootAsMumoEdge(ByteBuffer _bb, MumoEdge obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public MumoEdge __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String fromStationId() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer fromStationIdAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public String toStationId() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer toStationIdAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public int duration() { int o = __offset(8); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public int price() { int o = __offset(10); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public int accessibility() { int o = __offset(12); return o != 0 ? bb.getShort(o + bb_pos) & 0xFFFF : 0; }
  public int mumoId() { int o = __offset(14); return o != 0 ? bb.getInt(o + bb_pos) : 0; }

  public static int createMumoEdge(FlatBufferBuilder builder,
      int from_station_idOffset,
      int to_station_idOffset,
      int duration,
      int price,
      int accessibility,
      int mumo_id) {
    builder.startObject(6);
    MumoEdge.addMumoId(builder, mumo_id);
    MumoEdge.addToStationId(builder, to_station_idOffset);
    MumoEdge.addFromStationId(builder, from_station_idOffset);
    MumoEdge.addAccessibility(builder, accessibility);
    MumoEdge.addPrice(builder, price);
    MumoEdge.addDuration(builder, duration);
    return MumoEdge.endMumoEdge(builder);
  }

  public static void startMumoEdge(FlatBufferBuilder builder) { builder.startObject(6); }
  public static void addFromStationId(FlatBufferBuilder builder, int fromStationIdOffset) { builder.addOffset(0, fromStationIdOffset, 0); }
  public static void addToStationId(FlatBufferBuilder builder, int toStationIdOffset) { builder.addOffset(1, toStationIdOffset, 0); }
  public static void addDuration(FlatBufferBuilder builder, int duration) { builder.addShort(2, (short)duration, 0); }
  public static void addPrice(FlatBufferBuilder builder, int price) { builder.addShort(3, (short)price, 0); }
  public static void addAccessibility(FlatBufferBuilder builder, int accessibility) { builder.addShort(4, (short)accessibility, 0); }
  public static void addMumoId(FlatBufferBuilder builder, int mumoId) { builder.addInt(5, mumoId, 0); }
  public static int endMumoEdge(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

