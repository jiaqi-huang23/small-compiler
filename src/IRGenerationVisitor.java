package ir;

import type.*;
import ast.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.HashMap;

public class IRGenerationVisitor implements TempVisitor{
    String className;
    HashMap<String, Type> funcMap;
    Vector<IRFunction> irfuncs;

    public IRGenerationVisitor(String cname){
        className = cname;
        funcMap = new HashMap<String,Type>();
        irfuncs = new Vector<IRFunction>();
    }

    public Vector<IRFunction> getIRFuncs() {
        return irfuncs;
    }

    public Temp visit (Program p){
        //get class name
        //System.out.println("PROG " + className);
        Enumeration fEnum = p.flist.elements();
        //add all function declaration to function table
        while (fEnum.hasMoreElements()) {
            Function f = (Function)fEnum.nextElement();
            funcMap.put(f.getFuncName(), f.getReturnType());
        }

        fEnum = p.flist.elements();
        while (fEnum.hasMoreElements()) {
            Function f = (Function)fEnum.nextElement();
            f.accept(this);
        }

        return null;
    }

    public Temp visit (Function f) {
        IRFunction irf = new IRFunction(f); // signiture generated in constructor
        //System.out.println("FUNC " + irf.signiture);
        //no two functions can have the same name
        f.funcDecl.accept(this, irf);
        f.funcBody.accept(this, irf);
        //System.out.println("{");
        //irf.printTempDecls();
        //System.out.println();
        IRReturnInstruction ret = new IRReturnInstruction(null);
        irf.addInstruction(ret);
        //irf.printInstructions();
        irf.localNum = irf.temps.count;
        irf.temps.destroyFactory();
        IRLabelInstruction label = new IRLabelInstruction(); //reset label count
        label.resetLable();
        //System.out.println("}");
        irfuncs.add(irf);
        return null;
    }

    public Temp visit (FunctionDeclaration fd, IRFunction irf) {
        //put parameter temp into temp map
        fd.getParaList().accept(this, irf);
        return null;
    }
    public Temp visit (FunctionBody fb, IRFunction irf) {
        //variable declaration list
        fb.vdl.accept(this, irf);
        //statement list
        fb.stml.accept(this, irf);
        return null;
    }

    public Temp visit (AddExpression ad,IRFunction irf) {
        Temp lhs = ad.getExpr1().accept(this,irf);
        Temp rhs = ad.getExpr2().accept(this,irf);
        Temp dest = irf.temps.generateTemp(lhs.type);
        IRInstruction in = new IRBinaryOpAssignment(dest, lhs, rhs, Ops.BinaryOps.ADD);
        irf.addInstruction(in);
        return dest;
    }

    public Temp visit (ArrayAssignmentStatement aa, IRFunction irf) {
        //System.out.println("in array assignment");
        Temp lhs = aa.id.accept(this,irf);
        Temp index = aa.index.accept(this,irf);
        Temp rhs = aa.expr.accept(this,irf);
        IRInstruction in = new IRArrayAssignAssignment(lhs, index, rhs);
        irf.addInstruction(in);
        return lhs;
    }

    public Temp visit (ArrayReferenceExpression ar, IRFunction irf) {
        Temp rhs = ar.id.accept(this,irf);
        Temp index = ar.expr.accept(this,irf);
        Temp dest = irf.temps.generateTemp(((ArrayType)rhs.type).getNodeOfTypeOfArray().type);
        IRInstruction in = new IRArrayReferenceAssignment(dest, rhs, index);
        irf.addInstruction(in);
        return dest;
    }

    public Temp visit (AssignmentStatement as, IRFunction irf) {
        Temp t1 = as.id.accept(this, irf);
        Temp t2 = as.expr.accept(this,irf);
        IRInstruction in = new IROperandAssignment(t1,t2);
        irf.addInstruction(in);
        return t1;
    }


    public Temp visit (Block b, IRFunction irf) {
        Enumeration<Statement> stmEnum = b.getStatementList().slist.elements();
        while (stmEnum.hasMoreElements()) {
            //System.out.println("found statement list in block");
            Statement s = stmEnum.nextElement();
            s.accept(this,irf);
        }
        return null;
    }

    public Temp visit (EqualExpression ee, IRFunction irf) {
        Temp lhs = ee.getExpr1().accept(this,irf);
        Temp rhs = ee.getExpr2().accept(this,irf);
        Temp dest = irf.temps.generateTemp(new BooleanType());
        IRInstruction in = new IRBinaryOpAssignment(dest, lhs, rhs, Ops.BinaryOps.EQUAL);
        irf.addInstruction(in);
        return dest;
    }

