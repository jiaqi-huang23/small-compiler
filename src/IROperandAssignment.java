package ir;

public class IROperandAssignment extends IRAssignmentInstruction {
    Temp lhs;
    Temp rhs;

    public IROperandAssignment (Temp t1, Temp t2) {
        lhs = t1;
        rhs = t2;
    }

    public String toString() {
        String str = "    ";
        str += lhs + " := " + rhs;
        return str;
    }
}
