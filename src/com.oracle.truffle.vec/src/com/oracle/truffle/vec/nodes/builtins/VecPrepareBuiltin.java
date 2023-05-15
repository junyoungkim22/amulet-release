package com.oracle.truffle.vec.nodes.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.vec.nodes.VecBuiltinNode;
import com.oracle.truffle.vec.runtime.VecContext;
import com.oracle.truffle.vec.util.MemoryManager;

@NodeInfo(shortName = "prepare")
public abstract class VecPrepareBuiltin extends VecBuiltinNode {
  @Specialization
  public Object prepare(VirtualFrame frame) {
    Object[] args = frame.getArguments();
    String op = (String) args[0];
    if (op.contentEquals("aggregate")) {
      // Aggregate.
      assert args.length == 4;
      int[] input = (int[]) VecContext.getEnv().asHostObject(args[1]);
      int[] output = (int[]) VecContext.getEnv().asHostObject(args[2]);
      String inputFilePath = (String) args[3];
      MemoryManager.prepareAggregateData(inputFilePath, input, output);
    } else if (op.contentEquals("selectAST")) {
      // Select.
      if (args.length == 2) {
        int[] input0 = (int[]) VecContext.getEnv().asHostObject(args[1]);
        MemoryManager.prepareSelectData(input0);
        return true;
      }
      assert args.length == 5;
      int[] input0 = (int[]) VecContext.getEnv().asHostObject(args[1]);
      int[] input1 = (int[]) VecContext.getEnv().asHostObject(args[2]);
      int[] input2 = (int[]) VecContext.getEnv().asHostObject(args[3]);
      int[] input3 = (int[]) VecContext.getEnv().asHostObject(args[4]);
      MemoryManager.prepareSelectData(input0, input1, input2, input3);
    } else if (op.contentEquals("2dinit")) {
      int[][] input0 = (int[][]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
      return true;
    } else if (op.contentEquals("3dinit")) {
      int[][][] input0 = (int[][][]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
      return true;
    } else if (op.contentEquals("doubleinit")) {
      double[] input0 = (double[]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
    } else if (op.contentEquals("double2dinit")) {
      double[][] input0 = (double[][]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
      return true;
    } else if (op.contentEquals("double3dinit")) {
      double[][][] input0 = (double[][][]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
      return true;
    } else if (op.contentEquals("double4dinit")) {
      double[][][][] input0 = (double[][][][]) VecContext.getEnv().asHostObject(args[1]);
      MemoryManager.prepareSelectData(input0);
      return true;
    } else if (op.contentEquals("array range init")) {
      assert args.length == 3;
      int[] input = (int[]) VecContext.getEnv().asHostObject(args[1]);
      int range = (int) args[2];
      MemoryManager.prepareSelectData(input, range);
    } else {
      // Invalid operations.
      assert false;
    }
    return true;
  }
}
