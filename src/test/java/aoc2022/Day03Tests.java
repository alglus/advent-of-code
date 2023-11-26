package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("157", new Day03().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("70", new Day03().asTest().solvePart2());
    }
}