    public Temp visit (ExpressionStatement es, IRFunction irf) {
        return es.expr.accept(this,irf);
    }

    public Temp visit (FormalParameter fp, IRFunction irf) {
        Temp temp = irf.temps.generateTemp(fp.getParaType().type, fp.getParaName(), TempClass.PARAMETER);
        return temp;
    }

    public Temp visit (FunctionCallExpression fce, IRFunction irf) {
        String funcName = fce.getFuncId().name;
        Vector<Temp> args = new Vector<Temp>();
        Enumeration<Expression> argsEnum = fce.getArglist().elements();
        while(argsEnum.hasMoreElements()) {
            Temp arg = argsEnum.nextElement().accept(this,irf);
            args.add(arg);
        }
        //use function signiture to distinguish functions
        Type returnType = funcMap.get(funcName);
        //System.out.print(funcName + "- return type: " + returnType);
        Temp ret;
        if (returnType instanceof VoidType) {
            ret = null;
        } else {
            ret = irf.temps.generateTemp(returnType);
        }
        IRInstruction in = new IRFunctionCallInstruction(ret,funcName,args);
        irf.addInstruction(in);
        return ret;
    }

    public Temp visit (Identifier i, IRFunction irf) {
        return irf.temps.lookupTemp(i.name);
    }

    public Temp visit (IdentifierReference ir, IRFunction irf) {
        return irf.temps.lookupTemp(ir.name);
    }

    public Temp visit (IfStatement is, IRFunction irf) {
        IRLabelInstruction l1 = new IRLabelInstruction();
        //IRLabelInstruction l2 = new IRLabelInstruction();
        IRLabelInstruction end = new IRLabelInstruction();
        IRInstruction in;

        Temp t = is.getCondExpr().accept(this,irf);
        if (t.isParaOrLocal()) {
            Temp t2 = irf.temps.generateTemp(new BooleanType());
            in = new IROperandAssignment(t2,t);
            irf.addInstruction(in);
            t = t2;
        }
        //get the inversion of condition Expression
        Temp inv = irf.temps.generateTemp(new BooleanType());
        in = new IRUnaryOpAssignment(inv, Ops.UnaryOps.NOT, t);
        irf.addInstruction(in);
        in = new IRConditionalJumpInstruction(inv,l1);
        irf.addInstruction(in);
        is.getBlock1().accept(this, irf);
        in = new IRJumpInstruction(end);
        irf.addInstruction(in);

        irf.addInstruction(l1);
        if (is.getBlock2() != null) {
            is.getBlock2().accept(this,irf);
        }
        irf.addInstruction(end);
        return null;
    }


    public Temp visit (LessthanExpression le, IRFunction irf) {
        Temp lhs = le.getExpr1().accept(this,irf);
        Temp rhs = le.getExpr2().accept(this,irf);
        Temp dest = irf.temps.generateTemp(new BooleanType());
        IRInstruction in = new IRBinaryOpAssignment(dest, lhs, rhs, Ops.BinaryOps.LESSTHAN);
        irf.addInstruction(in);
        return dest;
    }
    public Temp visit (MultiExpression me, IRFunction irf) {
        Temp lhs = me.getExpr1().accept(this,irf);
        Temp rhs = me.getExpr2().accept(this,irf);
        Temp dest = irf.temps.generateTemp(lhs.type);
        IRInstruction in = new IRBinaryOpAssignment(dest, lhs, rhs, Ops.BinaryOps.MULTIPLY);
        irf.addInstruction(in);
        return dest;
    }

    public Temp visit (ParameterList pl, IRFunction irf) {
        Enumeration paraEnum = pl.paralist.elements();
        while (paraEnum.hasMoreElements()) {
            FormalParameter fp = (FormalParameter)paraEnum.nextElement();
            Temp temp = fp.accept(this, irf);
        }
        return null;
    }

    public Temp visit (ParenExpression pe, IRFunction irf) {
        Temp t = pe.getExpr().accept(this,irf);
        return t;
    }
    public Temp visit (PrintStatement ps, IRFunction irf) {
        Temp t = ps.getPrintExpr().accept(this,irf);
        IRInstruction in = new IRPrintInstruction(t);
        irf.addInstruction(in);
        return t;
    }
    public Temp visit (PrintlnStatement pl, IRFunction irf) {
        Temp t = pl.getPrintlnExpr().accept(this,irf);
        IRInstruction in = new IRPrintlnInstruction(t);
        irf.addInstruction(in);
        return t;
    }
    public Temp visit (ReturnStatement rs, IRFunction irf) {
        Temp ret = null;
        if (rs.getExpr() != null) {
            ret = rs.getExpr().accept(this,irf);
        }
        IRInstruction in = new IRReturnInstruction(ret);
        irf.addInstruction(in);
        return ret;
    }
    public Temp visit (StatementList sl, IRFunction irf) {
        Enumeration<Statement> stmEnum = sl.slist.elements();
        while (stmEnum.hasMoreElements()) {
            Statement s = stmEnum.nextElement();
            s.accept(this,irf);
        }
        return null;
    }

