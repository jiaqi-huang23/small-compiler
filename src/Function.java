package ast;

import semantics.*;
import type.*;
import ir.*;


public class Function extends ASTNode {
    public FunctionDeclaration funcDecl;
    public FunctionBody funcBody;

    public Function(FunctionDeclaration fd, FunctionBody fb) {
        funcDecl = fd;
        funcBody = fb;
    }

    public String getFuncName(){
        String funcName = funcDecl.funcName.name;
        return funcName;
    }

    public Type getReturnType() {
        return funcDecl.getReturnType().type;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v)throws SemanticException{
        return v.visit(this);
    }

    public Temp accept(TempVisitor v) {
        return v.visit(this);
    }
}
