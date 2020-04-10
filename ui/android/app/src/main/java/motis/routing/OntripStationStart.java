// automatically generated by the FlatBuffers compiler, do not modify

package motis.routing;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class OntripStationStart extends Table {
  public static OntripStationStart getRootAsOntripStationStart(ByteBuffer _bb) { return getRootAsOntripStationStart(_bb, new OntripStationStart()); }
  public static OntripStationStart getRootAsOntripStationStart(ByteBuffer _bb, OntripStationStart obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public OntripStationStart __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public InputStation station() { return station(new InputStation()); }
  public InputStation station(InputStation obj) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(o + bb_pos), bb) : null; }
  public long departureTime() { int o = __offset(6); return o != 0 ? bb.getLong(o + bb_pos) : 0; }

  public static int createOntripStationStart(FlatBufferBuilder builder,
      int stationOffset,
      long departure_time) {
    builder.startObject(2);
    OntripStationStart.addDepartureTime(builder, departure_time);
    OntripStationStart.addStation(builder, stationOffset);
    return OntripStationStart.endOntripStationStart(builder);
  }

  public static void startOntripStationStart(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addStation(FlatBufferBuilder builder, int stationOffset) { builder.addOffset(0, stationOffset, 0); }
  public static void addDepartureTime(FlatBufferBuilder builder, long departureTime) { builder.addLong(1, departureTime, 0); }
  public static int endOntripStationStart(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

