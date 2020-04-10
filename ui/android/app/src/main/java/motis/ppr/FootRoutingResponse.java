// automatically generated by the FlatBuffers compiler, do not modify

package motis.ppr;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class FootRoutingResponse extends Table {
  public static FootRoutingResponse getRootAsFootRoutingResponse(ByteBuffer _bb) { return getRootAsFootRoutingResponse(_bb, new FootRoutingResponse()); }
  public static FootRoutingResponse getRootAsFootRoutingResponse(ByteBuffer _bb, FootRoutingResponse obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public FootRoutingResponse __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public Routes routes(int j) { return routes(new Routes(), j); }
  public Routes routes(Routes obj, int j) { int o = __offset(4); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int routesLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }

  public static int createFootRoutingResponse(FlatBufferBuilder builder,
      int routesOffset) {
    builder.startObject(1);
    FootRoutingResponse.addRoutes(builder, routesOffset);
    return FootRoutingResponse.endFootRoutingResponse(builder);
  }

  public static void startFootRoutingResponse(FlatBufferBuilder builder) { builder.startObject(1); }
  public static void addRoutes(FlatBufferBuilder builder, int routesOffset) { builder.addOffset(0, routesOffset, 0); }
  public static int createRoutesVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startRoutesVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endFootRoutingResponse(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

