package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("8", new Day02().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("2286", new Day02().asTest().solvePart2());
    }
}
