package ir;

import java.util.*;
import type.*;

public class CodeGenerator {
    String filename;
    Vector<IRFunction> irfuncs;
    int lableCount;
    TypeLetterTable letterTable;
    HashMap<Ops,String> binaryOpMap;

    public CodeGenerator(String file, Vector<IRFunction> irf){
        filename = file;
        irfuncs = irf;
        lableCount = 0;
        letterTable = new TypeLetterTable();
        initOpMap();
        handleHeader();
        handleBody();
    }

    void handleHeader() {
        System.out.println(".class " + filename);
        System.out.println(".super java/lang/Object");
    }

    void handleBody() {
        Enumeration<IRFunction> funcEnum = irfuncs.elements();
        while(funcEnum.hasMoreElements()) {
            handleFunction(funcEnum.nextElement());
        }
    }

    void initOpMap() {
        binaryOpMap = new HashMap<Ops,String>();
        binaryOpMap.put(Ops.BinaryOps.ADD, "add");
        binaryOpMap.put(Ops.BinaryOps.SUBTRACT, "sub");
        binaryOpMap.put(Ops.BinaryOps.MULTIPLY, "mul");
        binaryOpMap.put(Ops.BinaryOps.EQUAL, "eq");
        binaryOpMap.put(Ops.BinaryOps.LESSTHAN, "lt");
    }

    void handleFunction(IRFunction func) {
        System.out.println();
        System.out.println(".method public static " + func.signiture);
        System.out.println(".limit locals " + func.localNum);
        System.out.println(".limit stack 256");
        //initialize locals
        //initLocals(func);
        //go through instructions
        Enumeration<IRInstruction> instrEnum = func.instructions.elements();
        while (instrEnum.hasMoreElements()) {
            IRInstruction instr = instrEnum.nextElement();
            System.out.println(";        " + instr.toString());
            handleInstruction(instr);
        }
        System.out.println(".end method");
    }

    void initLocals(IRFunction func) {
        Enumeration<Temp> tempEnum = func.temps.locals.elements();
        while(tempEnum.hasMoreElements()) {
            Temp temp = tempEnum.nextElement();
            if (temp.type instanceof StringType || temp.type instanceof ArrayType) {
                System.out.println("    aconst_null");
            } else if (temp.type instanceof FloatType) {
                ldcFloat(0);
            } else {
                ldcVal(0);
            }
            store(temp);
        }
    }

    //handle different irinstructions
    void handleInstruction(IRInstruction instr) {
        if (instr instanceof IRConstantAssignment) {
            handleConstantAssignment((IRConstantAssignment)instr);
        } else if (instr instanceof IROperandAssignment) {
            handleOperandAssignment((IROperandAssignment)instr);
        } else if (instr instanceof IRUnaryOpAssignment) {
            handleUnaryOpAssignment((IRUnaryOpAssignment)instr);
        } else if (instr instanceof IRBinaryOpAssignment) {
            handleBinaryOpAssignment((IRBinaryOpAssignment)instr);
        } else if (instr instanceof IRArrayAssignAssignment) {
            handleArrayAssignAssignment((IRArrayAssignAssignment)instr);
        } else if (instr instanceof IRArrayCreationAssignment) {
            handleArrayCreationAssignment((IRArrayCreationAssignment)instr);
        } else if (instr instanceof IRArrayReferenceAssignment) {
            handleArrayReferenceAssignment((IRArrayReferenceAssignment)instr);
        } else if (instr instanceof IRFunctionCallInstruction) {
            handleFunctionCall((IRFunctionCallInstruction)instr);
        } else if (instr instanceof IRPrintInstruction) {
            handlePrint((IRPrintInstruction)instr);
        } else if (instr instanceof IRPrintlnInstruction) {
            handlePrintln((IRPrintlnInstruction)instr);
        } else if (instr instanceof IRReturnInstruction) {
            handleReturn((IRReturnInstruction)instr);
        } else if (instr instanceof IRLabelInstruction) {
            handleLabel((IRLabelInstruction)instr);
        } else if (instr instanceof IRJumpInstruction) {
            handleJump((IRJumpInstruction)instr);
        } else if (instr instanceof IRConditionalJumpInstruction) {
            handleCondJump((IRConditionalJumpInstruction)instr);
        }
            //... other
         else {
            //report error
        }
    }
    void handleConstantAssignment(IRConstantAssignment instr) {
        ldc(instr);
        store(instr.temp);
    }

