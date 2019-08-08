package ast;

import semantics.*;
import type.*;
import ir.*;

public abstract class Expression extends ASTNode {
    public abstract Type accept (TypeVisitor v) throws SemanticException;
    public abstract void accept (Visitor v);
    public abstract Temp accept(TempVisitor v, IRFunction f);
    public abstract int getLine();
    public abstract int getOffset();
}
