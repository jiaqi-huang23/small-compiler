package ast;

import type.*;
import semantics.*;
import ir.*;

public class VariableDeclaration extends ASTNode {
    Identifier id;
    TypeNode tn;

    //constructor
    public VariableDeclaration(TypeNode t, Identifier i) {
        id = i;
        tn = t;
    }

    public Identifier getVarId() {
        return id;
    }

    public String getVarName() {
        return id.name;
    }

    public TypeNode getVarType() {
        return tn;
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
