package ast;

import java.util.Vector;
import type.*;
import semantics.*;
import ir.*;

public class FunctionCallExpression extends Expression {
     Vector arglist;
     IdentifierReference id;

    public FunctionCallExpression() {
        arglist = new Vector();
        id = null;
    }

    public void setId(IdentifierReference i) {
        id = i;
    }

    public void addElement(Expression expr){
        arglist.addElement(expr);
    }

    public IdentifierReference getFuncId() {
        return id;
    }

    public int argSize() {
        return arglist.size();
    }

    public Vector getArglist() {
        return arglist;
    }

    public int getLine() {
        return id.getLine();
    }

    public int getOffset() {
        return id.getOffset();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
