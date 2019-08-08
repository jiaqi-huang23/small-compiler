package ir;

import type.*;

public class Temp {
    public int number;
    public String name;
    public Type type;
    public TempClass tclass;

    public Temp (int num, Type t) {  //for intermediate values
        number = num;
        name = "";
        type = t;
        tclass = TempClass.INTERMEDIATE;
    }

    public Temp (int num, String id, Type t, TempClass c) { //for parameters and locals
        number = num;
        name = id;
        type = t;
        tclass = c;
    }

    public boolean isParaOrLocal() {
        if (tclass == TempClass.PARAMETER || tclass == TempClass.LOCAL) {
            return true;
        }
        return false;
    }

    public String toString() {
        String str = "T" + number;
        return str;
    }
}
