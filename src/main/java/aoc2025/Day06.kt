package aoc2025

import util.MathFunction
import util.Matrix

fun main() {
    Day06().printSolutions()
}

class Day06 : Puzzle2025(6) {

    private fun calculateGrandTotal(input: List<String>): Long {
        val numbers = input
            .subList(0, input.lastIndex)
            .map {
                it.trim()
                    .split(Regex("""\s+"""))
                    .map { n -> n.toLong() }
            }

        val operators = input[input.lastIndex]
            .trim()
            .split(Regex("""\s+"""))
            .map { MathFunction.fromOperator(it.single()) }

        return (0 until numbers[0].size)
            .fold(0L) { grandTotal, i ->
                val op = operators[i]
                val init = op.initialAccValue()

                grandTotal + (0 until numbers.size).fold(init) { acc, j ->
                    op.apply(acc, numbers[j][i])
                }
            }
    }

    private fun calculateCorrectGrandTotal(matrix: Matrix<Char>): Long {
        val problems = getProblems(matrix)
        val operators = getOperators(matrix)

        return problems
            .mapIndexed { i, numbers ->
                val op = operators[i]
                val init = op.initialAccValue()
                numbers.fold(init) { acc, n -> op.apply(acc, n) }
            }
            .sum()
    }

    private fun getProblems(matrix: Matrix<Char>): List<List<Long>> {
        val problems = mutableListOf<List<Long>>()
        var problem = mutableListOf<Long>()

        for (x in matrix.width - 1 downTo 0) {
            val number = StringBuilder()

            for (y in 0 until matrix.height - 1) {
                val c = matrix.at(x, y)
                if (c != ' ') number.append(c)
            }

            if (number.isEmpty()) {
                problems.add(problem)
                problem = mutableListOf()
            } else {
                problem.add(number.toString().toLong())
            }
        }

        problems.add(problem)
        return problems
    }

    private fun getOperators(matrix: Matrix<Char>): List<MathFunction> {
        return (matrix.width - 1 downTo 0)
            .map { x -> matrix.at(x, matrix.height - 1) }
            .filter { it != ' ' }
            .map { MathFunction.fromOperator(it) }
            .toList()
    }

    override fun solvePart1(): String {
        return calculateGrandTotal(inputLines).toString()
    }

    override fun solvePart2(): String {
        return calculateCorrectGrandTotal(inputAsCharMatrix).toString()
    }
}
