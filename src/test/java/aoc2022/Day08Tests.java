package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_08.txt");
        int treesVisibleFromOutsideTheGrid = Day08.treetopTreeHousePart1(input);

        assertEquals(21, treesVisibleFromOutsideTheGrid);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input_day_08.txt");
        int highestScenicScore = Day08.treetopTreeHousePart2(input);

        assertEquals(8, highestScenicScore);
    }
}
