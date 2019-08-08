package ir;

public class IRJumpInstruction implements IRInstruction {
    IRLabelInstruction label;

    public IRJumpInstruction (IRLabelInstruction l) {
        label = l;
    }
    public String toString() {
        String str = "    ";
        String l = label.toString();
        str += "GOTO " + l;
        return str;
    }
}
