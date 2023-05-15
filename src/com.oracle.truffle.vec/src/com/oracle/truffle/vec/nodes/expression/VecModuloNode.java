package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.vec.nodes.VecBinaryNode;

public abstract class VecModuloNode extends VecBinaryNode {

  @Specialization(rewriteOn = ArithmeticException.class)
  protected int modulo(int left, int right) {
    return left % right;
  }

  @Specialization(rewriteOn = ArithmeticException.class)
  protected double modulo(double left, double right) {
    return left % right;
  }
}
