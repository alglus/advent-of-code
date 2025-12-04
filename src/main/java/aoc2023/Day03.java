package aoc2023;

import util.Matrix;
import util.Point;
import util.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.StringUtil.isDigit;

public class Day03 extends Puzzle2023 {

    public Day03() {
        super(3);
    }

    public static void main(final String[] args) {
        new Day03().printSolutions();
    }

    private int sumAllNumbersAdjacentToSymbolsOnSchematic(final Matrix<Character> matrix) {
        final var schematic = parseSchematicAndSaveData(matrix);
        return sumAllAdjacentNumbersToASymbol(schematic, matrix);
    }

    private Schematic parseSchematicAndSaveData(final Matrix<Character> matrix) {
        final var schematic = new Schematic(new ArrayList<>(), new ArrayList<>());

        for (int y = 0; y < matrix.height; y++) {

            schematic.symbols.add(new ArrayList<>());
            schematic.numbers.add(new ArrayList<>());

            for (int x = 0; x < matrix.width; x++) {
                x = parseCharacterAndReturnNewX(matrix, x, y, schematic);
            }
        }
        return schematic;
    }

    private int parseCharacterAndReturnNewX(final Matrix<Character> matrix, int x, final int y, final Schematic schematic) {
        final var character = matrix.at(x, y);

        if (isSymbol(character)) {
            schematic.symbols.get(y).add(new Symbol(Point.at(x, y), character));
        }

        if (isDigit(character)) {
            x = parseNumberAndReturnItsEndX(matrix, x, y, character, schematic);
        }

        return x;
    }

    private int parseNumberAndReturnItsEndX(final Matrix<Character> matrix, final int startX, final int y,
                                            final char startDigit, final Schematic schematic) {
        final var number = new StringBuilder(String.valueOf(startDigit));
        final var rangeStart = startX;
        var rangeEnd = startX;

        for (int x = startX + 1; x < matrix.width; x++) {
            final var character = matrix.at(x, y);

            if (isDigit(character)) {
                number.append(character);
            } else {
                rangeEnd = x - 1;
                break;
            }

            if (matrix.isAtRightBorder(x)) {
                rangeEnd = x;
                break;
            }
        }

        schematic.numbers.get(y).add(
                new Number(
                        new Range<>(rangeStart, rangeEnd),
                        Integer.parseInt(number.toString())));

        return rangeEnd;
    }

    private int sumAllAdjacentNumbersToASymbol(final Schematic schematic, final Matrix<Character> matrix) {
        int sum = 0;

        for (int y = 0; y < matrix.height; y++) {

            for (final Number number : schematic.numbers.get(y)) {
                final var rangesToSearchSymbol = getAdjacentRangesToNumber(number, y, matrix);
                sum += getNumberIfAdjacentToAnySymbol(rangesToSearchSymbol, number, schematic);
            }
        }

        return sum;
    }

    private int getNumberIfAdjacentToAnySymbol(final Map<Integer, Range<Integer>> rangesToSearch, final Number number, final Schematic schematic) {
        for (final Entry<Integer, Range<Integer>> rangeToSearch : rangesToSearch.entrySet()) {
            final var y = rangeToSearch.getKey();
            final var adjacentRange = rangeToSearch.getValue();

            for (final Symbol symbol : schematic.symbols.get(y)) {
                if (adjacentRange.contains(symbol.point.x())) {
                    return number.value;
                }
            }
        }
        return 0;
    }

    private Map<Integer, Range<Integer>> getAdjacentRangesToNumber(final Number number, final int y, final Matrix<Character> matrix) {
        final var adjacentRangeX = getAdjacentRangeX(number.range.fromInc(), number.range.toInc(), matrix);
        return expandRangeVerticallyAndLimitToMatrix(y, adjacentRangeX, matrix);
    }

    private Map<Integer, Range<Integer>> getAdjacentRangesToSymbol(final Symbol symbol, final int y, final Matrix<Character> matrix) {
        final var adjacentRangeX = getAdjacentRangeX(symbol.point.x(), symbol.point.x(), matrix);
        return expandRangeVerticallyAndLimitToMatrix(y, adjacentRangeX, matrix);
    }

    private Range<Integer> getAdjacentRangeX(final int rangeStart, final int rangeEnd, final Matrix<Character> matrix) {
        return new Range<>(
                boundedX(rangeStart - 1, matrix),
                boundedX(rangeEnd + 1, matrix)
        );
    }

    private Map<Integer, Range<Integer>> expandRangeVerticallyAndLimitToMatrix(final int y, final Range<Integer> rangeX,
                                                                               final Matrix<Character> matrix) {
        return Stream
                .of(y - 1, y, y + 1)
                .filter(n -> n >= 0 && n < matrix.height)
                .collect(Collectors.toMap(n -> n, n -> rangeX));
    }

    private long sumAllGearRatios(final Matrix<Character> matrix) {
        final var schematic = parseSchematicAndSaveData(matrix);
        return sumMultiplicationOfTwoNumbersNextToGearSymbol(schematic, matrix);
    }

    private int sumMultiplicationOfTwoNumbersNextToGearSymbol(final Schematic schematic, final Matrix<Character> matrix) {
        var sum = 0;

        for (int y = 0; y < matrix.height; y++) {
            for (final Symbol symbol : schematic.symbols.get(y)) {

                if (symbol.value == '*') {
                    final var rangesAroundSymbol = getAdjacentRangesToSymbol(symbol, y, matrix);
                    final var adjacentNumbers = findNumbersAdjacentToSymbol(rangesAroundSymbol, schematic);

                    if (adjacentNumbers.size() == 2) {
                        final var gearRatio = adjacentNumbers.get(0).value * adjacentNumbers.get(1).value;
                        sum += gearRatio;
                    }
                }
            }
        }

        return sum;
    }

    private List<Number> findNumbersAdjacentToSymbol(final Map<Integer, Range<Integer>> rangesAroundSymbol, final Schematic schematic) {
        final List<Number> adjacentNumbers = new ArrayList<>();

        for (final Entry<Integer, Range<Integer>> rangeAroundSymbol : rangesAroundSymbol.entrySet()) {
            final var y = rangeAroundSymbol.getKey();
            final var adjacentRange = rangeAroundSymbol.getValue();

            for (final Number number : schematic.numbers.get(y)) {
                if (number.range.overlaps(adjacentRange)) {
                    adjacentNumbers.add(number);
                }
            }
        }

        return adjacentNumbers;
    }

    private boolean isSymbol(final char character) {
        return !isDigit(character) && character != '.';
    }

    private int boundedX(final int x, final Matrix<Character> matrix) {
        return Math.min(Math.max(0, x), matrix.width - 1);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(sumAllNumbersAdjacentToSymbolsOnSchematic(getInputAsCharMatrix()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(sumAllGearRatios(getInputAsCharMatrix()));
    }

    private record Schematic(
            List<List<Symbol>> symbols,
            List<List<Number>> numbers
    ) {
    }

    private record Symbol(
            Point point,
            char value
    ) {
    }

    private record Number(
            Range<Integer> range,
            int value
    ) {
    }

}
