package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("10605", new Day11().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("2713310158", new Day11().asTest().solvePart2());
    }
}
