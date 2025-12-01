package aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun aoc_example_part1() {
        assertEquals("3", Day01().asTest().solvePart1())
    }

    @Test
    fun aoc_example_part2() {
        assertEquals("6", Day01().asTest().solvePart2())
    }
}
