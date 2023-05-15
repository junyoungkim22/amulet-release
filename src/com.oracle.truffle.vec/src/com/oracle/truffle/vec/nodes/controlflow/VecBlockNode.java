package com.oracle.truffle.vec.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.vec.nodes.VecStatementNode;

public final class VecBlockNode extends VecStatementNode
    implements BlockNode.ElementExecutor<VecStatementNode> {
  @Child private BlockNode<VecStatementNode> block;

  public VecBlockNode(VecStatementNode[] bodyNodes) {
    this.block = bodyNodes.length > 0 ? BlockNode.create(bodyNodes, this) : null;
  }

  @Override
  public void executeVoid(VirtualFrame frame) {
    if (block != null) {
      this.block.executeVoid(frame, BlockNode.NO_ARGUMENT);
    }
  }

  public BlockNode<VecStatementNode> getBlockNode() {
    return this.block;
  }

  public void executeVoid(VirtualFrame frame, VecStatementNode node, int index, int argument) {
    node.executeVoid(frame);
  }
}
