package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("21", new Day08().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("8", new Day08().asTest().solvePart2());
    }
}
