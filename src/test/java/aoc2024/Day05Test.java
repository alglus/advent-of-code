package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day05Test {

    @Test
    void aoc_example_part1() {
        assertEquals("143", new Day05().asTest().solvePart1());
    }

    @Test
    void aoc_example_part2() {
        assertEquals("123", new Day05().asTest().solvePart2());
    }
}