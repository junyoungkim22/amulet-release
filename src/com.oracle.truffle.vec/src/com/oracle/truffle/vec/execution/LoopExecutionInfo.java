package com.oracle.truffle.vec.execution;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.DirectCallNode;

public class LoopExecutionInfo {
  public DirectCallNode[] plans;
  public CallTarget[] callTargets;
  public int[] loopStarts;
  public int[] loopEnds;
  public int[] incrementSizes;
  public String[] compareOpStrings;
  public long[] iterationsPerLoop;
  public int executionMode;

  public LoopExecutionInfo(
      int[] loopStarts, int[] loopEnds, int[] incrementSizes, String[] compareOpStrings) {
    this.loopStarts = loopStarts;
    this.loopEnds = loopEnds;
    this.incrementSizes = incrementSizes;
    this.compareOpStrings = compareOpStrings;
    this.executionMode = Integer.valueOf(System.getProperty("executionMode", "1"));
    assert (this.loopStarts.length == this.loopEnds.length);
  }

  public LoopExecutionInfo(
      DirectCallNode[] plans,
      int[] loopStarts,
      int[] loopEnds,
      int[] incrementSizes,
      String[] compareOpStrings) {
    this.plans = plans;
    this.loopStarts = loopStarts;
    this.loopEnds = loopEnds;
    this.incrementSizes = incrementSizes;
    this.compareOpStrings = compareOpStrings;

    assert (this.loopStarts.length == this.loopEnds.length);
  }
}
