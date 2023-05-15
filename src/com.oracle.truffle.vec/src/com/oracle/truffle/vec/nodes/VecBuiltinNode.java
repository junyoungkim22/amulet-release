package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.dsl.GenerateNodeFactory;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.vec.runtime.VecException;

@NodeChild(value = "arguments", type = VecBaseNode[].class)
@GenerateNodeFactory
public abstract class VecBuiltinNode extends VecBaseNode {
  @Override
  public final Object executeGeneric(VirtualFrame frame) {
    try {
      return execute(frame);
    } catch (UnsupportedSpecializationException e) {
      throw new VecException(e.getMessage(), this);
    }
  }

  protected abstract Object execute(VirtualFrame frame);
}
