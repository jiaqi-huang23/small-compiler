package ir;

import java.util.HashMap;

public class OpRepTable {
    HashMap<Ops,String> opTable;

    public OpRepTable() {
        opTable = new HashMap<Ops, String>();
        
        opTable.put(Ops.UnaryOps.NOT, "!");
        opTable.put(Ops.BinaryOps.ADD, "+");
        opTable.put(Ops.BinaryOps.MULTIPLY, "*");
        opTable.put(Ops.BinaryOps.SUBTRACT, "-");
        opTable.put(Ops.BinaryOps.EQUAL, "==");
        opTable.put(Ops.BinaryOps.LESSTHAN, "<");
    }

    public String getOpRep (Ops op) {
        String s = "";
        try {
            s = opTable.get(op);

        } catch (Exception e) {
            System.out.println("Operator not found.");
        }
        return s;
    }

}
