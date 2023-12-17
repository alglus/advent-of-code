package aoc2023;

import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;

public class Day09 extends Puzzle2023 {

    public Day09() {
        super(9);
    }

    public static void main(String[] args) {
        new Day09().printSolutions();
    }

    private long getSumOfExtrapolatedValues(List<String> input, Function<List<Long>, Long> getExtrapolatedValue) {
        return input.stream()
                .map(StringUtil::splitByWhitespaces)
                .map(stringArray -> Arrays.stream(stringArray)
                        .mapToLong(Long::parseLong)
                        .boxed().toList())
                .mapToLong(getExtrapolatedValue::apply)
                .sum();
    }

    private long getForwardExtrapolatedValue(List<Long> sequence) {
        var subsequences = calculateAllSubsequences(sequence);
        return calculateExtrapolatedValue(subsequences, addFowardValue(subsequences));
    }

    private long getBackwardExtrapolatedValue(List<Long> sequence) {
        var subsequences = calculateAllSubsequences(sequence);
        return calculateExtrapolatedValue(subsequences, addBackwardValue(subsequences));
    }

    private long calculateExtrapolatedValue(List<List<Long>> subsequences, LongBinaryOperator addValue) {
        return LongStreamEx.rangeClosed(0, subsequences.size() - 2)
                .reverseSorted()
                .reduce(0, addValue);
    }

    private LongBinaryOperator addFowardValue(List<List<Long>> subsequences) {
        return (extrapolatedValueFromSequenceBelow, index) -> {
            var currentSubsequence = subsequences.get((int) index);
            var lastValueOfCurrentSubsequence = currentSubsequence.get(currentSubsequence.size() - 1);
            return extrapolatedValueFromSequenceBelow + lastValueOfCurrentSubsequence;
        };
    }

    private LongBinaryOperator addBackwardValue(List<List<Long>> subsequences) {
        return (extrapolatedValueFromSequenceBelow, index) -> {
            var currentSubsequence = subsequences.get((int) index);
            var firstValueOfCurrentSubsequence = currentSubsequence.get(0);
            return firstValueOfCurrentSubsequence - extrapolatedValueFromSequenceBelow;
        };
    }

    private List<List<Long>> calculateAllSubsequences(List<Long> sequence) {
        List<List<Long>> subsequences = new ArrayList<>();
        subsequences.add(sequence);

        do {
            var lastSubsequence = subsequences.get(subsequences.size() - 1);
            var newSubsequence = StreamEx.of(lastSubsequence)
                    .pairMap((i1, i2) -> i2 - i1)
                    .toList();
            subsequences.add(newSubsequence);
        } while (lastSubsequenceNotAllZeros(subsequences));

        return subsequences;
    }

    private boolean lastSubsequenceNotAllZeros(List<List<Long>> subsequences) {
        return subsequences.get(subsequences.size() - 1).stream()
                .anyMatch(i -> i != 0);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfExtrapolatedValues(getInputLines(), this::getForwardExtrapolatedValue));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getSumOfExtrapolatedValues(getInputLines(), this::getBackwardExtrapolatedValue));
    }
}
