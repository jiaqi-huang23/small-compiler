package ir;

public class IRArrayReferenceAssignment extends IRAssignmentInstruction {
    Temp dest;
    Temp rhs;
    Temp index;

    public IRArrayReferenceAssignment (Temp d, Temp r, Temp i) {
        dest = d;
        index = i;
        rhs = r;
    }

    public String toString() {
        String str = "    ";
        str += dest + " := " + rhs + "[" + index + "]";
        return str;
    }
}
