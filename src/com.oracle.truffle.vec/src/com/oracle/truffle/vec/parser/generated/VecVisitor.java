package com.oracle.truffle.parser.generated;
// Generated from Vec.g4 by ANTLR 4.7
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced by {@link VecParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return
 *     type.
 */
public interface VecVisitor<T> extends ParseTreeVisitor<T> {
  /**
   * Visit a parse tree produced by the {@code blockParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBlockParse(VecParser.BlockParseContext ctx);
  /**
   * Visit a parse tree produced by the {@code forParse} labeled alternative in {@link
   * VecParser#parse}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForParse(VecParser.ForParseContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#block}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBlock(VecParser.BlockContext ctx);
  /**
   * Visit a parse tree produced by the {@code assignStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAssignStatement(VecParser.AssignStatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code ifStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIfStatement(VecParser.IfStatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code forStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForStatement(VecParser.ForStatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code simdDoubleFmaddStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSimdDoubleFmaddStatement(VecParser.SimdDoubleFmaddStatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code matmulKernel8x16Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel8x16Statement(VecParser.MatmulKernel8x16StatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code matmulKernel2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel2x8Statement(VecParser.MatmulKernel2x8StatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code matmulKernel1D2x8Statement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel1D2x8Statement(VecParser.MatmulKernel1D2x8StatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code gotoKernel8x8Statement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitGotoKernel8x8Statement(VecParser.GotoKernel8x8StatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code convKernelStatement} labeled alternative in {@link
   * VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitConvKernelStatement(VecParser.ConvKernelStatementContext ctx);
  /**
   * Visit a parse tree produced by the {@code declarativeLoopStatement} labeled alternative in
   * {@link VecParser#stmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitDeclarativeLoopStatement(VecParser.DeclarativeLoopStatementContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#ifStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIfStmt(VecParser.IfStmtContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#forStmt}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitForStmt(VecParser.ForStmtContext ctx);
  /**
   * Visit a parse tree produced by the {@code equalAssn} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitEqualAssn(VecParser.EqualAssnContext ctx);
  /**
   * Visit a parse tree produced by the {@code unaryAssnFront} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitUnaryAssnFront(VecParser.UnaryAssnFrontContext ctx);
  /**
   * Visit a parse tree produced by the {@code unaryAssnBack} labeled alternative in {@link
   * VecParser#assignment}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitUnaryAssnBack(VecParser.UnaryAssnBackContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#simdDoubleFmadd}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitSimdDoubleFmadd(VecParser.SimdDoubleFmaddContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#matmulKernel8x16}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel8x16(VecParser.MatmulKernel8x16Context ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#matmulKernel2x8}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel2x8(VecParser.MatmulKernel2x8Context ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#gotoKernel8x8}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitGotoKernel8x8(VecParser.GotoKernel8x8Context ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#convKernel}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitConvKernel(VecParser.ConvKernelContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#declarativeLoop}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitDeclarativeLoop(VecParser.DeclarativeLoopContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#matmulKernel1D2x8}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitMatmulKernel1D2x8(VecParser.MatmulKernel1D2x8Context ctx);
  /**
   * Visit a parse tree produced by the {@code varExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitVarExpr(VecParser.VarExprContext ctx);
  /**
   * Visit a parse tree produced by the {@code atomExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAtomExpr(VecParser.AtomExprContext ctx);
  /**
   * Visit a parse tree produced by the {@code relationalExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitRelationalExpr(VecParser.RelationalExprContext ctx);
  /**
   * Visit a parse tree produced by the {@code parenExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitParenExpr(VecParser.ParenExprContext ctx);
  /**
   * Visit a parse tree produced by the {@code andExpr} labeled alternative in {@link
   * VecParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAndExpr(VecParser.AndExprContext ctx);
  /**
   * Visit a parse tree produced by the {@code arrayVar} labeled alternative in {@link
   * VecParser#var}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitArrayVar(VecParser.ArrayVarContext ctx);
  /**
   * Visit a parse tree produced by the {@code idVar} labeled alternative in {@link VecParser#var}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIdVar(VecParser.IdVarContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#binop}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitBinop(VecParser.BinopContext ctx);
  /**
   * Visit a parse tree produced by {@link VecParser#assignop}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAssignop(VecParser.AssignopContext ctx);
  /**
   * Visit a parse tree produced by the {@code intAtom} labeled alternative in {@link
   * VecParser#atom}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitIntAtom(VecParser.IntAtomContext ctx);
}
