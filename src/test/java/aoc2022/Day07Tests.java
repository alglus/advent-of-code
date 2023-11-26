package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("95437", new Day07().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("24933642", new Day07().asTest().solvePart2());
    }
}