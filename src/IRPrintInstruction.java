package ir;

public class IRPrintInstruction implements IRInstruction {
    Temp temp;

    public IRPrintInstruction (Temp t) {
        temp = t;
    }

    public String toString() {
        String str = "    ";
        TypeLetterTable tt = new TypeLetterTable();
        str += "PRINT" + tt.getTypeLetter(temp.type) + " T" + temp.number;
        return str;
    }
}
