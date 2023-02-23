package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_03.txt");
        long prioritiesSum = Day03.rucksackReorganizationPart1(input);

        assertEquals(157, prioritiesSum);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_03.txt");
        long prioritiesSum = Day03.rucksackReorganizationPart2(input);

        assertEquals(70, prioritiesSum);
    }
}
