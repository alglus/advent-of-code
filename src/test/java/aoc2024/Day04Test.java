package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    @Test
    void aoc_example_part1() {
        assertEquals("18", new Day04().asTest().solvePart1());
    }

    @Test
    void aoc_example_part2() {
        assertEquals("9", new Day04().asTest().solvePart2());
    }
}