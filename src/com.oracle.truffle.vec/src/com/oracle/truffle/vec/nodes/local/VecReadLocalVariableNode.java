package com.oracle.truffle.vec.nodes.local;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeField;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameUtil;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.vec.nodes.VecExpressionNode;

@NodeField(name = "slot", type = FrameSlot.class)
public abstract class VecReadLocalVariableNode extends VecExpressionNode {
  protected abstract FrameSlot getSlot();

  @Specialization(guards = "frame.isInt(getSlot())")
  protected int readInt(VirtualFrame frame) {
    return FrameUtil.getIntSafe(frame, getSlot());
  }

  @Specialization(guards = "frame.isBoolean(getSlot())")
  protected boolean readBoolean(VirtualFrame frame) {
    return FrameUtil.getBooleanSafe(frame, getSlot());
  }

  @Specialization(guards = "frame.isDouble(getSlot())")
  protected double readDouble(VirtualFrame frame) {
    return FrameUtil.getDoubleSafe(frame, getSlot());
  }

  @Specialization(replaces = {"readInt", "readBoolean", "readDouble"})
  protected Object readObject(VirtualFrame frame) {
    if (!frame.isObject(getSlot())) {
      CompilerDirectives.transferToInterpreter();
      Object result = frame.getValue(getSlot());
      frame.setObject(getSlot(), result);
      return result;
    }
    return FrameUtil.getObjectSafe(frame, getSlot());
  }
}
