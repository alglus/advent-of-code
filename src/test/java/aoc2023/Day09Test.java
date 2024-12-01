package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("114", new Day09().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("2", new Day09().asTest().solvePart2());
    }
}
