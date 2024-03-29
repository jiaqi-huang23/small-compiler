package ast;

import type.*;
import semantics.*;
import ir.*;

public class IntegerLiteral extends Expression {
    Integer val;
    int line;
    int offset;

    public IntegerLiteral(Integer i, int l, int o) {
        val = i;
        line = l;
        offset = o;
    }

    public String getValString() {
        return Integer.toString(val);
    }

    public int getVal() {
        return val.intValue();
    }

    public int getLine() {
        return line;
    }

    public int getOffset() {
        return offset;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) throws SemanticException{
        return v.visit(this);
    }

    public Temp accept(TempVisitor v, IRFunction f) {
        return v.visit(this,f);
    }
}
