package ast;

import type.*;
import semantics.*;
import ir.*;

public class ArrayAssignmentStatement extends Statement {
	public IdentifierReference id;
    public Expression index;
    public Expression expr;

	public ArrayAssignmentStatement (IdentifierReference idr, Expression i, Expression e) {
		id = idr;
        index = i;
        expr = e;
	}

    public IdentifierReference getId() {
        return id;
    }

    public Expression getIndexExpr() {
        return index;
    }

    public Expression getExpr() {
        return expr;
    }
	public void accept (Visitor v) {
		v.visit(this);
	}

    public Type accept(TypeVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
