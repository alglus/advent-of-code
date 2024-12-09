package aoc2024;

import util.Direction;
import util.Matrix;
import util.Point;

import java.util.HashSet;
import java.util.Set;

public class Day06 extends Puzzle2024 {

    public Day06() {
        super(6);
    }

    public static void main(final String[] args) {
        new Day06().printSolutions();
    }

    private static int countDistinctPositions(final Matrix<Character> matrix) {
        final Point startingPoint = findStartingMarker(matrix);

        Move move = new Move(startingPoint, Direction.U);
        Set<Point> visitedPoints = new HashSet<>();

        do {
            visitedPoints.add(move.point());

            move = findNextMove(move, matrix);

        } while (!move.point().isOutsideOf(matrix));

        return visitedPoints.size();
    }

    private static Point findStartingMarker(final Matrix<Character> matrix) {
        for (int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {
                final var character = matrix.at(x, y);
                if (character == '^') {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalStateException("No starting marker found");
    }

    private static Move findNextMove(Move move, Matrix<Character> matrix) {
        while (true) {
            Point nextPoint = move.point().add(move.direction().step);

            if (nextPoint.isOutsideOf(matrix)) {
                return new Move(nextPoint, move.direction());
            }

            if (matrix.at(nextPoint) == '#') {
                move = new Move(move.point(), move.direction().rotate90DegreesClockwise());
                continue;
            }

            return new Move(nextPoint, move.direction());
        }
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countDistinctPositions(Matrix.of(getInputAsCharMatrix())));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getInputAsCharMatrix());
    }

    private record Move(Point point, Direction direction) {
    }
}
