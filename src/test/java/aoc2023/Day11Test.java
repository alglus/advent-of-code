package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    public void aoc_example_part1_1() {
        assertEquals("374", new Day11().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part1_2() {
        assertEquals("6", new Day11().asTest().withInputFileSuffix("_1_2").solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("82000210", new Day11().asTest().solvePart2());
    }
}
