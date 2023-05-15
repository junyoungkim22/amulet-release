package com.oracle.truffle.vec.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class VecBinaryNode extends VecExpressionNode {}
