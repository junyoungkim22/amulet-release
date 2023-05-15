package com.oracle.truffle.parser.generated;
// Generated from Vec.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeListener;

/** This interface defines a complete listener for a parse tree produced by {@link VecParser}. */
public interface VecListener extends ParseTreeListener {
  /**
   * Enter a parse tree produced by the {@code blockParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   */
  void enterBlockParse(VecParser.BlockParseContext ctx);
  /**
   * Exit a parse tree produced by the {@code blockParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   */
  void exitBlockParse(VecParser.BlockParseContext ctx);
  /**
   * Enter a parse tree produced by the {@code forParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   */
  void enterForParse(VecParser.ForParseContext ctx);
  /**
   * Exit a parse tree produced by the {@code forParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   */
  void exitForParse(VecParser.ForParseContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#block}.
   *
   * @param ctx the parse tree
   */
  void enterBlock(VecParser.BlockContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#block}.
   *
   * @param ctx the parse tree
   */
  void exitBlock(VecParser.BlockContext ctx);
  /**
   * Enter a parse tree produced by the {@code assignStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterAssignStatement(VecParser.AssignStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code assignStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitAssignStatement(VecParser.AssignStatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code ifStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterIfStatement(VecParser.IfStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code ifStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitIfStatement(VecParser.IfStatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code forStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterForStatement(VecParser.ForStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code forStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitForStatement(VecParser.ForStatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code simdDoubleFmaddStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterSimdDoubleFmaddStatement(VecParser.SimdDoubleFmaddStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code simdDoubleFmaddStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitSimdDoubleFmaddStatement(VecParser.SimdDoubleFmaddStatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code matmulKernel8x16Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel8x16Statement(VecParser.MatmulKernel8x16StatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code matmulKernel8x16Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel8x16Statement(VecParser.MatmulKernel8x16StatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code matmulKernel2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel2x8Statement(VecParser.MatmulKernel2x8StatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code matmulKernel2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel2x8Statement(VecParser.MatmulKernel2x8StatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code matmulKernel1D2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel1D2x8Statement(VecParser.MatmulKernel1D2x8StatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code matmulKernel1D2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel1D2x8Statement(VecParser.MatmulKernel1D2x8StatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code gotoKernel8x8Statement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterGotoKernel8x8Statement(VecParser.GotoKernel8x8StatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code gotoKernel8x8Statement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitGotoKernel8x8Statement(VecParser.GotoKernel8x8StatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code convKernelStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterConvKernelStatement(VecParser.ConvKernelStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code convKernelStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitConvKernelStatement(VecParser.ConvKernelStatementContext ctx);
  /**
   * Enter a parse tree produced by the {@code declarativeLoopStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void enterDeclarativeLoopStatement(VecParser.DeclarativeLoopStatementContext ctx);
  /**
   * Exit a parse tree produced by the {@code declarativeLoopStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   */
  void exitDeclarativeLoopStatement(VecParser.DeclarativeLoopStatementContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#ifStmt}.
   *
   * @param ctx the parse tree
   */
  void enterIfStmt(VecParser.IfStmtContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#ifStmt}.
   *
   * @param ctx the parse tree
   */
  void exitIfStmt(VecParser.IfStmtContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void enterForStmt(VecParser.ForStmtContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#forStmt}.
   *
   * @param ctx the parse tree
   */
  void exitForStmt(VecParser.ForStmtContext ctx);
  /**
   * Enter a parse tree produced by the {@code equalAssn} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void enterEqualAssn(VecParser.EqualAssnContext ctx);
  /**
   * Exit a parse tree produced by the {@code equalAssn} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void exitEqualAssn(VecParser.EqualAssnContext ctx);
  /**
   * Enter a parse tree produced by the {@code unaryAssnFront} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void enterUnaryAssnFront(VecParser.UnaryAssnFrontContext ctx);
  /**
   * Exit a parse tree produced by the {@code unaryAssnFront} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void exitUnaryAssnFront(VecParser.UnaryAssnFrontContext ctx);
  /**
   * Enter a parse tree produced by the {@code unaryAssnBack} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void enterUnaryAssnBack(VecParser.UnaryAssnBackContext ctx);
  /**
   * Exit a parse tree produced by the {@code unaryAssnBack} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   */
  void exitUnaryAssnBack(VecParser.UnaryAssnBackContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#simdDoubleFmadd}.
   *
   * @param ctx the parse tree
   */
  void enterSimdDoubleFmadd(VecParser.SimdDoubleFmaddContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#simdDoubleFmadd}.
   *
   * @param ctx the parse tree
   */
  void exitSimdDoubleFmadd(VecParser.SimdDoubleFmaddContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#matmulKernel8x16}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel8x16(VecParser.MatmulKernel8x16Context ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#matmulKernel8x16}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel8x16(VecParser.MatmulKernel8x16Context ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#matmulKernel2x8}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel2x8(VecParser.MatmulKernel2x8Context ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#matmulKernel2x8}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel2x8(VecParser.MatmulKernel2x8Context ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#gotoKernel8x8}.
   *
   * @param ctx the parse tree
   */
  void enterGotoKernel8x8(VecParser.GotoKernel8x8Context ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#gotoKernel8x8}.
   *
   * @param ctx the parse tree
   */
  void exitGotoKernel8x8(VecParser.GotoKernel8x8Context ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#convKernel}.
   *
   * @param ctx the parse tree
   */
  void enterConvKernel(VecParser.ConvKernelContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#convKernel}.
   *
   * @param ctx the parse tree
   */
  void exitConvKernel(VecParser.ConvKernelContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#declarativeLoop}.
   *
   * @param ctx the parse tree
   */
  void enterDeclarativeLoop(VecParser.DeclarativeLoopContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#declarativeLoop}.
   *
   * @param ctx the parse tree
   */
  void exitDeclarativeLoop(VecParser.DeclarativeLoopContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#matmulKernel1D2x8}.
   *
   * @param ctx the parse tree
   */
  void enterMatmulKernel1D2x8(VecParser.MatmulKernel1D2x8Context ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#matmulKernel1D2x8}.
   *
   * @param ctx the parse tree
   */
  void exitMatmulKernel1D2x8(VecParser.MatmulKernel1D2x8Context ctx);
  /**
   * Enter a parse tree produced by the {@code varExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterVarExpr(VecParser.VarExprContext ctx);
  /**
   * Exit a parse tree produced by the {@code varExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitVarExpr(VecParser.VarExprContext ctx);
  /**
   * Enter a parse tree produced by the {@code atomExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAtomExpr(VecParser.AtomExprContext ctx);
  /**
   * Exit a parse tree produced by the {@code atomExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAtomExpr(VecParser.AtomExprContext ctx);
  /**
   * Enter a parse tree produced by the {@code relationalExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterRelationalExpr(VecParser.RelationalExprContext ctx);
  /**
   * Exit a parse tree produced by the {@code relationalExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitRelationalExpr(VecParser.RelationalExprContext ctx);
  /**
   * Enter a parse tree produced by the {@code parenExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterParenExpr(VecParser.ParenExprContext ctx);
  /**
   * Exit a parse tree produced by the {@code parenExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitParenExpr(VecParser.ParenExprContext ctx);
  /**
   * Enter a parse tree produced by the {@code andExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void enterAndExpr(VecParser.AndExprContext ctx);
  /**
   * Exit a parse tree produced by the {@code andExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   */
  void exitAndExpr(VecParser.AndExprContext ctx);
  /**
   * Enter a parse tree produced by the {@code arrayVar} labeled alternative in {@link
   * VecParser#var}.
   *
   * @param ctx the parse tree
   */
  void enterArrayVar(VecParser.ArrayVarContext ctx);
  /**
   * Exit a parse tree produced by the {@code arrayVar} labeled alternative in {@link
   * VecParser#var}.
   *
   * @param ctx the parse tree
   */
  void exitArrayVar(VecParser.ArrayVarContext ctx);
  /**
   * Enter a parse tree produced by the {@code idVar} labeled alternative in {@link VecParser#var}.
   *
   * @param ctx the parse tree
   */
  void enterIdVar(VecParser.IdVarContext ctx);
  /**
   * Exit a parse tree produced by the {@code idVar} labeled alternative in {@link VecParser#var}.
   *
   * @param ctx the parse tree
   */
  void exitIdVar(VecParser.IdVarContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#binop}.
   *
   * @param ctx the parse tree
   */
  void enterBinop(VecParser.BinopContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#binop}.
   *
   * @param ctx the parse tree
   */
  void exitBinop(VecParser.BinopContext ctx);
  /**
   * Enter a parse tree produced by {@link VecParser#assignop}.
   *
   * @param ctx the parse tree
   */
  void enterAssignop(VecParser.AssignopContext ctx);
  /**
   * Exit a parse tree produced by {@link VecParser#assignop}.
   *
   * @param ctx the parse tree
   */
  void exitAssignop(VecParser.AssignopContext ctx);
  /**
   * Enter a parse tree produced by the {@code intAtom} labeled alternative in {@link
   * VecParser#atom}.
   *
   * @param ctx the parse tree
   */
  void enterIntAtom(VecParser.IntAtomContext ctx);
  /**
   * Exit a parse tree produced by the {@code intAtom} labeled alternative in {@link
   * VecParser#atom}.
   *
   * @param ctx the parse tree
   */
  void exitIntAtom(VecParser.IntAtomContext ctx);
}
