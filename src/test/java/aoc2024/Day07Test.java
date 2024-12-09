package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Test {

    @Test
    void aoc_example_part1_1() {
        assertEquals("3749", new Day07().asTest().solvePart1());
    }

    @Test
    void aoc_example_part2() {
        assertEquals("11387", new Day07().asTest().solvePart2());
    }
}