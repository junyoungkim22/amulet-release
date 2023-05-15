package com.oracle.truffle.vec.nodes.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.vec.nodes.VecBuiltinNode;
import com.oracle.truffle.vec.runtime.VecContext;

@NodeInfo(shortName = "aggregate")
public abstract class VecAggregateBuiltin extends VecBuiltinNode {
  @Specialization
  public Object aggregate(VirtualFrame frame) {
    Object[] args = frame.getArguments();
    int[] input = (int[]) VecContext.getEnv().asHostObject(args[0]);
    int[] output = (int[]) VecContext.getEnv().asHostObject(args[1]);

    // TODO: For now assume array sizes are multiples of 16 for SIMD processing.
    assert input.length % 16 == 0;

    for (int i = 0; i < input.length; i += 16) {
      // TODO: Check CompilerDirectives.inInterpreter().
      // CompilerDirectives.vecAggregate(i, input, output);
    }

    return true;
  }
}

// // SIMD: compilerDirectives
// 1. vecAggregate CompilerDirectives (Truffle)

// 1.5 replacement of vecAggregate(Truffle)
// 2. AggregateNode in backend
// 3. AggregateNode -> AggregateOp (LIR language)
// 4. AggregateOp (assembly code generation)
// 5. Assembler (add AVX-512 instructions needed)

// 1. scalar vs. SIMD
//     -- e.g., aggregate
//     -- for (i) { counter[id[i]] += 1; }
// 2. plans for conditions
//     -- ASTs
//     -- SIMD plans
