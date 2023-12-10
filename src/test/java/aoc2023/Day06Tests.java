package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("288", new Day06().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("71503", new Day06().asTest().solvePart2());
    }
}
