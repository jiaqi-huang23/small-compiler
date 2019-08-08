package ir;

public class IRLabelInstruction implements IRInstruction{
    static int labelCount;
    int number;

    public IRLabelInstruction() {
        number = labelCount;
        labelCount++;
    }

    public String toString() {
        return "L" + number;
    }

    public void resetLable() {
        labelCount = 0;
    }
}
