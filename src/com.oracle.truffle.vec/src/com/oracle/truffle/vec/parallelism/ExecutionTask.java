package com.oracle.truffle.vec.parallelism;

import com.oracle.truffle.vec.execution.Execution;
import com.oracle.truffle.vec.execution.PhaseExecutionInfo;
import java.util.concurrent.Callable;

public final class ExecutionTask implements Callable<Integer> {
  private final PhaseExecutionInfo phaseExecutionInfo;
  private String id;

  public ExecutionTask(PhaseExecutionInfo phaseExecutionInfo, String id) {
    this.phaseExecutionInfo = phaseExecutionInfo;
    this.id = id;
  }

  @Override
  public Integer call() throws Exception {
    final long taskStartTime = System.nanoTime();

    Execution.adaptiveExecution(phaseExecutionInfo);
    final int executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));
    if (executionMode == 2) {
      System.out.println(this.id + " time: " + (System.nanoTime() - taskStartTime));
    }
    return 1;
  }
}
