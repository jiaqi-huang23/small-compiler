package ir;

public class IRBinaryOpAssignment extends IRAssignmentInstruction {
    Temp dest;
    Temp lhs;
    Temp rhs;
    Ops operator;

    public IRBinaryOpAssignment(Temp d, Temp l, Temp r, Ops op) {
        dest = d;
        lhs = l;
        rhs = r;
        operator = op;
    }

    public String toString() {
        String str = "    ";
        TypeLetterTable tt = new TypeLetterTable();
        OpRepTable ot = new OpRepTable();
        String opstr = tt.getTypeLetter(lhs.type) + ot.getOpRep(operator);
        str += dest + " := " + lhs + " " + opstr + " " + rhs;
        return str;
    }
}
