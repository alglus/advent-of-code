package aoc2023;

import util.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static util.CollectionUtil.intersection;
import static util.CollectionUtil.setFromUnorderedRangeEnds;

public class Day11 extends Puzzle2023 {

    public Day11() {
        super(11);
    }

    public static void main(final String[] args) {
        new Day11().printSolutions();
    }

    private UniverseData getExpansionSpace(final List<String> universe) {
        final List<Point> galaxies = new ArrayList<>();
        final Set<Integer> emptyRows = new HashSet<>();
        Set<Integer> emptyColumns = new HashSet<>();

        for (int r = 0; r < universe.size(); r++) {
            final var row = universe.get(r);
            final var emptyColumnsInRow = new HashSet<Integer>();

            for (int c = 0; c < row.length(); c++) {
                final var data = row.charAt(c);

                if (data == '#') {
                    galaxies.add(Point.at(c, r));
                } else {
                    emptyColumnsInRow.add(c);
                }
            }

            if (emptyColumnsInRow.size() == row.length()) {
                emptyRows.add(r);
            }

            if (r == 0) {
                emptyColumns = emptyColumnsInRow;
            } else {
                emptyColumns.retainAll(emptyColumnsInRow);
            }
        }

        return new UniverseData(galaxies, emptyRows, emptyColumns);
    }

    private long getSumOfPathLengthsBetweenAllGalaxies(final UniverseData universeData, final int expansionFactor) {
        long pathLengtsSum = 0;

        for (int i = 0; i < universeData.galaxies().size(); i++) {
            for (int j = i + 1; j < universeData.galaxies().size(); j++) {

                final var galaxy1 = universeData.galaxies().get(i);
                final var galaxy2 = universeData.galaxies().get(j);

                final var expansionColumnsOnPath = intersection(
                        universeData.emptyColumns,
                        setFromUnorderedRangeEnds(galaxy1.x(), galaxy2.x()));
                final var expansionRowsOnPath = intersection(
                        universeData.emptyRows,
                        setFromUnorderedRangeEnds(galaxy1.y(), galaxy2.y()));

                pathLengtsSum += Math.abs(galaxy2.x() - galaxy1.x()) - expansionColumnsOnPath.size()
                        + Math.abs(galaxy2.y() - galaxy1.y()) - expansionRowsOnPath.size()
                        + (long) expansionColumnsOnPath.size() * expansionFactor
                        + (long) expansionRowsOnPath.size() * expansionFactor;
            }
        }

        return pathLengtsSum;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfPathLengthsBetweenAllGalaxies(getExpansionSpace(getInputLines()), 2));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getSumOfPathLengthsBetweenAllGalaxies(getExpansionSpace(getInputLines()), 1000000));
    }

    private record UniverseData(
            List<Point> galaxies,
            Set<Integer> emptyRows,
            Set<Integer> emptyColumns
    ) {
    }
}
