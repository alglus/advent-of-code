package aoc2025

fun main() {
    Day03().printSolutions()
}

class Day03 : Puzzle2025(3) {

    private fun getTotalJoltage(input: List<String>, getMaxJoltage: (String) -> Long): Long {
        return input.sumOf(getMaxJoltage)
    }

    private fun getMaxTwoBatteriesJoltage(): (String) -> Long = { bank ->
        var maxJoltage: Long = -1

        for (i in 0 until bank.length - 1) {
            for (j in i + 1 until bank.length) {
                val joltage = "${bank[i]}${bank[j]}".toLong()
                if (joltage > maxJoltage) maxJoltage = joltage
            }
        }

        maxJoltage
    }

    private fun getMaxNBatteriesJoltage(n: Int): (String) -> Long = { bank ->
        val maxJoltage = buildString {
            var s = 0

            for (k in n - 1 downTo 0) {
                var maxDigit = -1
                var maxDigitIndex = -1

                for (i in s until bank.length - k) {
                    val digit = bank[i].digitToInt()

                    if (digit > maxDigit) {
                        maxDigit = digit
                        maxDigitIndex = i
                    }
                }

                append(maxDigit)
                s = maxDigitIndex + 1
            }
        }
        
        maxJoltage.toLong()
    }

    override fun solvePart1(): String {
//        return getTotalJoltage(inputLines, getMaxTwoBatteriesJoltage()).toString()
        return getTotalJoltage(inputLines, getMaxNBatteriesJoltage(2)).toString()
    }

    override fun solvePart2(): String {
        return getTotalJoltage(inputLines, getMaxNBatteriesJoltage(12)).toString()
    }
}