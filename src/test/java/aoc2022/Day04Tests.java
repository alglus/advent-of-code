package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_04.txt");
        int overlappingPairs = Day04.campCleanUpPart1(input);

        assertEquals(2, overlappingPairs);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_04.txt");
        int overlappingPairs = Day04.campCleanUpPart2(input);

        assertEquals(4, overlappingPairs);
    }
}
