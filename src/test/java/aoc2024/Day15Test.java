package aoc2024;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day15Test {

    @Test
    void aoc_example_part1_1() {
        assertEquals("10092", new Day15().asTest().withInputFileSuffix("_1").solvePart1());
    }

    @Test
    void aoc_example_part1_2() {
        assertEquals("2028", new Day15().asTest().withInputFileSuffix("_2").solvePart1());
    }

    @Test
    @Disabled
    void aoc_example_part2_1() {
        assertEquals("9021", new Day15().asTest().withInputFileSuffix("_1").solvePart2());
    }

    @Test
    @Disabled
    void aoc_example_part2_2() {
        assertEquals("618", new Day15().asTest().withInputFileSuffix("_3").solvePart2());
    }
}