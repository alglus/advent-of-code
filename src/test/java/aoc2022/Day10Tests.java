package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_10.txt");
        int signalStrengthSum = Day10.cathodeRayTubePart1(input);

        assertEquals(13140, signalStrengthSum);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_10.txt");
        List<String> imageDrawn = Day10.cathodeRayTubePart2(input);

        List<String> imageExpected = List.of(
                "##..##..##..##..##..##..##..##..##..##..",
                "###...###...###...###...###...###...###.",
                "####....####....####....####....####....",
                "#####.....#####.....#####.....#####.....",
                "######......######......######......####",
                "#######.......#######.......#######....."
        );

        assertEquals(imageDrawn, imageExpected);
    }
}
