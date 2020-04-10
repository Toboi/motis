// automatically generated by the FlatBuffers compiler, do not modify

package motis;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class StatisticsEntry extends Table {
  public static StatisticsEntry getRootAsStatisticsEntry(ByteBuffer _bb) { return getRootAsStatisticsEntry(_bb, new StatisticsEntry()); }
  public static StatisticsEntry getRootAsStatisticsEntry(ByteBuffer _bb, StatisticsEntry obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public StatisticsEntry __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public long value() { int o = __offset(6); return o != 0 ? bb.getLong(o + bb_pos) : 0; }

  public static int createStatisticsEntry(FlatBufferBuilder builder,
      int nameOffset,
      long value) {
    builder.startObject(2);
    StatisticsEntry.addValue(builder, value);
    StatisticsEntry.addName(builder, nameOffset);
    return StatisticsEntry.endStatisticsEntry(builder);
  }

  public static void startStatisticsEntry(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addValue(FlatBufferBuilder builder, long value) { builder.addLong(1, value, 0); }
  public static int endStatisticsEntry(FlatBufferBuilder builder) {
    int o = builder.endObject();
    builder.required(o, 4);  // name
    return o;
  }
};

