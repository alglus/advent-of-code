package util;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MathFunction {
    ADD('+'), SUB('-'), MUL('*'), DIV('/');

    private final char operator;

    MathFunction(final char operator) {
        this.operator = operator;
    }

    public static MathFunction fromOperator(final char operator) {
        return Arrays.stream(values())
                .filter(function -> function.operator == operator)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator found!"));
    }

    public long apply(final long n1, final long n2) {
        return switch (this) {
            case ADD -> n1 + n2;
            case SUB -> n1 - n2;
            case MUL -> n1 * n2;
            case DIV -> n1 / n2;
        };
    }

    public long initialAccValue() {
        return switch (this) {
            case ADD, SUB -> 0L;
            case MUL, DIV -> 1L;
        };
    }
}
