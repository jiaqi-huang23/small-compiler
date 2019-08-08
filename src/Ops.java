package ir;

public interface Ops {
    public enum UnaryOps implements Ops {
        NOT
    }
    public enum BinaryOps implements Ops{
        ADD,
        SUBTRACT,
        MULTIPLY,
        LESSTHAN,
        EQUAL
    }
}
