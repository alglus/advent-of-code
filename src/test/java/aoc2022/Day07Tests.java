package aoc2022;

import org.junit.jupiter.api.Test;
import util.Util;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day07Tests {

    @Test
    public void aoc_example_part1() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_07.txt");
        int totalDirectoriesSize = Day07.noSpaceLeftOnDevicePart1(input);

        assertEquals(95437, totalDirectoriesSize);
    }

    @Test
    public void aoc_example_part2() {
        List<String> input = Util.getLinesFromTestFile("aoc2022/input/day_07.txt");
        int sizeOfSmallestDirToDelete = Day07.noSpaceLeftOnDevicePart2(input);

        assertEquals(24933642, sizeOfSmallestDirToDelete);
    }
}