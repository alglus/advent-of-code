package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("2", new Day02().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("4", new Day02().asTest().solvePart2());
    }
}