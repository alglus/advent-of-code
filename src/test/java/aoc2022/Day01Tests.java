package aoc2022;


import org.junit.jupiter.api.Test;
import util.Util;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

    @Test
    public void calories_in_empty_list_are_zero() {
        List<String> input = Collections.emptyList();
        long maxCaloriesSum = Day01.countCaloriesPart1(input);

        assertEquals(0, maxCaloriesSum);
    }

    @Test
    public void max_calories_sum_in_list_of_one_item_is_equal_to_that_item() {
        List<String> input = List.of("42");
        long maxCaloriesSumPart1 = Day01.countCaloriesPart1(input);
        long maxCaloriesSumPart2 = Day01.countCaloriesPart2(input);

        assertEquals(42, maxCaloriesSumPart1);
        assertEquals(42, maxCaloriesSumPart2);
    }

    @Test
    public void max_calories_sum_in_list_of_just_zeros_is_zero() {
        List<String> input = List.of("0", "", "0", "0", "", "0", "0", "0", "", "0", "0", "0", "0");
        long maxCaloriesSumPart1 = Day01.countCaloriesPart1(input);
        long maxCaloriesSumPart2 = Day01.countCaloriesPart2(input);

        assertEquals(0, maxCaloriesSumPart1);
        assertEquals(0, maxCaloriesSumPart2);
    }

    @Test
    public void calories_of_top_three_elves_in_list_of_one_elf_are_equal_to_that_one_elf() {
        List<String> input = List.of("42", "42", "42");
        long caloriesOfTopThreeElves = Day01.countCaloriesPart2(input);

        assertEquals(126, caloriesOfTopThreeElves);
    }

    @Test
    public void calories_of_top_three_elves_in_list_of_two_elves_are_equal_to_those_two_elves() {
        List<String> input = List.of("42", "42", "", "42", "42", "42");
        long caloriesOfTopThreeElves = Day01.countCaloriesPart2(input);

        assertEquals(210, caloriesOfTopThreeElves);
    }

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_01.txt");
        long caloriesOfTopElf = Day01.countCaloriesPart1(input);

        assertEquals(24000, caloriesOfTopElf);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_01.txt");
        long caloriesOfTopThreeElves = Day01.countCaloriesPart2(input);

        assertEquals(45000, caloriesOfTopThreeElves);
    }
}
