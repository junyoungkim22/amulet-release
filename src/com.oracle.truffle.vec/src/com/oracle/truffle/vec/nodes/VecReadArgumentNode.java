package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class VecReadArgumentNode extends VecBaseNode {
  private final int index;

  public VecReadArgumentNode(int index) {
    this.index = index;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    Object[] args = frame.getArguments();
    assert index < args.length;
    return args[index];
  }
}
