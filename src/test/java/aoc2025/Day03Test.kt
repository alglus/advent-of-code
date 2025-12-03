package aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun aoc_example_part1() {
        assertEquals("357", Day03().asTest().solvePart1())
    }

    @Test
    fun aoc_example_part2() {
        assertEquals("3121910778619", Day03().asTest().solvePart2())
    }
}
