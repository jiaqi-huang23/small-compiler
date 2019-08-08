package ir;

public class IRReturnInstruction implements IRInstruction {
    Temp ret;

    public IRReturnInstruction(Temp r) {
        ret = r;
    }

    public String toString() {
        String str = "    RETURN ";
        if (ret != null) {
            str += ret;
        }
        return str;
    }
}
