package ast;

import type.*;
import semantics.*;
import ir.*;

public class BooleanLiteral extends Expression {
    boolean val;
    int line;
    int offset;

    public BooleanLiteral(boolean v, int l, int o) {
        val = v;
        line = l;
        offset = o;
    }

    public String getValString() {
        if (val == true) {
            return "TRUE";
        } else {
            return "FALSE";
        }
    }

    public int getLine(){
        return line;
    }

    public int getOffset(){
        return offset;
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
