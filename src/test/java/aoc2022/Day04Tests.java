package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("2", new Day04().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("4", new Day04().asTest().solvePart2());
    }
}
