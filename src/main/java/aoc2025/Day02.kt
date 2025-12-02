package aoc2025

import util.MathUtil.isEven
import util.MathUtil.isInteger

fun main() {
    Day02().printSolutions()
}

class Day02 : Puzzle2025(2) {

    private fun sumInvalidIds(
        input: String,
        getInvalidIds: (List<String>) -> Iterable<String>,
    ): Long {
        return input
            .split(",")
            .map { rangeString -> rangeString.split("-") }
            .map { rangeEnds -> LongRange(rangeEnds.first().toLong(), rangeEnds.last().toLong()) }
            .map { range -> range.map(Long::toString) }
            .flatMap(getInvalidIds)
            .sumOf(String::toLong)
    }

    private fun getNumbersWithOneRepetition(): (List<String>) -> Iterable<String> = { range ->
        range
            .filter(hasEvenLength())
            .filter(hasNumberRepeatedTwice())
    }

    private fun hasEvenLength(): (String) -> Boolean = { s -> isEven(s.length) }

    private fun hasNumberRepeatedTwice(): (String) -> Boolean = { s ->
        val half = s.length / 2

        (0 until half).all { i ->
            s[i] == s[i + half]
        }
    }

    private fun getNumbersWithAnyRepetitions(): (List<String>) -> Iterable<String> = { range ->
        range.filter { s ->
            (2..s.length).any { parts ->
                val partLengthExact = s.length.toDouble() / parts

                if (isInteger(partLengthExact)) {
                    val partLength = partLengthExact.toInt()

                    (0 until parts - 1).all { repetition ->
                        (0 until partLength).all { index ->
                            s[index + repetition * partLength] == s[index + repetition * partLength + partLength]
                        }
                    }
                } else {
                    false
                }
            }
        }
    }

    override fun solvePart1(): String {
        return sumInvalidIds(inputAsString, this@Day02.getNumbersWithOneRepetition()).toString()
    }

    override fun solvePart2(): String {
        return sumInvalidIds(inputAsString, this@Day02.getNumbersWithAnyRepetitions()).toString()
    }
}
