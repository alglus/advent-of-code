package aoc2022;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {

    @Test
    public void aoc_example_part1() {
        assertEquals("13140", new Day10().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        var imageExpected = """
                ##..##..##..##..##..##..##..##..##..##..
                ###...###...###...###...###...###...###.
                ####....####....####....####....####....
                #####.....#####.....#####.....#####.....
                ######......######......######......####
                #######.......#######.......#######.....""";

        assertEquals(imageExpected, new Day10().asTest().solvePart2());
    }
}
