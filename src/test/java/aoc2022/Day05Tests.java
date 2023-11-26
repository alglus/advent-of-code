package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("CMZ", new Day05().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("MCD", new Day05().asTest().solvePart2());
    }
}
