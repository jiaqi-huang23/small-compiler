package ir;

public class IRArrayAssignAssignment extends IRAssignmentInstruction {
    Temp lhs;
    Temp index;
    Temp rhs;

    public IRArrayAssignAssignment (Temp l, Temp i, Temp r) {
        lhs = l;
        index = i;
        rhs = r;
    }

    public String toString() {
        String str = "    ";
        str += lhs + "[" + index + "]" + " := " + rhs;
        return str;
    }
}
