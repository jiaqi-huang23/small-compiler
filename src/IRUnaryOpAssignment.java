package ir;

public class IRUnaryOpAssignment extends IRAssignmentInstruction {
    Temp dest;
    Ops operator;
    Temp orig;

    public IRUnaryOpAssignment(Temp d, Ops o, Temp ori) {
        dest = d;
        operator = o;
        orig = ori;
    }

    public String toString() {
        TypeLetterTable tt = new TypeLetterTable();
        OpRepTable ot = new OpRepTable();
        String opStr = tt.getTypeLetter(orig.type) + ot.getOpRep(operator);
        String str = "    ";
        str += dest + " := " + opStr + " " + orig;
        return str;
    }
}
