package ir;

public class IRArrayCreationAssignment extends IRAssignmentInstruction {
    Temp arr;
    int size;

    public IRArrayCreationAssignment (Temp a, int s) {
        arr = a;
        size = s;
    }

    public String toString() {
        TypeLetterTable tt = new TypeLetterTable();
        String type = tt.getTypeLetter(arr.type);
        String str = "    " + arr + " := NEWARRAY " + type.substring(1) + " " + size;
        return str;
    }
}
