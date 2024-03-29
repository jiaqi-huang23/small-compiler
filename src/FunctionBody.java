package ast;

import semantics.*;
import type.*;
import ir.*;

public class FunctionBody extends ASTNode {

     public VariableDeclarationList vdl;
     public StatementList stml;


    public FunctionBody(VariableDeclarationList v,StatementList s){
        vdl = v;
        stml = s;
    }

    //two kinds of nodes in functionbody : VariableDeclaration or Statement
    public void addElement(VariableDeclaration vd) {
        vdl.addElement(vd);
    }

    public void addElement (Statement s) {
        stml.addElement(s);
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
