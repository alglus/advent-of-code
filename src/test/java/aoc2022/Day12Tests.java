package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_12.txt");
        int fewestStepsRequired = Day12.hillClimbingPart1(input);

        assertEquals(31, fewestStepsRequired);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_12.txt");
        int lengthOfShortestPathFromAnyA = Day12.hillClimbingPart2(input);

        assertEquals(29, lengthOfShortestPathFromAnyA);
    }

}