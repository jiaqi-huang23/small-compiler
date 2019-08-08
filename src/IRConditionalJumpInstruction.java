package ir;

public class IRConditionalJumpInstruction implements IRInstruction {
    IRLabelInstruction label;
    Temp temp;

    public IRConditionalJumpInstruction (Temp t, IRLabelInstruction l) {
        label = l;
        temp = t;
    }
    public String toString() {
        String str = "    ";
        String l = label.toString();
        str += "IF " + temp + " GOTO " + l;
        return str;
    }
}
