package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_05.txt");
        String topCrates = Day05.supplyStacksPart1(input);

        assertEquals("CMZ", topCrates);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_05.txt");
        String topCrates = Day05.supplyStacksPart2(input);

        assertEquals("MCD", topCrates);
    }
}
