package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day09Test {

    @Test
    void aoc_example_part1() {
        assertEquals("1928", new Day09().asTest().solvePart1());
    }

    @Test
    void aoc_example_part2() {
        assertEquals("2858", new Day09().asTest().solvePart2());
    }
}