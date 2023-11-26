package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("13", new Day09().asTest().withInputFileSuffix("_1").solvePart1());
    }

    @Test
    public void aoc_example_part2_1() {
        assertEquals("1", new Day09().asTest().withInputFileSuffix("_1").solvePart2());
    }

    @Test
    public void aoc_example_part2_2() {
        assertEquals("36", new Day09().asTest().withInputFileSuffix("_2").solvePart2());
    }
}
