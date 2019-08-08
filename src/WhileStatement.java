package ast;

import semantics.*;
import type.*;
import ir.*;

public class WhileStatement extends Statement {
    Expression expr;
    Block blk;
    int line;
    int offset;

    public WhileStatement (Expression e, Block b, int l, int o) {
        expr = e;
        blk = b;
        line = l;
        offset = o;
    }

    public Expression getCondExpr() {
        return expr;
    }

    public Block getBlock() {
        return blk;
    }

    public int getLine() {
        return line;
    }

    public int getOffset() {
        return offset;
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
