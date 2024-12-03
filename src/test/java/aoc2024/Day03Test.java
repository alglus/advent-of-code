package aoc2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    @Test
    void aoc_example_part1() {
        assertEquals("161", new Day03().asTest().withInputFileSuffix("_1").solvePart1());
    }

    @Test
    void aoc_example_part2() {
        assertEquals("48", new Day03().asTest().withInputFileSuffix("_2").solvePart2());
    }
}