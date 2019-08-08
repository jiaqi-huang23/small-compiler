package ir;

public class IRConstantAssignment extends IRAssignmentInstruction {
    Temp temp;
    String constVal;

    public IRConstantAssignment(Temp t, String val) {
        temp = t;
        constVal = val;
    }

    public String toString() {
        String str = "    ";
        str += temp + " := " + constVal;
        return str;
    }
}
