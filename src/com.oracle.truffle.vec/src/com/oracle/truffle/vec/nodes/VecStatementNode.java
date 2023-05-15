package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;

public abstract class VecStatementNode extends Node {
  public abstract void executeVoid(VirtualFrame frame);
}