    void handleOperandAssignment(IROperandAssignment instr) {
        load(instr.rhs);
        store(instr.lhs);
    }

    void handleUnaryOpAssignment(IRUnaryOpAssignment instr) {
        //in current language, only "!" - Ops.UnaryOps.NOT
        if (instr.operator == Ops.UnaryOps.NOT) {
            load(instr.orig);
            ldcVal(1);
            System.out.println("    ixor");
            store(instr.dest);
        }
    }

    void handleBinaryOpAssignment(IRBinaryOpAssignment instr) {
        load(instr.lhs);
        load(instr.rhs);
        if (instr.operator == Ops.BinaryOps.ADD) {
            numericOp(instr.dest, Ops.BinaryOps.ADD);
        } else if (instr.operator == Ops.BinaryOps.MULTIPLY) {
            numericOp(instr.dest, Ops.BinaryOps.MULTIPLY);
        } else if (instr.operator == Ops.BinaryOps.SUBTRACT) {
            numericOp(instr.dest, Ops.BinaryOps.SUBTRACT);
        } else if (instr.operator == Ops.BinaryOps.EQUAL) {
            compareOp(instr.dest, instr.lhs, Ops.BinaryOps.EQUAL);
        } else if (instr.operator == Ops.BinaryOps.LESSTHAN) {
            compareOp(instr.dest, instr.lhs, Ops.BinaryOps.LESSTHAN);
        } else {
            //report error
        }
    }

    void handleArrayAssignAssignment(IRArrayAssignAssignment instr) {
        load(instr.lhs);
        load(instr.index);
        load(instr.rhs);
        storeArray(instr.rhs);
    }

    void handleArrayCreationAssignment(IRArrayCreationAssignment instr) {
        ldcVal(instr.size);
        Type type = ((ArrayType)(instr.arr.type)).type;
        String t = "";
        String typestr = "";
        if (type instanceof StringType) {
            t = "a";
            typestr = "java/lang/String";
        } else if (type instanceof FloatType) {
            typestr = "float";
        } else {
            typestr = "int";
        }
        System.out.println("    " + t + "newarray " + typestr);
        System.out.println("    astore " + instr.arr.number);
    }

    void handleArrayReferenceAssignment(IRArrayReferenceAssignment instr) {
        load(instr.rhs);
        load(instr.index);
        loadArray(instr.dest);
        store(instr.dest);
    }


    void handleFunctionCall(IRFunctionCallInstruction instr) {
        Enumeration<Temp> argEnum = instr.args.elements();
        while (argEnum.hasMoreElements()) {
            Temp temp = argEnum.nextElement();
            load(temp);
        }
        IRFunctionSigniture signiture = new IRFunctionSigniture(instr.funcName, instr.args, instr.returnVal);
        System.out.println("    invokestatic " + filename + "/" + signiture);
        if (instr.returnVal != null) {
            store(instr.returnVal);
        }
    }

    void handlePrint(IRPrintInstruction instr) {
        System.out.println("    getstatic java/lang/System/out Ljava/io/PrintStream;");
        load(instr.temp);
        System.out.println("    invokevirtual java/io/PrintStream/print(" + letterTable.getTypeLetter(instr.temp.type) + ")V");
    }

    void handlePrintln(IRPrintlnInstruction instr) {
        System.out.println("    getstatic java/lang/System/out Ljava/io/PrintStream;");
        load(instr.temp);
        System.out.println("    invokevirtual java/io/PrintStream/println(" + letterTable.getTypeLetter(instr.temp.type) + ")V");
    }

