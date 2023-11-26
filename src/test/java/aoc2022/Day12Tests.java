package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("31", new Day12().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("29", new Day12().asTest().solvePart2());
    }
}