package ir;

import java.util.Vector;
import java.util.Enumeration;

public class IRFunctionCallInstruction implements IRInstruction {
    String funcName;
    Vector<Temp> args;
    Temp returnVal;

    public IRFunctionCallInstruction (Temp ret, String name, Vector<Temp> arglist) {
        returnVal = ret;
        funcName = name;
        args = arglist;
    }

    public String toString() {
        String str = "    ";
        if (returnVal != null) {
            str += returnVal + " := ";
        }
        str += "CALL " + funcName + "(";
        Enumeration<Temp> tempEnum = args.elements();
        while (tempEnum.hasMoreElements()) {
            str += tempEnum.nextElement();
        }
        str += ")";

        return str;
    }

}
