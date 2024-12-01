package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("11", new Day01().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("31", new Day01().asTest().solvePart2());
    }
}