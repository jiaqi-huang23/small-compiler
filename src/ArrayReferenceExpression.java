package ast;

import type.*;
import semantics.*;
import ir.*;

public class ArrayReferenceExpression extends Expression {
    public Expression expr;
    public IdentifierReference id;

    public ArrayReferenceExpression (IdentifierReference i, Expression e) {
        id = i;
        expr = e;
    }

    public void accept (Visitor v) {
        v.visit(this);
    }

    public IdentifierReference getId() {
        return id;
    }

    public Expression getIndexExpr() {
        return expr;
    }

    public Expression getExpr() {
        return expr;
    }

    public int getLine() {
        return expr.getLine();
    }

    public int getOffset() {
        return expr.getOffset();
    }

    public Type accept(TypeVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
