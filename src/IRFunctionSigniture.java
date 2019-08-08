package ir;

import type.*;
import java.util.*;

public class IRFunctionSigniture {
    Vector<Temp> paralist;
    String funcName;
    Temp ret;

    public IRFunctionSigniture(String name, Vector<Temp> paras, Temp re) {
        paralist = paras;
        funcName = name;
        ret = re;
    }

    public String toString() {
        String sig = "";
        String para = "";
        TypeLetterTable tt = new TypeLetterTable();
        //get para Types
        Enumeration<Temp> paras = paralist.elements();
        while (paras.hasMoreElements()) {
            Type t = paras.nextElement().type;
            para += tt.getTypeLetter(t);
        }
        String returnTypeLetter = "";
        if (ret != null) {
             returnTypeLetter = tt.getTypeLetter(ret.type);
        } else {
            returnTypeLetter = "V";
        }
        //set signiture
        sig = funcName + "(" + para + ")" + returnTypeLetter;

        return sig;
    }
}
