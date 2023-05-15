package com.oracle.truffle.vec.runtime;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage.Env;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.vec.Vec;
import com.oracle.truffle.vec.nodes.VecBaseNode;
import com.oracle.truffle.vec.nodes.VecBuiltinNode;
import com.oracle.truffle.vec.nodes.VecReadArgumentNode;
import com.oracle.truffle.vec.nodes.VecRootNode;
import com.oracle.truffle.vec.nodes.builtins.VecAggregateBuiltinFactory;
import com.oracle.truffle.vec.nodes.builtins.VecPrepareBuiltinFactory;
import com.oracle.truffle.vec.nodes.builtins.VecSelectASTBuiltinFactory;
import com.oracle.truffle.vec.nodes.builtins.VecSimdDoubleFmaddBuiltinFactory;

public final class VecContext {
  private final Vec language;
  private static Env env;

  public VecContext(Vec language, Env env) {
    this.language = language;
    VecContext.setEnv(env);
    installBuiltins();
  }

  private void installBuiltins() {
    installBuiltin(VecPrepareBuiltinFactory.getInstance());
    installBuiltin(VecAggregateBuiltinFactory.getInstance());
    installBuiltin(VecSelectASTBuiltinFactory.getInstance());
    installBuiltin(VecSimdDoubleFmaddBuiltinFactory.getInstance());
  }

  private void installBuiltin(NodeFactory<? extends VecBuiltinNode> factory) {
    int argumentCount = factory.getExecutionSignature().size();
    VecBaseNode[] argumentNodes = new VecBaseNode[argumentCount];
    for (int i = 0; i < argumentCount; i++) {
      argumentNodes[i] = new VecReadArgumentNode(i);
    }
    VecBuiltinNode builtin = factory.createNode((Object) argumentNodes);

    VecRootNode rootNode = new VecRootNode(language, builtin);
    VecFunction function = new VecFunction(Truffle.getRuntime().createCallTarget(rootNode));
    String name = builtin.getClass().getAnnotation(NodeInfo.class).shortName();
    getEnv().exportSymbol(name, function);
  }

  public static Env getEnv() {
    return env;
  }

  public static void setEnv(Env env) {
    VecContext.env = env;
  }
}
