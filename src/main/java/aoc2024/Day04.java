package aoc2024;

import util.Direction;
import util.Matrix;
import util.Point;

import java.util.List;

public class Day04 extends Puzzle2024 {

    public Day04() {
        super(4);
    }

    public static void main(final String[] args) {
        new Day04().printSolutions();
    }

    private static int countNumberOfXmas(final Matrix<Character> matrix) {
        int xmasCount = 0;

        for (int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {

                final Point point = new Point(x, y);
                final char letter = matrix.at(point);

                if (letter == 'X') {
                    xmasCount += countXmasStartingAt(matrix, point);
                }
            }
        }

        return xmasCount;
    }

    private static int countXmasStartingAt(final Matrix<Character> matrix, final Point startingPoint) {
        final List<Character> xmas = List.of('X', 'M', 'A', 'S');
        int xmasCount = 0;

        for (final Direction direction : Direction.values()) {

            for (int i = 1; i < xmas.size(); i++) {
                final Point nextPoint = startingPoint.add(direction.step, i);

                if (nextPoint.isOutsideOf(matrix)) {
                    break;
                }

                final char nextLetter = matrix.at(nextPoint);

                if (nextLetter != xmas.get(i)) {
                    break;
                }

                if (nextLetter == 'S' && i == xmas.size() - 1) {
                    xmasCount++;
                }
            }
        }

        return xmasCount;
    }

    private static int countNumberOfMas(final Matrix<Character> matrix) {
        final List<Matrix<Character>> masPatterns = List.of(
                Matrix.of(new Character[][]{
                        {'M', null, 'S'},
                        {null, 'A', null},
                        {'M', null, 'S'},
                }),
                Matrix.of(new Character[][]{
                        {'S', null, 'S'},
                        {null, 'A', null},
                        {'M', null, 'M'},
                }),
                Matrix.of(new Character[][]{
                        {'M', null, 'M'},
                        {null, 'A', null},
                        {'S', null, 'S'},
                }),
                Matrix.of(new Character[][]{
                        {'S', null, 'M'},
                        {null, 'A', null},
                        {'S', null, 'M'},
                })
        );

        int masCount = 0;

        for (int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {

                final Point point = new Point(x, y);

                final boolean isPatternPresent = masPatterns
                        .stream()
                        .anyMatch(pattern -> matrix.hasPatternAt(point, pattern));

                if (isPatternPresent) {
                    masCount++;
                }
            }
        }

        return masCount;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(countNumberOfXmas(getInputAsCharMatrix()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(countNumberOfMas(getInputAsCharMatrix()));
    }
}
