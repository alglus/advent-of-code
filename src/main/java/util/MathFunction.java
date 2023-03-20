package util;

import java.util.Arrays;

public enum MathFunction {
    ADD('+'), SUB('-'), MUL('*'), DIV('/');

    private final char operator;

    MathFunction(char operator) {
        this.operator = operator;
    }

    public static MathFunction fromOperator(char operator) {
        return Arrays.stream(values())
                .filter(function -> function.operator == operator)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unkown operator found!"));
    }
}
