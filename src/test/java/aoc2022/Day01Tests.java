package aoc2022;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

public class Day01Tests {

    @Test
    public void calories_in_empty_list_are_zero() {
        Day01 day01 = spy(Day01.class);
        Mockito.when(day01.getInputLines()).thenReturn(Collections.emptyList());

        assertEquals("0", day01.solvePart1());
    }

    @Test
    public void max_calories_sum_in_list_of_one_item_is_equal_to_that_item() {
        Day01 day01 = spy(Day01.class);
        Mockito.when(day01.getInputLines()).thenReturn(List.of("42"));

        assertEquals("42", day01.solvePart1());
        assertEquals("42", day01.solvePart2());
    }

    @Test
    public void max_calories_sum_in_list_of_just_zeros_is_zero() {
        Day01 day01 = spy(Day01.class);
        Mockito.when(day01.getInputLines()).thenReturn(
                List.of("0", "", "0", "0", "", "0", "0", "0", "", "0", "0", "0", "0")
        );

        assertEquals("0", day01.solvePart1());
        assertEquals("0", day01.solvePart2());
    }

    @Test
    public void calories_of_top_three_elves_in_list_of_one_elf_are_equal_to_that_one_elf() {
        Day01 day01 = spy(Day01.class);
        Mockito.when(day01.getInputLines()).thenReturn(
                List.of("42", "42", "42")
        );

        var caloriesOfTopThreeElves = day01.solvePart2();
        assertEquals("126", caloriesOfTopThreeElves);
    }

    @Test
    public void calories_of_top_three_elves_in_list_of_two_elves_are_equal_to_those_two_elves() {
        Day01 day01 = spy(Day01.class);
        Mockito.when(day01.getInputLines()).thenReturn(
                List.of("42", "42", "", "42", "42", "42")
        );

        var caloriesOfTopThreeElves = day01.solvePart2();
        assertEquals("210", caloriesOfTopThreeElves);
    }

    @Test
    public void aoc_example_part1() {
        assertEquals("24000", new Day01().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("45000", new Day01().asTest().solvePart2());
    }
}
