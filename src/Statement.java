package ast;

import type.*;
import semantics.*;
import ir.*;

public abstract class Statement {
    public abstract Type accept (TypeVisitor v) throws SemanticException;
    public abstract void accept (Visitor v);
    public abstract Temp accept (TempVisitor v, IRFunction f);
}
