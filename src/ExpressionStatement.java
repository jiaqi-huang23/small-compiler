package ast;

import semantics.*;
import type.*;
import ir.*;

public class ExpressionStatement extends Statement {
    public Expression expr;

    public ExpressionStatement (Expression e) {
        expr = e;
    }


    public Expression getExpr(){
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
