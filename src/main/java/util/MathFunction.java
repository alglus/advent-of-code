package util;

public enum MathFunction {
    ADD('+'), SUB('-'), MUL('*'), DIV('/');

    private final char operator;

    MathFunction(char operator) {
        this.operator = operator;
    }

    public static MathFunction fromOperator(char operator) {
        for (MathFunction function : values()) {
            if (function.operator == operator)
                return function;
        }

        throw new IllegalArgumentException("Unkown operator found!");
    }
}
