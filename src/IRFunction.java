package ir;

import type.*;
import ast.*;
import java.util.*;


public class IRFunction {
    public TempFactory temps;
    public String name;
    public String signiture;
    public int localNum;
    public Vector<IRInstruction> instructions;

    public IRFunction(Function f) {
        temps = new TempFactory();
        name = f.getFuncName();
        instructions = new Vector<IRInstruction>();
        localNum = 0;
        // create signiture
        String para = "";
        TypeLetterTable tt = new TypeLetterTable();
        //get para Types
        Enumeration paras = f.funcDecl.getParaList().paralist.elements();
        while (paras.hasMoreElements()) {
            Type t = ((FormalParameter)paras.nextElement()).getParaType().type;
            para += tt.getTypeLetter(t);
        }
        if (name.equals("main")) {
            para += "[Ljava/lang/String;";
        }
        String returnTypeLetter = tt.getTypeLetter(f.getReturnType());
        //set signiture
        signiture = name + "(" + para + ")" + returnTypeLetter;
    }

    public String getSigniture() {
        return signiture;
    }

    public void addInstruction(IRInstruction in) {
        instructions.add(in);
    }

    public void printTempDecls() {
        temps.printTempDeclaration();
    }

    public void printInstructions() {
        Enumeration<IRInstruction> insEnum = Collections.enumeration(instructions);
        while(insEnum.hasMoreElements()) {
            System.out.println(insEnum.nextElement()+ ";");
        }
    }
}
