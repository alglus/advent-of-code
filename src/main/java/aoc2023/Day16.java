package aoc2023;

import util.Direction;
import util.Matrix;
import util.Point;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Set;

public class Day16 extends Puzzle2023 {

    public Day16() {
        super(16);
    }

    public static void main(final String[] args) {
        new Day16().printSolutions();
    }

    private long getEnergizedTilesCount(final Matrix<Character> matrix, final BeamEntry startingBeamEntry) {
        final var energyMap = Matrix.create(Direction.class, matrix.width, matrix.height, null);
        final var beamEntries = new ArrayDeque<BeamEntry>();

        beamEntries.add(startingBeamEntry);

        while (!beamEntries.isEmpty()) {
            final var beamEntry = beamEntries.poll();
            var point = beamEntry.point;
            var direction = beamEntry.direction;

            while (true) {
                point = point.add(direction.step);

                if (point.isOutsideOf(matrix) || energyMap.at(point) == direction) {
                    break;
                }

                energyMap.set(point, direction);
                final var tile = matrix.at(point);

                if (tile == '/' || tile == '\\') {
                    direction = setNewDirection(tile, direction);
                }

                if (tile == '-' || tile == '|') {
                    if (isBeamSplit(point, tile, direction, beamEntries)) {
                        break;
                    }
                }
            }
        }

        return energyMap.stream()
                .filter(Objects::nonNull)
                .count();
    }

    private long getMaximumEnergizedTilesCount(final Matrix<Character> matrix) {
        var maxCount = 0L;

        for (int x = 0; x < matrix.width; x++) {
            final var countStartingOnTopRow = getEnergizedTilesCount(matrix, new BeamEntry(Point.at(x, -1), Direction.D));
            final var countStartingOnBottomRow = getEnergizedTilesCount(matrix, new BeamEntry(Point.at(x, matrix.height), Direction.U));
            maxCount = Math.max(maxCount, countStartingOnTopRow);
            maxCount = Math.max(maxCount, countStartingOnBottomRow);
        }

        for (int y = 0; y < matrix.height; y++) {
            final var countStartingOnLeftColumn = getEnergizedTilesCount(matrix, new BeamEntry(Point.at(-1, y), Direction.R));
            final var countStartingOnRightColumn = getEnergizedTilesCount(matrix, new BeamEntry(Point.at(matrix.width, y), Direction.L));
            maxCount = Math.max(maxCount, countStartingOnLeftColumn);
            maxCount = Math.max(maxCount, countStartingOnRightColumn);
        }

        return maxCount;
    }

    private Direction setNewDirection(final Character tile, final Direction direction) {
        return switch (direction) {
            case R -> tile == '/' ? Direction.U : Direction.D;
            case L -> tile == '/' ? Direction.D : Direction.U;
            case U -> tile == '/' ? Direction.R : Direction.L;
            case D -> tile == '/' ? Direction.L : Direction.R;
            default -> throw new IllegalStateException("Unexpected direction: " + direction);
        };
    }

    private boolean isBeamSplit(final Point point, final Character tile, final Direction direction, final ArrayDeque<BeamEntry> beamEntries) {
        return switch (direction) {
            case L, R -> {
                if (tile == '-') yield false;
                beamEntries.addAll(Set.of(new BeamEntry(point, Direction.U), new BeamEntry(point, Direction.D)));
                yield true;
            }
            case U, D -> {
                if (tile == '|') yield false;
                beamEntries.addAll(Set.of(new BeamEntry(point, Direction.L), new BeamEntry(point, Direction.R)));
                yield true;
            }
            default -> throw new IllegalStateException("Unexpected direction: " + direction);
        };
    }

    @Override
    public String solvePart1() {
        return String.valueOf(
                getEnergizedTilesCount(
                        Matrix.of(getInputAsCharMatrix()),
                        new BeamEntry(Point.at(-1, 0), Direction.R)));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getMaximumEnergizedTilesCount(Matrix.of(getInputAsCharMatrix())));
    }

    private record BeamEntry(Point point, Direction direction) {
    }
}
