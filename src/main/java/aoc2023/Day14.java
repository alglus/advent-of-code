package aoc2023;

import one.util.streamex.EntryStream;
import util.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 extends Puzzle2023 {

    public Day14() {
        super(14);
    }

    public static void main(final String[] args) {
        new Day14().printSolutions();
    }

    private long getTotalLoadOnNorthSupportBeams(final Matrix<Character> matrix) {
        return EntryStream.of(matrix.matrix)
                .mapValues(row -> Arrays.stream(row).filter(c -> c == 'O').count())
                .mapToLong(entry -> entry.getValue() * convertIndexToLoad(entry.getKey(), matrix.height))
                .sum();
    }

    private long getTotalLoadOnNorthSupportBeamsAfterNCycles(Matrix<Character> matrix, final int numberOfCycles) {
        final List<Long> loads = new ArrayList<>();
        var patternStart = 0;
        var patternLength = 0;
        var patternCandidateFound = false;
        var patternFound = false;
        long currentLoad = getTotalLoadOnNorthSupportBeams(matrix);

        for (int i = 0; i < numberOfCycles; i++) {
            matrix = spinCycle(matrix);
            currentLoad = getTotalLoadOnNorthSupportBeams(matrix);

            if (!patternCandidateFound && loads.contains(currentLoad)) {
                patternStart = loads.indexOf(currentLoad);
                patternLength = i - patternStart;
                patternCandidateFound = true;
            }

            loads.add(currentLoad);

            if (patternCandidateFound) {
                if (currentLoad != loads.get(i - patternLength)) {
                    patternCandidateFound = false;
                }
                if (i >= patternStart + 10 * patternLength) {
                    // Make sure, that we really found a pattern by checking that it repeats itself 10x in a row.
                    // This also excludes finding a "pattern", consisting of a single value, when it is followed by the
                    // same value a couple of times, as could have been the case with the value 69 in the following
                    // loads list: loads={87,69,69,69,65,64,65,63,...}.
                    patternFound = true;
                    break;
                }
            }
        }

        if (patternFound) {
            return loads.get(patternStart + ((numberOfCycles - 1 - patternStart) % patternLength));
        } else {
            return currentLoad;
        }
    }

    private Matrix<Character> spinCycle(final Matrix<Character> matrix) {
        return tiltPlatformEast(tiltPlatformSouth(tiltPlatformWest(tiltPlatformNorth(matrix))));
    }

    // For testing purposes.
    private Matrix<Character> spinCycles(Matrix<Character> matrix, final int n) {
        System.out.println("start: " + getTotalLoadOnNorthSupportBeams(matrix));
        for (int i = 0; i < n; i++) {
            matrix = spinCycle(matrix);
            System.out.println(i + ": " + getTotalLoadOnNorthSupportBeams(matrix));
        }
        return matrix;
    }

    private Matrix<Character> tiltPlatformNorth(final Matrix<Character> matrix) {
        for (int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {

                final var rock = matrix.at(x, y);
                if (rock == 'O') {
                    for (int row = y - 1; row >= 0; row--) {
                        if (matrix.at(x, row) == '.') {
                            moveRock(matrix, x, row + 1, x, row);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private Matrix<Character> tiltPlatformWest(final Matrix<Character> matrix) {
        for (int x = 0; x < matrix.width; x++) {
            for (int y = 0; y < matrix.height; y++) {

                final var rock = matrix.at(x, y);
                if (rock == 'O') {
                    for (int col = x - 1; col >= 0; col--) {
                        if (matrix.at(col, y) == '.') {
                            moveRock(matrix, col + 1, y, col, y);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private Matrix<Character> tiltPlatformSouth(final Matrix<Character> matrix) {
        for (int y = matrix.height - 1; y >= 0; y--) {
            for (int x = 0; x < matrix.width; x++) {

                final var rock = matrix.at(x, y);
                if (rock == 'O') {
                    for (int row = y + 1; row < matrix.height; row++) {
                        if (matrix.at(x, row) == '.') {
                            moveRock(matrix, x, row - 1, x, row);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private Matrix<Character> tiltPlatformEast(final Matrix<Character> matrix) {
        for (int x = matrix.width - 1; x >= 0; x--) {
            for (int y = 0; y < matrix.height; y++) {

                final var rock = matrix.at(x, y);
                if (rock == 'O') {
                    for (int col = x + 1; col < matrix.width; col++) {
                        if (matrix.at(col, y) == '.') {
                            moveRock(matrix, col - 1, y, col, y);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return matrix;
    }

    private int convertIndexToLoad(final int index, final int matrixHeight) {
        return matrixHeight - index;
    }

    private void moveRock(final Matrix<Character> matrix, final int xFrom, final int yFrom, final int xTo, final int yTo) {
        matrix.set(xTo, yTo, 'O');
        matrix.set(xFrom, yFrom, '.');
    }

    @Override
    public String solvePart1() {
        final var matrix = tiltPlatformNorth(getInputAsCharMatrix());
        return String.valueOf(getTotalLoadOnNorthSupportBeams(matrix));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getTotalLoadOnNorthSupportBeamsAfterNCycles(
                getInputAsCharMatrix(),
                1000000000));
    }
}
