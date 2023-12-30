package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("1320", new Day15().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part1_2() {
        assertEquals("52", new Day15().asTest().withInputFileSuffix("_1_2").solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("145", new Day15().asTest().solvePart2());
    }
}
