package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_11.txt");
        long monkeyBusinessLevel = Day11.monkeyInTheMiddlePart1(input);

        assertEquals(10605, monkeyBusinessLevel);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_11.txt");
        long monkeyBusinessLevel = Day11.monkeyInTheMiddlePart2(input);

        assertEquals(2713310158L, monkeyBusinessLevel);
    }
}
