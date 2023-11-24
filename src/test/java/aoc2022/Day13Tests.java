package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_13.txt");
        int rightOrderPairsSum = Day13.distressSignalPart1(input);

        assertEquals(13, rightOrderPairsSum);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_13.txt");
        int decoderKey = Day13.distressSignalPart2(input);

        assertEquals(140, decoderKey);
    }
}
