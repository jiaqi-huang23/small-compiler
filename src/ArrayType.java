package type;

import ast.*;

public class ArrayType extends Type {
    TypeNode t;
    IntegerLiteral n;
    public Type type;


    public ArrayType(TypeNode tt, Expression nn) {
        t = tt;
        n = (IntegerLiteral)nn;
        type = t.type;
    }

    public TypeNode getNodeOfTypeOfArray() {
        return t;
    }

    public int getSize() {
        return n.getVal();
    }

    public String toString() {
        String str = t.type + "[" + Integer.toString(n.getVal()) + "]";
        return str;
    }
}
