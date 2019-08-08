package ast;

import java.util.Vector;
import semantics.*;
import type.*;
import ir.*;

public class Block extends ASTNode {
    StatementList l;

    public Block (StatementList s) {
        l = s;
    }

    public StatementList getStatementList() {
        return l;
    }

    public int size() {
        return l.size();
    }

    public void accept (Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeCheckingVisitor v) throws SemanticException {
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
