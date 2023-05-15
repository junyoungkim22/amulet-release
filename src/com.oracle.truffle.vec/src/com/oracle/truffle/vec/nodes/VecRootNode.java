package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.vec.Vec;

@NodeInfo(language = "Vec")
public class VecRootNode extends RootNode {
  @Child private VecBaseNode bodyNode;

  public VecRootNode() {
    this(null, null);
  }

  public VecRootNode(Vec language, VecBaseNode bodyNode) {
    super(language);
    this.bodyNode = bodyNode;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    if (bodyNode == null) {
      return null;
    } else {
      return bodyNode.executeGeneric(frame);
    }
  }
}
