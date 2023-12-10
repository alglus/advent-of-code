package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("35", new Day05().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("46", new Day05().asTest().solvePart2());
    }
}
