package ast;

import semantics.*;
import type.*;
import ir.*;

public class AssignmentStatement extends Statement {
	 public IdentifierReference id;
	 public Expression expr;

	public AssignmentStatement (IdentifierReference i, Expression e) {
		id = i;
		expr = e;
	}

	public void accept (Visitor v) {
		v.visit(this);
	}

    public IdentifierReference getIdr() {
        return id;
    }

    public Expression getExpr() {
        return expr;
    }

    public Type accept (TypeVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
