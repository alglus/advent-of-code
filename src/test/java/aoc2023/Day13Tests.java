package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("405", new Day13().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("400", new Day13().asTest().solvePart2());
    }
}
