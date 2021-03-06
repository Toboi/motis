// automatically generated by the FlatBuffers compiler, do not modify

package motis;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class MoveWrapper extends Table {
  public static MoveWrapper getRootAsMoveWrapper(ByteBuffer _bb) { return getRootAsMoveWrapper(_bb, new MoveWrapper()); }
  public static MoveWrapper getRootAsMoveWrapper(ByteBuffer _bb, MoveWrapper obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public MoveWrapper __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public byte moveType() { int o = __offset(4); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public Table move(Table obj) { int o = __offset(6); return o != 0 ? __union(obj, o) : null; }

  public static int createMoveWrapper(FlatBufferBuilder builder,
      byte move_type,
      int moveOffset) {
    builder.startObject(2);
    MoveWrapper.addMove(builder, moveOffset);
    MoveWrapper.addMoveType(builder, move_type);
    return MoveWrapper.endMoveWrapper(builder);
  }

  public static void startMoveWrapper(FlatBufferBuilder builder) { builder.startObject(2); }
  public static void addMoveType(FlatBufferBuilder builder, byte moveType) { builder.addByte(0, moveType, 0); }
  public static void addMove(FlatBufferBuilder builder, int moveOffset) { builder.addOffset(1, moveOffset, 0); }
  public static int endMoveWrapper(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

