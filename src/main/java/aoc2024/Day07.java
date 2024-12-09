package aoc2024;

import java.util.Arrays;
import java.util.List;

public class Day07 extends Puzzle2024 {

    public Day07() {
        super(7);
    }

    public static void main(final String[] args) {
        new Day07().printSolutions();
    }

    private static long getSumOfTrueEquations(final List<String> input, final List<MathFunction> operators) {
        return input.stream()
                .map(Day07::convertToEquation)
                .filter(eq -> keepTrueEquations(eq, operators))
                .mapToLong(Equation::result)
                .sum();
    }

    private static Equation convertToEquation(final String inputLine) {
        final var result = Long.parseLong(inputLine.split(": ")[0]);

        final var numbers = Arrays.stream(inputLine.split(": ")[1].split(" "))
                .map(Long::parseLong)
                .toList();

        return new Equation(result, numbers);
    }

    private static boolean keepTrueEquations(final Equation equation, final List<MathFunction> operators) {
        final int operatorsCount = operators.size();
        final int combinationsNumber = (int) Math.pow(operatorsCount, equation.numbers().size() - 1);

        for (int i = 0; i < combinationsNumber; i++) {
            long result = equation.numbers().get(0);

            for (int j = 1; j < equation.numbers().size(); j++) {
                final int combinationsDividedJMinus1TimesByOperatorsCount = (int) Math.round(combinationsNumber * Math.pow(operatorsCount, -(j - 1)));
                final int combinationsDividedJTimesByOperatorCount = combinationsDividedJMinus1TimesByOperatorsCount / operatorsCount;

                final MathFunction operator = operators.get((i % combinationsDividedJMinus1TimesByOperatorsCount) / combinationsDividedJTimesByOperatorCount);
                final long number = equation.numbers().get(j);

                result = operator.apply(result, number);
            }

            if (result == equation.result()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfTrueEquations(
                getInputLines(),
                List.of(MathFunction.ADD, MathFunction.MUL)
        ));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getSumOfTrueEquations(
                getInputLines(),
                List.of(MathFunction.ADD, MathFunction.MUL, MathFunction.CONCAT)
        ));
    }

    private enum MathFunction {
        ADD, MUL, CONCAT;

        public long apply(final long a, final long b) {
            return switch (this) {
                case ADD -> a + b;
                case MUL -> a * b;
                case CONCAT -> Long.parseLong("" + a + b);
            };
        }
    }

    private record Equation(long result, List<Long> numbers) {
    }
}
