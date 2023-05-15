package com.oracle.truffle.vec;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.vec.nodes.VecRootNode;
import com.oracle.truffle.vec.runtime.VecContext;
import com.oracle.truffle.vec.runtime.VecObject;

@TruffleLanguage.Registration(name = "Vec", id = Vec.ID)
public final class Vec extends TruffleLanguage<VecContext> {
  public static final String ID = "vec";

  @Override
  protected VecContext createContext(Env env) {
    return new VecContext(this, env);
  }

  @ExportLibrary(InteropLibrary.class)
  public static final class RootFunction implements TruffleObject {
    public final CallTarget code;

    protected RootFunction(CallTarget code) {
      this.code = code;
    }

    @ExportMessage
    public boolean isExecutable() {
      return true;
    }

    @ExportMessage
    public Object execute(Object[] arguments) {
      return code.call(arguments);
    }
  }

  @Override
  protected CallTarget parse(ParsingRequest request) throws Exception {
    return Truffle.getRuntime().createCallTarget(new VecRootNode());
  }

  @Override
  protected boolean isObjectOfLanguage(Object object) {
    return object instanceof VecObject;
  }
}
