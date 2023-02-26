package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_09_1.txt");
        int positionsVisitedAtLeastOnce = Day09.ropeBridgePart1(input);

        assertEquals(13, positionsVisitedAtLeastOnce);
    }

    @Test
    public void aoc_example_part2_1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_09_1.txt");
        int positionsVisitedAtLeastOnce = Day09.ropeBridgePart2(input);

        assertEquals(1, positionsVisitedAtLeastOnce);
    }

    @Test
    public void aoc_example_part2_2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_09_2.txt");
        int positionsVisitedAtLeastOnce = Day09.ropeBridgePart2(input);

        assertEquals(36, positionsVisitedAtLeastOnce);
    }
}
