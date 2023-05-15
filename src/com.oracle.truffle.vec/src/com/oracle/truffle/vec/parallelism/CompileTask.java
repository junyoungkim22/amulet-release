package com.oracle.truffle.vec.parallelism;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.vec.execution.Compilation;
import com.oracle.truffle.vec.execution.PhaseExecutionInfo;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public final class CompileTask implements Callable<Integer> {
  private CallTarget callTarget;
  private Method compileMethod;
  private final PhaseExecutionInfo phaseExecutionInfo;
  private int planIndex;
  private long warmupSize;
  private String id;

  public CompileTask(
      CallTarget callTarget,
      Method compileMethod,
      PhaseExecutionInfo phaseExecutionInfo,
      int planIndex,
      long warmupSize,
      String id) {
    this.callTarget = callTarget;
    this.compileMethod = compileMethod;
    this.phaseExecutionInfo = phaseExecutionInfo;
    this.planIndex = planIndex;
    this.warmupSize = warmupSize;
    this.id = id;
  }

  @Override
  public Integer call() throws Exception {
    System.out.println(this.id + " START");
    Compilation.compileSinglePlan(
        callTarget, compileMethod, phaseExecutionInfo, planIndex, warmupSize);
    return 1;
  }
}
