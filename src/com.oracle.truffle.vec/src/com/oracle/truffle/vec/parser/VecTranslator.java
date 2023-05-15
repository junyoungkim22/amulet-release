package com.oracle.truffle.vec.parser;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.parser.generated.VecLexer;
import com.oracle.truffle.parser.generated.VecParser;
import java.util.HashMap;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class VecTranslator {
  public static ParseResult strToNodes(
      String str, HashMap<String, Object> arrMap, FrameDescriptor frameDescriptor) {
    CharStream strCharStream = CharStreams.fromString(str + "\n");
    VecLexer lexer = new VecLexer(strCharStream);
    VecParser parser = new VecParser(new CommonTokenStream(lexer));

    ParseTree tree = parser.parse();
    ParseResult ret = new ParseResult();
    VecReader reader = new VecReader(arrMap, frameDescriptor, ret);
    reader.visit(tree);
    return ret;
  }
}
