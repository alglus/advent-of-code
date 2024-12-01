package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Test {

    @Test
    public void aoc_example_part1_1() {
        assertEquals("2", new Day08().asTest().withInputFileSuffix("_1_1").solvePart1());
    }

    @Test
    public void aoc_example_part1_2() {
        assertEquals("6", new Day08().asTest().withInputFileSuffix("_1_2").solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("6", new Day08().asTest().withInputFileSuffix("_2").solvePart2());
    }

}
