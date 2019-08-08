package ir;

import java.util.HashMap;
import java.util.Vector;
import java.util.Enumeration;
import type.*;

public class TempFactory {
    static int count;
    HashMap<String, Temp> tempMap; //<variable name /constant value, temp>
    Vector<TempDeclaration> tempDecls;
    Vector<Temp> locals;

    public TempFactory() {
        tempMap = new HashMap<String,Temp>();
        tempDecls = new Vector<TempDeclaration>();
        locals = new Vector<Temp>();
    }

    public void addTemp(Temp t) {
        tempMap.put(t.name,t);
        addTempDecl(t);
        locals.add(t);
        count++;
    }

    public void addTempDecl(Temp t) {
        tempDecls.add(new TempDeclaration(t));
    }

    //for intermediates
    public Temp generateTemp (Type t) {
         Temp temp = new Temp(count,t);
         addTemp(temp);
         return temp;
    }

    //for variables and constants
    public Temp generateTemp (Type t, String s, TempClass c) {
        Temp temp = new Temp(count, s, t, c);
        addTemp(temp);
        return temp;
    }

    public Temp lookupTemp(String name) {
        return tempMap.get(name);
    }

    public String getTempRep(Temp temp) {
        return null;
    }

    public void printTempDeclaration() {
        Enumeration<TempDeclaration> tempDeclEnum = tempDecls.elements();
        while(tempDeclEnum.hasMoreElements()) {
            System.out.println(tempDeclEnum.nextElement() + ";");
        }
    }

    public void destroyFactory() {
        count = 0;
    }
}
