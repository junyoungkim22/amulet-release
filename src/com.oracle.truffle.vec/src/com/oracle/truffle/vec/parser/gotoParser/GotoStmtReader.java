package com.oracle.truffle.vec.parser.gotoParser;

import com.oracle.truffle.parser.generated.VecBaseVisitor;
import com.oracle.truffle.parser.generated.VecParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GotoStmtReader extends VecBaseVisitor<String> {

  private Map<String, Object> arrMap;

  public List<Double> constArgs;
  public List<Object> varArgs;
  public List<Integer> varArgProperties;

  public String argString;

  public String aArrName;
  public String bArrName;
  public String cArrName;

  public Object aArr;
  public Object bArr;
  public Object cArr;

  public String iString;
  public String jString;
  public String kString;

  public boolean aTranspose;
  public boolean bTranspose;

  public GotoStmtReader(Map<String, Object> arrMap) {
    this.arrMap = arrMap;
    this.kString = null;
    argString = "";
    this.constArgs = new ArrayList<Double>();
    this.varArgs = new ArrayList<Object>();
    this.varArgProperties = new ArrayList<Integer>();

    // debugging
    /*
    aArr = arrMap.get("mat0");
    bArr = arrMap.get("mat1");
    cArr = arrMap.get("result");
    iString = "i";
    jString = "j";
    kString = "k";
    */
  }

  public Map<String, Object> getArrMap() {
    return this.arrMap;
  }

  @Override
  public String visitAssignStatement(VecParser.AssignStatementContext ctx) {
    return this.visit(ctx.assignment());
  }

  @Override
  public String visitEqualAssn(VecParser.EqualAssnContext ctx) {
    if (ctx.var() instanceof VecParser.ArrayVarContext) {
      VecParser.ArrayVarContext cArrayVar = (VecParser.ArrayVarContext) ctx.var();
      cArrName = cArrayVar.ID().getText();
      cArr = arrMap.get(cArrName);
      iString = cArrayVar.expr().get(0).getText();
      jString = cArrayVar.expr().get(1).getText();
    }

    switch (ctx.assignop().getText()) {
      case "=":
        this.visit(ctx.expr());
        break;
      case "+=":
        VecParser.ExprContext[] maskCtxs = checkIfConditionalExpr(ctx.expr());
        if (ctx.expr() instanceof VecParser.RelationalExprContext) {
          VecParser.RelationalExprContext relCtx = (VecParser.RelationalExprContext) ctx.expr();
          if (relCtx.op.getText().equals("*")
              && (maskCtxs == null /*The expr is NOT a conditional expr*/)) {
            argString += GotoOpCode.FMADD;
            argString += GotoOpCode.C;
            this.visit(relCtx.expr(0));
            this.visit(relCtx.expr(1));
            return "";
          }
        }
        if (maskCtxs != null) {
          argString += GotoOpCode.MASKADD;
          this.visit(maskCtxs[0]);
          this.visit(ctx.var());
          this.visit(maskCtxs[1]);
          return "";
        } else {
          argString += GotoOpCode.ADD;
          this.visit(ctx.var());
          this.visit(ctx.expr());
          break;
        }
    }
    return "";
  }

  @Override
  public String visitArrayVar(VecParser.ArrayVarContext ctx) {
    String arrName = ctx.ID().getText();
    int dimensions = ctx.expr().size();
    if (dimensions == 2) {
      String index0 = ctx.expr().get(0).getText();
      String index1 = ctx.expr().get(1).getText();
      if (index0.equals(iString) && !index1.equals(jString)) { // is a[i][k]
        aArrName = arrName;
        aArr = arrMap.get(arrName);
        kString = index1;
        argString += GotoOpCode.A;
        aTranspose = false;
        return "";
      } else if (!index0.equals(jString) && index1.equals(iString)) { // is a[k][i]
        aArrName = arrName;
        aArr = arrMap.get(arrName);
        kString = index0;
        argString += GotoOpCode.A;
        aTranspose = true;
        return "";
      } else if (!index0.equals(iString) && index1.equals(jString)) { // is b[k][j]
        bArrName = arrName;
        bArr = arrMap.get(arrName);
        kString = index0;
        argString += GotoOpCode.B;
        bTranspose = false;
        return "";
      } else if (index0.equals(jString) && !index1.equals(iString)) { // is b[j][k]
        bArrName = arrName;
        bArr = arrMap.get(arrName);
        kString = index1;
        argString += GotoOpCode.B;
        bTranspose = true;
        return "";
      } else if (arrName.equals(cArrName)) {
        argString += GotoOpCode.C;
        return "";
      } else if (index0.equals(iString) && index1.equals(jString)) { // is arr[i][j]
        argString += GotoOpCode.VARIABLEARG;
        Object arr = arrMap.get(arrName);
        if (!varArgs.contains(arr)) {
          varArgs.add(arr);
          varArgProperties.add(3);
        }
        int index = varArgs.indexOf(arr);
        String indexString = Integer.toBinaryString(index);
        indexString =
            String.format("%" + String.valueOf(GotoOpCode.INDEXLENGTH) + "s", indexString)
                .replaceAll(" ", "0");
        argString += indexString;
        return "";
      }
    } else if (dimensions == 1) {
      argString += GotoOpCode.VARIABLEARG;
      Object arr = arrMap.get(arrName);
      String index0 = ctx.expr().get(0).getText();
      if (!varArgs.contains(arr)) {
        varArgs.add(arr);
        if (index0.equals(iString)) {
          varArgProperties.add(1);
        } else if (index0.equals(jString)) {
          varArgProperties.add(2);
        }
      }
      int index = varArgs.indexOf(arr);
      String indexString = Integer.toBinaryString(index);
      indexString =
          String.format("%" + String.valueOf(GotoOpCode.INDEXLENGTH) + "s", indexString)
              .replaceAll(" ", "0");
      argString += indexString;
      return "";
    }
    return "";
  }

  private VecParser.ExprContext[] checkIfConditionalExpr(VecParser.ExprContext ctx) {
    if (ctx instanceof VecParser.RelationalExprContext) {
      VecParser.RelationalExprContext relCtx = (VecParser.RelationalExprContext) ctx;
      if (!relCtx.op.getText().equals("*")) {
        return null;
      }
      if (relCtx.expr(0) instanceof VecParser.ParenExprContext) {
        VecParser.ParenExprContext leftParenExprContext =
            (VecParser.ParenExprContext) relCtx.expr(0);
        if (leftParenExprContext.expr() instanceof VecParser.RelationalExprContext) {
          VecParser.RelationalExprContext relCtx0 =
              (VecParser.RelationalExprContext) leftParenExprContext.expr();
          String binopString = relCtx0.op.getText();
          if (binopString.equals("<")
              || binopString.equals(">")
              || binopString.equals("<=")
              || binopString.equals(">=")) {
            // Return compare expression and calc expression
            return new VecParser.ExprContext[] {relCtx0, relCtx.expr(1)};
          }
        }
      }
      return null;
    } else {
      return null;
    }
  }

  @Override
  public String visitRelationalExpr(VecParser.RelationalExprContext ctx) {
    // System.out.println("REL");
    // System.out.println(ctx.getText());
    VecParser.ExprContext[] maskCtxs = checkIfConditionalExpr(ctx.expr(1));
    if (maskCtxs != null) {
      String binopString = ctx.op.getText();
      switch (binopString) {
        case "+":
          argString += GotoOpCode.MASKADD;
          break;
        case "-":
          argString += GotoOpCode.MASKSUB;
          break;
      }
      this.visit(maskCtxs[0]);
      this.visit(ctx.expr(0));
      this.visit(maskCtxs[1]);
      return "";
    }
    /*
    Boolean isMask = false;
    VecParser.ExprContext cmpContext = null;
    VecParser.ExprContext otherContext = null;
    if(ctx.op.getText().equals("*") && ctx.expr(0) instanceof VecParser.ParenExprContext) {
        VecParser.ParenExprContext leftParenExprContext = (VecParser.ParenExprContext) ctx.expr(0);
        if(leftParenExprContext.expr() instanceof VecParser.RelationalExprContext) {
               VecParser.RelationalExprContext relCtx0 = (VecParser.RelationalExprContext) leftParenExprContext.expr();
               String binopString = relCtx0.op.getText();
               if(binopString.equals("<") || binopString.equals(">") || binopString.equals("<=") || binopString.equals(">=")) {
                   isMask = true;
                   cmpContext = relCtx0;
               }
        }
    }
    if(isMask) {
        if(ctx.expr(1) instanceof VecParser.ParenExprContext) {
            VecParser.ParenExprContext rightParenExprContext = (VecParser.ParenExprContext) ctx.expr(1);
            if(rightParenExprContext.expr() instanceof VecParser.RelationalExprContext) {
                VecParser.RelationalExprContext relCtx1 = (VecParser.RelationalExprContext) rightParenExprContext.expr();
                String binopString = relCtx1.op.getText();
                if(binopString.equals("+")) {
                    argString += GotoOpCode.MASKADD;
                }
                else if(binopString.equals("-")) {
                    argString += GotoOpCode.MASKSUB;
                }
                this.visit(cmpContext);
                this.visit(relCtx1.expr(0));
                this.visit(relCtx1.expr(1));
                return "";
            }
        }
    }
    */
    if (ctx.op.getText().equals("<")) {
      argString += GotoOpCode.LT;
    } else if (ctx.op.getText().equals(">")) {
      argString += GotoOpCode.GT;
    } else if (ctx.op.getText().equals(">=")) {
      return "";
    } else if (ctx.op.getText().equals("<=")) {
      return "";
    } else if (ctx.op.getText().equals("+")) {
      argString += GotoOpCode.ADD;
    } else if (ctx.op.getText().equals("-")) {
      argString += GotoOpCode.SUB;
    } else if (ctx.op.getText().equals("*")) {
      argString += GotoOpCode.MUL;
    } else if (ctx.op.getText().equals("%")) {
      return "";
    }
    this.visit(ctx.expr(0));
    this.visit(ctx.expr(1));
    return "";
  }

  @Override
  public String visitIntAtom(VecParser.IntAtomContext ctx) {
    double argValue = (double) Integer.parseInt(ctx.getText());
    argString += GotoOpCode.CONSTARG;
    if (!constArgs.contains(argValue)) {
      constArgs.add(argValue);
    }
    int index = constArgs.indexOf(argValue);
    String indexString = Integer.toBinaryString(index);
    indexString =
        String.format("%" + String.valueOf(GotoOpCode.INDEXLENGTH) + "s", indexString)
            .replaceAll(" ", "0");
    argString += indexString;
    return "";
  }

  public void changeFormArgs(boolean packMatrix) {
    if (aTranspose && bTranspose && !packMatrix) {
      Object temp = this.aArr;
      this.aArr = this.bArr;
      this.bArr = temp;
      for (int index = 0; index < varArgProperties.size(); index++) {
        int property = varArgProperties.get(index);
        if (property == 1) { // i
          varArgProperties.set(index, 2);
        } else if (property == 2) { // j
          varArgProperties.set(index, 1);
        } else if (property == 3) {
          System.out.println("TODO!");
        }
      }
    }
  }

  public long getMatrixForm() {
    if (!aTranspose && !bTranspose) {
      return 0;
    } else if (aTranspose && !bTranspose) {
      return 1;
    } else if (!aTranspose && bTranspose) {
      return 2;
    } else { // AtBt
      return 3;
    }
  }

  /*
     public VecReader(HashMap<String, Object> arrMap, FrameDescriptor frameDescriptor, ParseResult parseResult) {
      this.arrMap = arrMap;
      this.frameDescriptor = frameDescriptor;
      this.locals = new HashMap<String, FrameSlot>();
      this.parseResult = parseResult;
      this.parseResult.locals = this.locals;
  }

  public FrameDescriptor getFrameDescriptor() {
      return this.frameDescriptor;
  }

  public HashMap<String, FrameSlot> getLocals() {
      return this.locals;
  }

  @Override public VecStatementNode visitBlockParse(VecParser.BlockParseContext ctx) {
      return this.visit(ctx.block());
  }

     @Override public VecStatementNode visitForParse(VecParser.ForParseContext ctx) {
      return this.visit(ctx.forStmt());
  }

  @Override public VecStatementNode visitForStmt(VecParser.ForStmtContext ctx) {
      VecExpressionNode loopCondition = (VecExpressionNode) this.visit(ctx.expr());
      VecStatementNode initStatement = this.visit(ctx.assignment(0));
      this.parseResult.initStatements.add(initStatement);
      VecStatementNode updateStatement = this.visit(ctx.assignment(1));
      this.parseResult.updateStatements.add(updateStatement);

      if(ctx.assignment(0) instanceof VecParser.EqualAssnContext) {
          VecParser.EqualAssnContext equalAssn = (VecParser.EqualAssnContext) ctx.assignment(0);
          this.parseResult.loopStarts.add((VecExpressionNode) this.visit(equalAssn.expr()));
          if(equalAssn.var() instanceof VecParser.IdVarContext) {
                 this.parseResult.loopVariableStrs.add(equalAssn.var().getText());
             }
          else {
                 System.out.println("Loop init not form of assignment");
                 return null;
          }
      }
      else {
          System.out.println("Loop init not form of assignment");
          return null;
      }

      if(ctx.expr() instanceof VecParser.RelationalExprContext) {
          VecParser.RelationalExprContext relExpr = (VecParser.RelationalExprContext) ctx.expr();
          this.parseResult.loopEnds.add((VecExpressionNode) this.visit(relExpr.expr(1)));
          this.parseResult.compareOpStrings.add((relExpr.binop().getText()));
      }
         else {
          System.out.println("Loop condition not form of relational expr");
          return null;
      }

      if(ctx.assignment(1) instanceof VecParser.UnaryAssnFrontContext) {
          VecParser.UnaryAssnFrontContext assnContext = (VecParser.UnaryAssnFrontContext) ctx.assignment(1);
          this.parseResult.updates.add(getIncrementExpression(assnContext.UNARY_OP().getText()));
          this.parseResult.negates.add(false);
      }
         else if(ctx.assignment(1) instanceof VecParser.UnaryAssnBackContext) {
          VecParser.UnaryAssnBackContext assnContext = (VecParser.UnaryAssnBackContext) ctx.assignment(1);
          this.parseResult.updates.add(getIncrementExpression(assnContext.UNARY_OP().getText()));
          this.parseResult.negates.add(false);
      }
      else if(ctx.assignment(1) instanceof VecParser.EqualAssnContext) {
          VecParser.EqualAssnContext assnContext = (VecParser.EqualAssnContext) ctx.assignment(1);
          VecExpressionNode updateExpr = (VecExpressionNode) this.visit(assnContext.expr());
          switch(assnContext.assignop().getText()) {
              case "+=":
                  this.parseResult.updates.add(updateExpr);
                  this.parseResult.negates.add(false);
                  break;
              case "-=":
                  this.parseResult.updates.add(updateExpr);
                  this.parseResult.negates.add(true);
                  break;
              default:
                  System.out.println("Invalid assign update!");
                  return null;
          }
      }
      for(VecParser.StmtContext stmtContext : ctx.block().stmt()) {
             if(stmtContext instanceof VecParser.AssignStatementContext || stmtContext instanceof VecParser.SimdDoubleFmaddStatementContext
                 || stmtContext instanceof VecParser.MatmulKernel8x16StatementContext
                 || stmtContext instanceof VecParser.MatmulKernel1D2x8StatementContext
                 || stmtContext instanceof VecParser.MatmulKernel2x8StatementContext
                 || stmtContext instanceof VecParser.GotoKernel8x8StatementContext) {
                 final VecStatementNode loopBodyStatement = this.visit(stmtContext);
                 this.parseResult.loopBodyStatements.add(loopBodyStatement);
             }
             else {
                 this.visit(stmtContext);
                 // No need to add anything to parseResult.loopBodyStatements, info is added while visiting if statement
             }
         }
      //this.visit(ctx.block());

         return null;
  }


     @Override public VecStatementNode visitIfStmt(VecParser.IfStmtContext ctx) {
         VecExpressionNode condition = (VecExpressionNode) this.visit(ctx.expr());
         VecStatementNode body = this.visit(ctx.block());
         VecExpressionNode[] conditions;
         if (condition instanceof VecAndNode) {
             VecAndNode andCond = (VecAndNode) condition;
             ArrayList<VecExpressionNode> andCondArrayList = andCond.getConditionList();
             conditions = new VecExpressionNode[andCondArrayList.size()];
             conditions = andCondArrayList.toArray(conditions);
         }
         else {
             conditions = new VecExpressionNode[1];
             conditions[0] = condition;
         }
         //this.parseResult.conditions = conditions;
         //this.parseResult.ifBody = body;
         final IfStatementInfo ifStatementInfo = new IfStatementInfo(conditions, body);
         this.parseResult.loopBodyStatements.add(ifStatementInfo);

         final VecExecutionPlanNode result = new VecExecutionPlanNode(conditions, body, "LOGICAL_AND", null);
         return result;
     }

  @Override public VecStatementNode visitBlock(VecParser.BlockContext ctx) {
         VecStatementNode [] stmtArr = new VecStatementNode[ctx.stmt().size()];
      int i = 0;
         for(VecParser.StmtContext stmtContext : ctx.stmt()) {
             VecStatementNode stmt = this.visit(stmtContext);
             stmtArr[i] = stmt;
             i++;
         }
         final VecBlockNode result = new VecBlockNode(stmtArr);
         return result;
  }

  @Override public VecStatementNode visitAssignStatement(VecParser.AssignStatementContext ctx) {
      return this.visit(ctx.assignment());
  }

  @Override public VecStatementNode visitEqualAssn(VecParser.EqualAssnContext ctx) {
      VecExpressionNode exprNode = (VecExpressionNode) this.visit(ctx.expr());
      VecExpressionNode assignNode = null;
      VecExpressionNode varNode = null;
      switch(ctx.assignop().getText()) {
          case "=":
              assignNode = exprNode;
              break;
          case "+=":
              varNode = (VecExpressionNode) this.visit(ctx.var());
              assignNode = VecAddNodeGen.create(varNode, exprNode);
              break;
             case "-=":
                 varNode = (VecExpressionNode) this.visit(ctx.var());
              assignNode = VecSubNodeGen.create(varNode, exprNode);
              break;
             case "*=":
                 varNode = (VecExpressionNode) this.visit(ctx.var());
              assignNode = VecMulNodeGen.create(varNode, exprNode);
              break;
      }
      if(ctx.var() instanceof VecParser.ArrayVarContext) {
          VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
          return createWriteArrayNode(arrayVar, assignNode);
      }
      else if (ctx.var() instanceof VecParser.IdVarContext) {
          VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
          return createWriteVarNode(idVar, assignNode);
      }
      return null;
  }

  @Override public VecStatementNode visitIdVar(VecParser.IdVarContext ctx) {
      String name = ctx.ID().getText();
      if(this.arrMap.containsKey(name)) {  // When referencing an array variable without indexing
          String arrType = arrMap.get(name).getClass().getName();
          //System.out.println(arrType);
          if(arrType.contains("[")) {
                 //System.out.println(name);
                 //System.out.println("ARRAY address");
                 final VecExpressionNode[] indexNodes = new VecExpressionNode[0];
                 final VecStatementNode result = new VecReadObjectArrayVariableNode(indexNodes, this.arrMap.get(name));
                 return result;
          }
      }
         FrameSlot frameSlot = this.frameDescriptor.findOrAddFrameSlot(name, FrameSlotKind.Int);
         this.locals.put(name, frameSlot);
         final VecStatementNode result = VecReadLocalVariableNodeGen.create(frameSlot);
         return result;
  }

  @Override public VecStatementNode visitArrayVar(VecParser.ArrayVarContext ctx) {
      String name = ctx.ID().getText();
      if(this.arrMap.containsKey(name)) {
          final VecExpressionNode[] indexNodes = new VecExpressionNode[ctx.expr().size()];
          int dimension = 0;
          for(VecParser.ExprContext exprCtx : ctx.expr()) {
              indexNodes[dimension] = (VecExpressionNode) this.visit(exprCtx);
              dimension++;
          }
          String arrType = arrMap.get(name).getClass().getName();
          if(arrType.contains("I")) {
              final VecReadIntArrayVariableNode result = new VecReadIntArrayVariableNode(indexNodes, arrMap.get(name));
              return result;
          }
          else if(arrType.contains("D")) {
              if(StringUtils.countMatches(arrType, "[") > dimension) {
                     final VecIndexDoubleArrayVariableNode result = new VecIndexDoubleArrayVariableNode(indexNodes, arrMap.get(name));
                     return result;
              }
              else {
                     final VecReadDoubleArrayVariableNode result = new VecReadDoubleArrayVariableNode(indexNodes, arrMap.get(name));
                     return result;
              }
          }
          else {
              System.out.println("Unknown array type encountered while parsing!");
              return null;
          }
      }
      else {
          System.out.println("Use of undefined Array variable!");
          return null;
      }
  }

  @Override public VecStatementNode visitIntAtom(VecParser.IntAtomContext ctx) {
      final VecIntLiteralNode result = new VecIntLiteralNode(Integer.parseInt(ctx.getText()));
      return result;
  }

  @Override public VecStatementNode visitParenExpr(VecParser.ParenExprContext ctx) {
      return (VecExpressionNode) this.visit(ctx.expr());
  }

  @Override public VecStatementNode visitRelationalExpr(VecParser.RelationalExprContext ctx) {
      VecExpressionNode lhs = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode rhs = (VecExpressionNode) this.visit(ctx.expr(1));
      if (ctx.binop().getText().equals("<")) {
             final VecLessThanNode result = VecLessThanNodeGen.create(lhs, rhs);
             return result;
      }
      else if (ctx.binop().getText().equals(">")) {
             final VecGreaterThanNode result = VecGreaterThanNodeGen.create(lhs, rhs);
             return result;
      }
         else if (ctx.binop().getText().equals(">=")) {
             final VecGreaterThanOrEqualNode result = VecGreaterThanOrEqualNodeGen.create(lhs, rhs);
             return result;

      }
         else if (ctx.binop().getText().equals("<=")) {
             final VecLessThanOrEqualNode result = VecLessThanOrEqualNodeGen.create(lhs, rhs);
             return result;
      }
      else if (ctx.binop().getText().equals("+")) {
             final VecAddNode result = VecAddNodeGen.create(lhs, rhs);
             return result;
      }
      else if (ctx.binop().getText().equals("-")) {
             final VecSubNode result = VecSubNodeGen.create(lhs, rhs);
             return result;
      }
      else if (ctx.binop().getText().equals("*")) {
             final VecMulNode result = VecMulNodeGen.create(lhs, rhs);
             return result;
      }
      else if (ctx.binop().getText().equals("%")) {
             final VecModuloNode result = VecModuloNodeGen.create(lhs, rhs);
             return result;
      }
      System.out.println("Invalid binary operator!!");
      return null;
  }


  @Override public VecStatementNode visitAndExpr(VecParser.AndExprContext ctx) {
      VecExpressionNode lhs = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode rhs = (VecExpressionNode) this.visit(ctx.expr(1));
      final VecAndNode result = new VecAndNode(lhs, rhs);
      return result;
  }

  private VecExpressionNode getIncrementExpression(String unaryOp) {
      switch(unaryOp) {
          case "++":
              return new VecIntLiteralNode(1);
          case "--":
              return new VecIntLiteralNode(-1);
          default:
              System.out.println("Invalid unary operator");
              return null;
      }
  }

  private VecStatementNode createWriteArrayNode(VecParser.ArrayVarContext ctx, VecExpressionNode exprNode) {
      String arrayName = ctx.ID().getText();
      final VecExpressionNode[] indexNodes = new VecExpressionNode[ctx.expr().size()];
         int i = 0;
         for(VecParser.ExprContext exprCtx : ctx.expr()) {
             indexNodes[i] = (VecExpressionNode) this.visit(exprCtx);
             i++;
         }
         String arrType = this.arrMap.get(arrayName).getClass().getName();
         if(arrType.contains("I")) {
             final VecWriteIntArrayVariableNode result = new VecWriteIntArrayVariableNode(indexNodes, exprNode, arrMap.get(arrayName));
             return result;
         }
         else if(arrType.contains("D")) {
             final VecWriteDoubleArrayVariableNode result = new VecWriteDoubleArrayVariableNode(indexNodes, exprNode, arrMap.get(arrayName));
             return result;
         }
         else {
             System.out.println("Unknown array type encountered while parsing!");
             return null;
         }
  }

  private VecStatementNode createWriteVarNode(VecParser.IdVarContext ctx, VecExpressionNode exprNode) {
         String name = ctx.ID().getText();
         FrameSlot frameSlot = this.frameDescriptor.findOrAddFrameSlot(name, FrameSlotKind.Int);
         this.locals.put(name, frameSlot);
         VecWriteLocalVariableNode result = VecWriteLocalVariableNodeGen.create(exprNode, frameSlot);
         return result;
  }

  @Override public VecStatementNode visitUnaryAssnBack(VecParser.UnaryAssnBackContext ctx) {
         VecExpressionNode varExpr = (VecExpressionNode) this.visit(ctx.var());
         VecExpressionNode incrementExpr = getIncrementExpression(ctx.UNARY_OP().getText());
         VecAddNode addExpr = VecAddNodeGen.create(varExpr, incrementExpr);
         if(ctx.var() instanceof VecParser.ArrayVarContext) {
          VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
          return createWriteArrayNode(arrayVar, addExpr);
      }
         else if (ctx.var() instanceof VecParser.IdVarContext) {
          VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
          return createWriteVarNode(idVar, addExpr);
      }
      return null;
  }

  @Override public VecStatementNode visitUnaryAssnFront(VecParser.UnaryAssnFrontContext ctx) {
         VecExpressionNode varExpr = (VecExpressionNode) this.visit(ctx.var());
         VecExpressionNode incrementExpr = getIncrementExpression(ctx.UNARY_OP().getText());
         VecAddNode addExpr = VecAddNodeGen.create(varExpr, incrementExpr);
         if(ctx.var() instanceof VecParser.ArrayVarContext) {
          VecParser.ArrayVarContext arrayVar = (VecParser.ArrayVarContext) ctx.var();
          return createWriteArrayNode(arrayVar, addExpr);
      }
         else if (ctx.var() instanceof VecParser.IdVarContext) {
          VecParser.IdVarContext idVar = (VecParser.IdVarContext) ctx.var();
          return createWriteVarNode(idVar, addExpr);
      }
      return null;
  }

  @Override public VecStatementNode visitSimdDoubleFmaddStatement(VecParser.SimdDoubleFmaddStatementContext ctx) {
      return this.visit(ctx.simdDoubleFmadd());
  }

  @Override public VecStatementNode visitSimdDoubleFmadd(VecParser.SimdDoubleFmaddContext ctx) {
      VecExpressionNode multVarExpr = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode inputArrExpr = (VecExpressionNode) this.visit(ctx.expr(1));
      VecExpressionNode outputArrExpr = (VecExpressionNode) this.visit(ctx.expr(2));
      return new VecSimdDoubleFmaddNode(multVarExpr, inputArrExpr, outputArrExpr);
  }

  @Override public VecStatementNode visitMatmulKernel8x16Statement(VecParser.MatmulKernel8x16StatementContext ctx) {
      return this.visit(ctx.matmulKernel8x16());
  }

  @Override public VecStatementNode visitMatmulKernel8x16(VecParser.MatmulKernel8x16Context ctx) {
      VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
      VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
      VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
      VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
      VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
      VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
      return new VecMatmulKernel8x16Node(aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override public VecStatementNode visitMatmulKernel1D2x8Statement(VecParser.MatmulKernel1D2x8StatementContext ctx) {
      return this.visit(ctx.matmulKernel1D2x8());
  }

  @Override public VecStatementNode visitMatmulKernel1D2x8(VecParser.MatmulKernel1D2x8Context ctx) {
      VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
      VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
      VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
      VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
      VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
      VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
      return new VecMatmulKernel1D2x8Node(aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override public VecStatementNode visitMatmulKernel2x8Statement(VecParser.MatmulKernel2x8StatementContext ctx) {
      return this.visit(ctx.matmulKernel2x8());
  }

  @Override public VecStatementNode visitMatmulKernel2x8(VecParser.MatmulKernel2x8Context ctx) {
      VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
      VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
      VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
      VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
      VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
      VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
      VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
      return new VecMatmulKernel2x8Node(aExpr, bExpr, resultExpr, kPanelSizeExpr, iExpr, kExpr, jExpr);
  }

  @Override public VecStatementNode visitGotoKernel8x8Statement(VecParser.GotoKernel8x8StatementContext ctx) {
      return this.visit(ctx.gotoKernel8x8());
  }

  @Override public VecStatementNode visitGotoKernel8x8(VecParser.GotoKernel8x8Context ctx) {
      //VecExpressionNode aExpr = (VecExpressionNode) this.visit(ctx.expr(0));
      //VecExpressionNode bExpr = (VecExpressionNode) this.visit(ctx.expr(1));
      //VecExpressionNode resultExpr = (VecExpressionNode) this.visit(ctx.expr(2));
      double[][] a = (double[][]) arrMap.get(ctx.expr(0).getText());
      double[][] b = (double[][]) arrMap.get(ctx.expr(1).getText());
      double[][] c = (double[][]) arrMap.get(ctx.expr(2).getText());
      VecExpressionNode kPanelSizeExpr = (VecExpressionNode) this.visit(ctx.expr(3));
      VecExpressionNode iExpr = (VecExpressionNode) this.visit(ctx.expr(4));
      VecExpressionNode kExpr = (VecExpressionNode) this.visit(ctx.expr(5));
      VecExpressionNode jExpr = (VecExpressionNode) this.visit(ctx.expr(6));
      VecExpressionNode calcExpr = (VecExpressionNode) this.visit(ctx.expr(7));
      VecGotoKernel8x8Node ret = new VecGotoKernel8x8Node(a, b, c, kPanelSizeExpr, iExpr, kExpr, jExpr, calcExpr);
      //ret.arrs[3] = arrMap.get("threshold");
      return ret;
  }
  */
}
