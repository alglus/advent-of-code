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

    public static void main(String[] args) {
        new Day03().printSolutions();
    }

    private int sumAllNumbersAdjacentToSymbolsOnSchematic(Matrix matrix) {
        var schematic = parseSchematicAndSaveData(matrix);
        return sumAllAdjacentNumbersToASymbol(schematic, matrix);
    }

    private Schematic parseSchematicAndSaveData(Matrix matrix) {
        var schematic = new Schematic(new ArrayList<>(), new ArrayList<>());

        for (int y = 0; y < matrix.heigth; y++) {

            schematic.symbols.add(new ArrayList<>());
            schematic.numbers.add(new ArrayList<>());

            for (int x = 0; x < matrix.width; x++) {
                x = parseCharacterAndReturnNewX(matrix, x, y, schematic);
            }
        }
        return schematic;
    }

    private int parseCharacterAndReturnNewX(Matrix matrix, int x, int y, Schematic schematic) {
        var character = matrix.xy(x, y);

        if (isSymbol(character)) {
            schematic.symbols.get(y).add(new Symbol(Point.at(x, y), character));
        }

        if (isDigit(character)) {
            x = parseNumberAndReturnItsEndX(matrix, x, y, character, schematic);
        }

        return x;
    }

    private int parseNumberAndReturnItsEndX(Matrix matrix, int startX, int y, char startDigit, Schematic schematic) {
        var number = new StringBuilder(String.valueOf(startDigit));
        var rangeStart = startX;
        var rangeEnd = startX;

        for (int x = startX + 1; x < matrix.width; x++) {
            var character = matrix.xy(x, y);

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

    private int sumAllAdjacentNumbersToASymbol(Schematic schematic, Matrix matrix) {
        int sum = 0;

        for (int y = 0; y < matrix.heigth; y++) {

            for (Number number : schematic.numbers.get(y)) {
                var rangesToSearchSymbol = getAdjacentRangesToNumber(number, y, matrix);
                sum += getNumberIfAdjacentToAnySymbol(rangesToSearchSymbol, number, schematic);
            }
        }

        return sum;
    }

    private int getNumberIfAdjacentToAnySymbol(Map<Integer, Range<Integer>> rangesToSearch, Number number, Schematic schematic) {
        for (Entry<Integer, Range<Integer>> rangeToSearch : rangesToSearch.entrySet()) {
            var y = rangeToSearch.getKey();
            var adjacentRange = rangeToSearch.getValue();

            for (Symbol symbol : schematic.symbols.get(y)) {
                if (adjacentRange.contains(symbol.point.x())) {
                    return number.value;
                }
            }
        }
        return 0;
    }

    private Map<Integer, Range<Integer>> getAdjacentRangesToNumber(Number number, int y, Matrix matrix) {
        var adjacentRangeX = getAdjacentRangeX(number.range.fromInc(), number.range.toInc(), matrix);
        return expandRangeVerticallyAndLimitToMatrix(y, adjacentRangeX, matrix);
    }

    private Map<Integer, Range<Integer>> getAdjacentRangesToSymbol(Symbol symbol, int y, Matrix matrix) {
        var adjacentRangeX = getAdjacentRangeX(symbol.point.x(), symbol.point.x(), matrix);
        return expandRangeVerticallyAndLimitToMatrix(y, adjacentRangeX, matrix);
    }

    private Range<Integer> getAdjacentRangeX(int rangeStart, int rangeEnd, Matrix matrix) {
        return new Range<>(
                boundedX(rangeStart - 1, matrix),
                boundedX(rangeEnd + 1, matrix)
        );
    }

    private Map<Integer, Range<Integer>> expandRangeVerticallyAndLimitToMatrix(int y, Range<Integer> rangeX, Matrix matrix) {
        return Stream
                .of(y - 1, y, y + 1)
                .filter(n -> n >= 0 && n < matrix.heigth)
                .collect(Collectors.toMap(n -> n, n -> rangeX));
    }

    private long sumAllGearRatios(Matrix matrix) {
        var schematic = parseSchematicAndSaveData(matrix);
        return sumMultiplicationOfTwoNumbersNextToGearSymbol(schematic, matrix);
    }

    private int sumMultiplicationOfTwoNumbersNextToGearSymbol(Schematic schematic, Matrix matrix) {
        var sum = 0;

        for (int y = 0; y < matrix.heigth; y++) {
            for (Symbol symbol : schematic.symbols.get(y)) {

                if (symbol.value == '*') {
                    var rangesAroundSymbol = getAdjacentRangesToSymbol(symbol, y, matrix);
                    var adjacentNumbers = findNumbersAdjacentToSymbol(rangesAroundSymbol, schematic);

                    if (adjacentNumbers.size() == 2) {
                        var gearRatio = adjacentNumbers.get(0).value * adjacentNumbers.get(1).value;
                        sum += gearRatio;
                    }
                }
            }
        }

        return sum;
    }

    private List<Number> findNumbersAdjacentToSymbol(Map<Integer, Range<Integer>> rangesAroundSymbol, Schematic schematic) {
        List<Number> adjacentNumbers = new ArrayList<>();

        for (Entry<Integer, Range<Integer>> rangeAroundSymbol : rangesAroundSymbol.entrySet()) {
            var y = rangeAroundSymbol.getKey();
            var adjacentRange = rangeAroundSymbol.getValue();

            for (Number number : schematic.numbers.get(y)) {
                if (number.range.overlaps(adjacentRange)) {
                    adjacentNumbers.add(number);
                }
            }
        }

        return adjacentNumbers;
    }

    private boolean isSymbol(char character) {
        return !isDigit(character) && character != '.';
    }

    private int boundedX(int x, Matrix matrix) {
        return Math.min(Math.max(0, x), matrix.width - 1);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(
                sumAllNumbersAdjacentToSymbolsOnSchematic(
                        Matrix.of(getInputAsCharMatrix())
                ));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(
                sumAllGearRatios(Matrix.of(getInputAsCharMatrix()))
        );
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
