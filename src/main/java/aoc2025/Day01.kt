package aoc2025

import kotlin.math.abs
import kotlin.math.floor

fun main() {
    Day01().printSolutions()
}

class Day01 : Puzzle2025(1) {

    private val instructionPattern = Regex("""(\w)(\d+)""")

    fun calculatePassword(instructions: List<String>): Int {
        var zeros = 0
        var currentRotation = 50

        for (rotation in getRotations(instructions)) {
            currentRotation = (currentRotation + (100 + rotation % 100)) % 100

            if (currentRotation == 0) zeros++
        }

        return zeros
    }

    fun recalculatePassword(instructions: List<String>): Int {
        var zeros = 0
        var currentRotation = 50

        for (rotation in getRotations(instructions)) {
            val newRotation = currentRotation + rotation % 100

            if (currentRotation != 0 && newRotation !in 0..100) zeros++

            currentRotation = (100 + newRotation) % 100

            if (currentRotation == 0) zeros++
            zeros += floor(abs(rotation) / 100.0).toInt()
        }

        return zeros
    }

    private fun getRotations(instructions: List<String>): List<Int> {
        return instructions
            .map { rotation ->
                val match = instructionPattern.find(rotation)!!
                val (direction, distance) = match.destructured

                if (direction == "R") distance.toInt() else -distance.toInt()
            }
    }

    override fun solvePart1(): String {
        return calculatePassword(inputLines).toString()
    }

    override fun solvePart2(): String {
        return recalculatePassword(inputLines).toString()
    }
}
