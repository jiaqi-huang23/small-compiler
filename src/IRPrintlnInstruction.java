package ir;

public class IRPrintlnInstruction implements IRInstruction {
    Temp temp;

    public IRPrintlnInstruction (Temp t) {
        temp = t;
    }

    public String toString() {
        String str = "    ";
        TypeLetterTable tt = new TypeLetterTable();
        str += "PRINTLN" + tt.getTypeLetter(temp.type) + " " + temp;
        return str;
    }
}
