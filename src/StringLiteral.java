package ast;

import type.*;
import semantics.*;
import ir.*;

public class StringLiteral extends Expression {
    public String str;
    int line;
    int offset;

    public StringLiteral(String s, int l, int o) {
        str = s;
        line = l;
        offset = o;
    }

    public String getValString() {
        return str;
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
