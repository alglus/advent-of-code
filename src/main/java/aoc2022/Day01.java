package aoc2022;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Day01 extends Puzzle2022 {

    public Day01() {
        super(1);
    }

    public static void main(String[] args) {
        new Day01().printSolutions();
    }

    private TreeSet<Long> sumCaloriesOfEachElfAndPutInSetInReverseOrder(List<String> input) {
        TreeSet<Long> caloriesSums = new TreeSet<>(Comparator.reverseOrder());
        long caloriesSum = 0;

        for (String calories : input) {
            if (calories.isBlank()) {
                caloriesSums.add(caloriesSum);
                caloriesSum = 0;
                continue;
            }
            caloriesSum += Long.parseLong(calories);
        }

        caloriesSums.add(caloriesSum);

        return caloriesSums;
    }

    @Override
    public String solvePart1() {
        return sumCaloriesOfEachElfAndPutInSetInReverseOrder(getInputLines())
                .first()
                .toString();
    }

    @Override
    public String solvePart2() {
        return String.valueOf(
                sumCaloriesOfEachElfAndPutInSetInReverseOrder(getInputLines()).stream()
                        .limit(3)
                        .mapToLong(Long::longValue)
                        .sum()
        );
    }
}
