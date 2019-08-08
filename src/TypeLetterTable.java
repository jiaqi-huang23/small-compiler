package ir;

import type.*;
import java.util.HashMap;

// a map stores Types with its corresponding IR representation
public class TypeLetterTable {

    public String getTypeLetter(Type t) {
        if (t instanceof ArrayType) {
            Type arrType = ((ArrayType)t).getNodeOfTypeOfArray().type;
            return "[" + getBasicTypeLetter(arrType);
        } else {
            return getBasicTypeLetter(t);
        }
    }

    String getBasicTypeLetter(Type t) {
        if (t instanceof BooleanType) {
            return "Z";
        } else if (t instanceof CharType) {
            return "C";
        } else if (t instanceof IntegerType) {
            return "I";
        } else if (t instanceof FloatType) {
            return "F";
        } else if (t instanceof VoidType) {
            return "V";
        } else if (t instanceof StringType) {
            return "Ljava/lang/String;";
        } else {
            return "";
        }
    }
}
