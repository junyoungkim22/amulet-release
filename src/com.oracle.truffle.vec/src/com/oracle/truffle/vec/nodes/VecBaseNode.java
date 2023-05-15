package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(description = "The abstract base node")
public abstract class VecBaseNode extends Node {
  public abstract Object executeGeneric(VirtualFrame frame);
}
