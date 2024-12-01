package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("136", new Day14().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part1_2() {
        assertEquals("10", new Day14().asTest().withInputFileSuffix("_1_2").solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("64", new Day14().asTest().solvePart2());
    }
}
