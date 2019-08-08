package ir;

public class TempDeclaration {
    Temp temp;

    public TempDeclaration(Temp t){
        temp = t;
    }

    public String toString() {
        TypeLetterTable tt = new TypeLetterTable();
        String str = "    TEMP " + temp.number + ":";
        //padding to left
        str += String.format("%1$-4s",tt.getTypeLetter(temp.type));
        if (temp.tclass == TempClass.PARAMETER) {
            str = str + "    [P(\"" + temp.name + "\")]";
        } else if (temp.tclass == TempClass.LOCAL) {
            str = str + "    [L(\"" + temp.name + "\")]";
        } else {
            //do nothing
        }
        return str;
    }

}
