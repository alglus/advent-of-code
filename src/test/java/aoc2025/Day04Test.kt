package aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun aoc_example_part1() {
        assertEquals("13", Day04().asTest().solvePart1())
    }

    @Test
    fun aoc_example_part2() {
        assertEquals("43", Day04().asTest().solvePart2())
    }
}
