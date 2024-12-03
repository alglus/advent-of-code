package aoc2024;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day03 extends Puzzle2024 {

    public Day03() {
        super(3);
    }

    public static void main(final String[] args) {
        new Day03().printSolutions();
    }

    private static long calculateSumOfAllMultiplications(final String input) {
        final String multiplicationRegex = "mul\\((\\d+),(\\d+)\\)";

        return Pattern.compile(multiplicationRegex)
                .matcher(input)
                .results()
                .map(match -> Stream
                        .of(match.group(1), match.group(2))
                        .map(Integer::parseInt))
                .mapToInt(factors -> factors.reduce(1, Math::multiplyExact))
                .sum();
    }

    private static long calculateSumOfAllMultiplicationsExcludingTheDontInstructions(final String input) {
        final var clearedInput = keepOnlyTheDoInstructions(input);
        return calculateSumOfAllMultiplications(clearedInput);
    }

    private static String keepOnlyTheDoInstructions(final String input) {
        final String instructionGroupRegex = "(do\\(\\)|don't\\(\\))(.+?)(?=do\\(\\)|don't\\(\\)|$)";

        return Pattern.compile(instructionGroupRegex)
                .matcher("do()" + input)
                .results()
                .map(match -> List.of(match.group(1), match.group(2)))
                .filter(instructionGroup -> instructionGroup.get(0).equals("do()"))
                .map(instructionGroup -> instructionGroup.get(1))
                .collect(Collectors.joining());
    }

    @Override
    public String solvePart1() {
        return String.valueOf(calculateSumOfAllMultiplications(getInputAsString()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(calculateSumOfAllMultiplicationsExcludingTheDontInstructions(getInputAsString()));
    }
}
