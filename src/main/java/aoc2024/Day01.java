package aoc2024;

import one.util.streamex.IntStreamEx;
import util.SortedList;
import util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01 extends Puzzle2024 {

    public Day01() {
        super(1);
    }

    public static void main(final String[] args) {
        new Day01().printSolutions();
    }

    @Override
    public String solvePart1() {
        return String.valueOf(calculateTotalDistanceBetweenLists(getInputLines()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(calculateSimilarityScore(getInputLines()));
    }

    private long calculateTotalDistanceBetweenLists(final List<String> input) {
        final List<Integer> leftList = new SortedList<>();
        final List<Integer> rightList = new SortedList<>();

        input.stream()
                .map(StringUtil::splitByWhitespaces)
                .forEach(distances -> {
                    leftList.add(Integer.valueOf(distances[0]));
                    rightList.add(Integer.valueOf(distances[1]));
                });

        return IntStreamEx.range(0, input.size())
                .mapToLong(i -> Math.abs(leftList.get(i) - rightList.get(i)))
                .sum();
    }

    private long calculateSimilarityScore(final List<String> input) {
        final List<Integer> leftList = new ArrayList<>();
        final Map<Integer, Integer> distancesCountInRightList = new HashMap<>();

        input.stream()
                .map(StringUtil::splitByWhitespaces)
                .forEach(distances -> {
                    leftList.add(Integer.valueOf(distances[0]));
                    distancesCountInRightList.merge(Integer.valueOf(distances[1]), 1, Integer::sum);
                });

        return leftList.stream()
                .mapToLong(distance -> (long) distance * distancesCountInRightList.getOrDefault(distance, 0))
                .sum();
    }
}
