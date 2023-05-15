package com.oracle.truffle.vec.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.vec.nodes.VecExpressionNode;
import java.util.ArrayList;

public final class VecAndNode extends VecExpressionNode {
  @Child private VecExpressionNode left;
  @Child private VecExpressionNode right;

  public VecAndNode(VecExpressionNode left, VecExpressionNode right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public Object executeGeneric(VirtualFrame frame) {
    try {
      return left.executeBoolean(frame) && right.executeBoolean(frame);
    } catch (UnexpectedResultException e) {
      System.out.println("Error while executing AND condition");
      return null;
    }
  }

  public ArrayList<VecExpressionNode> getConditionList() {
    ArrayList<VecExpressionNode> andConditions = new ArrayList<VecExpressionNode>();
    if (this.left instanceof VecAndNode) {
      VecAndNode leftAnd = (VecAndNode) this.left;
      ArrayList<VecExpressionNode> leftConditions = leftAnd.getConditionList();
      andConditions.addAll(leftConditions);
    } else {
      andConditions.add(this.left);
    }

    if (this.right instanceof VecAndNode) {
      VecAndNode rightAnd = (VecAndNode) this.right;
      ArrayList<VecExpressionNode> rightConditions = rightAnd.getConditionList();
      andConditions.addAll(rightConditions);
    } else {
      andConditions.add(this.right);
    }
    return andConditions;
  }
}
