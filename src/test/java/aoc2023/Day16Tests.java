package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("46", new Day16().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("51", new Day16().asTest().solvePart2());
    }
}