    public Temp visit (SubtractExpression se, IRFunction irf) {
        Temp lhs = se.getExpr1().accept(this,irf);
        Temp rhs = se.getExpr2().accept(this,irf);
        Temp dest = irf.temps.generateTemp(lhs.type);
        IRInstruction in = new IRBinaryOpAssignment(dest, lhs, rhs, Ops.BinaryOps.SUBTRACT);
        irf.addInstruction(in);
        return dest;
    }
    //public Temp visit (TempNode t)

    public Temp visit (VariableDeclaration vd, IRFunction irf) {
        Type t = vd.getVarType().type;
        Temp temp = irf.temps.generateTemp(t, vd.getVarName(),TempClass.LOCAL);
        if (t instanceof ArrayType) {
            int size = ((ArrayType)t).getSize();
            IRInstruction in = new IRArrayCreationAssignment(temp, size);
            irf.addInstruction(in);
        }
        return temp;
    }

    public Temp visit (VariableDeclarationList vdl, IRFunction irf) {
        Enumeration vEnum = vdl.vlist.elements();
        while (vEnum.hasMoreElements()) {
            VariableDeclaration v = (VariableDeclaration)vEnum.nextElement();
            Temp temp = v.accept(this, irf);
            irf.localNum++;
        }
        return null;
    }

    public Temp visit (WhileStatement ws, IRFunction irf) {
        IRInstruction in;
        IRLabelInstruction l1 = new IRLabelInstruction();
        IRLabelInstruction l2 = new IRLabelInstruction();

        irf.addInstruction(l1);
        Temp t = ws.getCondExpr().accept(this,irf);
        if (t.isParaOrLocal()) {
            Temp t2 = irf.temps.generateTemp(new BooleanType());
            in = new IROperandAssignment(t2,t);
            irf.addInstruction(in);
            t = t2;
        }
        Temp inv = irf.temps.generateTemp(new BooleanType());
        in = new IRUnaryOpAssignment(inv, Ops.UnaryOps.NOT, t);
        irf.addInstruction(in);
        in = new IRConditionalJumpInstruction(inv,l2);
        irf.addInstruction(in);
        ws.getBlock().accept(this,irf);
        in = new IRJumpInstruction(l1);
        irf.addInstruction(in);
        irf.addInstruction(l2);
        return null;
    }
    public Temp visit (IntegerLiteral il, IRFunction irf) {
        //System.out.println("visiting int literal");
        Temp temp = irf.temps.generateTemp(new IntegerType(), il.getValString(),TempClass.CONSTANT);
        IRInstruction in = new IRConstantAssignment(temp, il.getValString());
        irf.addInstruction(in);
        return temp;
    }
    public Temp visit (StringLiteral sl, IRFunction irf) {
        Temp temp = irf.temps.generateTemp(new StringType(), sl.getValString(),TempClass.CONSTANT);
        IRInstruction in = new IRConstantAssignment(temp, sl.getValString());
        irf.addInstruction(in);
        return temp;
    }
    public Temp visit (FloatLiteral fl, IRFunction irf) {
        Temp temp = irf.temps.generateTemp(new FloatType(), fl.getValString(),TempClass.CONSTANT);
        IRInstruction in = new IRConstantAssignment(temp, fl.getValString());
        irf.addInstruction(in);
        return temp;
    }
    public Temp visit (CharacterLiteral cl, IRFunction irf) {
        Temp temp = irf.temps.generateTemp(new CharType(), cl.getValString(),TempClass.CONSTANT);
        IRInstruction in = new IRConstantAssignment(temp, cl.getValString());
        irf.addInstruction(in);
        return temp;
    }
    public Temp visit (BooleanLiteral bl, IRFunction irf) {
        Temp temp = irf.temps.generateTemp(new BooleanType(), bl.getValString(),TempClass.CONSTANT);
        IRInstruction in = new IRConstantAssignment(temp, bl.getValString());
        irf.addInstruction(in);
        return temp;
    }
}
