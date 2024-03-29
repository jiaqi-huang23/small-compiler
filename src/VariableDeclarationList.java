package ast;

import java.util.Vector;
import semantics.*;
import type.*;
import ir.*;

public class VariableDeclarationList extends ASTNode {
    public Vector vlist;

    //constructor
    public VariableDeclarationList() {
        vlist = new Vector();
    }

    public void addElement(VariableDeclaration vd) {
        vlist.addElement(vd);
    }

    public int size() {
        return vlist.size();
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
