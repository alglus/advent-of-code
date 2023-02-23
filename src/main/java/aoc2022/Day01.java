package aoc2022;

import util.Util;

import java.util.*;

public class Day01 {

    /* --- Part One --- */
    public static long countCaloriesPart1(List<String> input) {

        if (input.isEmpty()) return 0;

        TreeSet<Long> caloriesOfElves = sumCaloriesOfEachElfAndPutInSetInReverseOrder(input);

        return caloriesOfElves.first();
    }


    /* --- Part Two --- */
    public static long countCaloriesPart2(List<String> input) {

        if (input.isEmpty()) return 0;

        TreeSet<Long> caloriesOfElves = sumCaloriesOfEachElfAndPutInSetInReverseOrder(input);

        return caloriesOfElves.stream()
                .limit(3)
                .mapToLong(Long::longValue)
                .sum();
    }


    private static TreeSet<Long> sumCaloriesOfEachElfAndPutInSetInReverseOrder(List<String> input) {
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


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_01.txt");


        System.out.println("Part 1: " + countCaloriesPart1(input));
        System.out.println("Part 2: " + countCaloriesPart2(input));
    }
}