    void handleReturn(IRReturnInstruction instr) {
        Temp ret = instr.ret;
        if (ret == null) {
            System.out.println("    return");
        } else {
            load(ret);
            if (ret.type instanceof FloatType) {
                System.out.println("    freturn ");
            } else if (ret.type instanceof StringType || ret.type instanceof ArrayType) {
                System.out.println("    areturn ");
            } else  {
                System.out.println("    ireturn ");
            }
        }
    }

    void handleLabel(IRLabelInstruction instr) {
        System.out.println(instr + ":");
    }

    void handleJump(IRJumpInstruction instr) {
        goTo(instr.label.toString());
    }

    void handleCondJump(IRConditionalJumpInstruction instr) {
        load(instr.temp);
        System.out.println("    ifne " + instr.label.toString());
    }
    //instructions
    void ldc(IRConstantAssignment instr) {
        Type t = instr.temp.type;
        int n = 0;
        String valstr = instr.constVal;
        if (t instanceof CharType) {
            char c = valstr.charAt(1);
            //System.out.println("char is " + c);
            n = c;
            System.out.println("    ldc " + n);
        } else if (t instanceof BooleanType){
            if (valstr.equals("TRUE")) {
                n = 1;
            }
            System.out.println("    ldc " + n);
        } else {
            System.out.println("    ldc " + valstr);
        }
    }

    void ldcVal(int val) {
        System.out.println("    ldc " + val);
    }

    void ldcFloat(float val) {
        System.out.println("    ldc " + (val+0.0));
    }

    void load(Temp temp) {
        Type t = temp.type;
        if (t instanceof StringType || t instanceof ArrayType) {
            System.out.println("    aload " + temp.number);
        } else if (t instanceof FloatType) {
            System.out.println("    fload " + temp.number);
        } else {
            System.out.println("    iload " + temp.number);
        }
    }

    void store(Temp temp) {
        Type t = temp.type;
        if (t instanceof StringType || t instanceof ArrayType) {
            System.out.println("    astore " + temp.number);
        } else if (t instanceof FloatType) {
            System.out.println("    fstore " + temp.number);
        } else {
            System.out.println("    istore " + temp.number);
        }
    }

    void loadArray(Temp temp) {
        String t = "";
        if (temp.type instanceof FloatType) {
            t = "f";
        } else if (temp.type instanceof StringType || temp.type instanceof ArrayType) {
            t = "a";
        } else {
            t = "i";
        }
        System.out.println("    " + t + "aload");
    }

    void storeArray(Temp temp) {
        String t = "";
        if (temp.type instanceof FloatType) {
            t = "f";
        } else if (temp.type instanceof StringType || temp.type instanceof ArrayType) {
            t = "a";
        } else {
            t = "i";
        }
        System.out.println("    " + t + "astore");
    }

    //do add, multiply, substract
    void numericOp(Temp temp, Ops op) {
        if(temp.type instanceof FloatType) {
            System.out.println("    f" + binaryOpMap.get(op));
        } else {
            System.out.println("    i" + binaryOpMap.get(op));
        }
        store(temp);
    }

    void compareOp(Temp temp, Temp lhs, Ops op) {
        if (lhs.type instanceof StringType) {
            System.out.println("    invokevirtual java/lang/String/compareTo(Ljava/lang/String;)I");
        } else {
            if(lhs.type instanceof FloatType) {
                System.out.println("    fsub");
            } else {
                System.out.println("    isub");
            }
        }
        String label_1 = getNewLabel();
        String label_2 = getNewLabel();
        branch(op,label_1);
        ldcVal(0);
        goTo(label_2);
        addLabel(label_1);
        ldcVal(1);
        addLabel(label_2);
        store(temp);
    }

    void branch(Ops op, String label) {
        System.out.println("    if" + binaryOpMap.get(op) + " " + label);
    }

    void goTo(String label) {
        System.out.println("    goto " + label);
    }

    String getNewLabel() {
        String label = "L_" + lableCount;
        lableCount++;
        return label;
    }

    void addLabel(String label) {
        System.out.println(label + ":");
    }

}
