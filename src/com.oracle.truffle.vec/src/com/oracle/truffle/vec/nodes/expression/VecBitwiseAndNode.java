package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecBitwiseAndNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int bitwiseAnd(int left, int right) {
    return left & right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double bitwiseAnd(double left, double right) {
    return Double.longBitsToDouble(
        Double.doubleToRawLongBits(left) & Double.doubleToRawLongBits(right));
  }
}
