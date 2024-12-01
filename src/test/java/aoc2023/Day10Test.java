package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @Test
    public void aoc_example_part1_1() {
        assertEquals("4", new Day10().asTest().withInputFileSuffix("_1_1").solvePart1());
    }

    @Test
    public void aoc_example_part1_2() {
        assertEquals("8", new Day10().asTest().withInputFileSuffix("_1_2").solvePart1());
    }

    @Test
    public void aoc_example_part2_1() {
        assertEquals("4", new Day10().asTest().withInputFileSuffix("_2_1").solvePart2());
    }

    @Test
    public void aoc_example_part2_2() {
        assertEquals("4", new Day10().asTest().withInputFileSuffix("_2_2").solvePart2());
    }

    @Test
    public void aoc_example_part2_3() {
        assertEquals("8", new Day10().asTest().withInputFileSuffix("_2_3").solvePart2());
    }

    @Test
    public void aoc_example_part2_4() {
        assertEquals("10", new Day10().asTest().withInputFileSuffix("_2_4").solvePart2());
    }
}
