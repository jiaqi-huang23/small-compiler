package ast;

import semantics.*;
import type.*;
import ir.*;

public class PrintlnStatement extends Statement {
    Expression expr;
    int line;
    int offset;

    public PrintlnStatement (Expression e, int l, int o) {
        expr = e;
        line = l;
        offset = o;
    }

    public int getLine() {
        return line;
    }

    public int getOffset() {
        return offset;
    }


    public Expression getPrintlnExpr() {
        return expr;
    }

    public void accept (Visitor v) {
        v.visit(this);
    }

    public Type accept (TypeVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
