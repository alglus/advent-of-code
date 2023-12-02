package aoc2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01Tests {

    @Test
    public void aoc_example_part1() {
        assertEquals("142", new Day01().asTest().withInputFileSuffix("_1").solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("281", new Day01().asTest().withInputFileSuffix("_2").solvePart2());
    }

}
