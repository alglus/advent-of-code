package aoc2025

import util.Direction
import util.Matrix
import util.Point

fun main() {
    Day04().printSolutions()
}

class Day04 : Puzzle2025(4) {

    private fun countAdjacentRolls(matrix: Matrix<Char>): Int {
        return matrix.points()
            .filter { point -> matrix.at(point) != '.' }
            .map(toNeighbouringRolls(matrix))
            .count { neighbours -> neighbours.size < 4 }
    }

    private fun toNeighbouringRolls(matrix: Matrix<Char>): (Point) -> List<Point> = { point ->
        Direction.entries
            .map { dir -> point.add(dir.step) }
            .filter { point -> point.isInsideOf(matrix) }
            .filter { point -> matrix.at(point) == '@' }
    }

    private fun countRemovedRolls(matrix: Matrix<Char>): Int {
        var totalRemovedRolls = 0

        do {
            val toRemove = matrix.points()
                .filter { point -> matrix.at(point) != '.' }
                .filter { point -> toNeighbouringRolls(matrix)(point).count() < 4 }

            totalRemovedRolls += toRemove.size
            for (point in toRemove) matrix.set(point, '.')
        } while (toRemove.isNotEmpty())

        return totalRemovedRolls
    }

    override fun solvePart1(): String {
        return countAdjacentRolls(inputAsCharMatrix).toString()
    }

    override fun solvePart2(): String {
        return countRemovedRolls(inputAsCharMatrix).toString()
    }
}
