package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("13", new Day04().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("30", new Day04().asTest().solvePart2());
    }
}
