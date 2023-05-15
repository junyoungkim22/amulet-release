package com.oracle.truffle.vec.nodes.util;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import com.oracle.truffle.vec.nodes.VecTypes;

@TypeSystemReference(VecTypes.class)
@NodeChild
public abstract class VecUnboxNode extends VecExpressionNode {

  @Specialization
  protected static int fromString(int value) {
    return value;
  }

  @Specialization
  protected static boolean fromBoolean(boolean value) {
    return value;
  }

  @Specialization
  protected static double fromDouble(double value) {
    return value;
  }
}
