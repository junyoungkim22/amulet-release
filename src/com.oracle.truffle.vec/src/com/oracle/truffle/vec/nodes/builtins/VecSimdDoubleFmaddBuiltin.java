package com.oracle.truffle.vec.nodes.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.vec.nodes.VecBuiltinNode;
import com.oracle.truffle.vec.runtime.VecContext;

@NodeInfo(shortName = "simddoublefmadd")
public abstract class VecSimdDoubleFmaddBuiltin extends VecBuiltinNode {
  @Specialization
  public Object simdDoubleMult(VirtualFrame frame) {
    Object[] args = frame.getArguments();
    double multVal = (double) args[0];
    double[] input = (double[]) VecContext.getEnv().asHostObject(args[1]);
    double[] output = (double[]) VecContext.getEnv().asHostObject(args[2]);

    // double multVal = (double) multValArg;

    // TODO: For now assume array sizes are multiples of 16 for SIMD processing.
    assert input.length % 8 == 0;
    assert input.length == output.length;

    CompilerDirectives.simdDoubleFmadd(input.length, multVal, input, output);

    return true;
  }
}
