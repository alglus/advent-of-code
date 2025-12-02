package aoc2025

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun aoc_example_part1() {
        assertEquals("1227775554", Day02().asTest().solvePart1())
    }

    @Test
    fun aoc_example_part2() {
        assertEquals("4174379265", Day02().asTest().solvePart2())
    }
}
