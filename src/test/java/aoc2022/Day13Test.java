package aoc2022;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("13", new Day13().asTest().solvePart1());
    }

    @Disabled("Part 2 is not yet solved and outputs an endless loop for now.")
    @Test
    public void aoc_example_part2() {
        assertEquals("140", new Day13().asTest().solvePart2());
    }
}
