package aoc2024;

import util.Direction;
import util.Matrix;
import util.Point;

import java.util.List;

public class Day15 extends Puzzle2024 {

    public Day15() {
        super(15);
    }

    public static void main(final String[] args) {
        new Day15().printSolutions();
    }

    private static long getSumOfGpsCoordinates(final List<List<String>> inputFile) {
        final Input input = parseInput(inputFile);
        Point point = input.start();

        for (int i = 0; i < input.moves().size(); i++) {
            final var direction = input.moves().get(i);
            point = moveRobot(point, direction, input.map());
        }

        return calculateSumOfGpsCoordinates(input.map());
    }

    private static Input parseInput(final List<List<String>> inputFile) {
        final Matrix<Character> matrix = Matrix.of(util.Input.convertCharInputInto2dArray(inputFile.get(0)));
        final Point startPoint = findStartPoint(matrix);
        final List<Direction> moves = parseDirections(inputFile.subList(1, inputFile.size()));
        return new Input(matrix, startPoint, moves);
    }

    private static Point findStartPoint(final Matrix<Character> matrix) {
        for (int y = 0; y < matrix.height; y++) {
            for (int x = 0; x < matrix.width; x++) {
                final char c = matrix.at(x, y);
                if (c == '@') {
                    return new Point(x, y);
                }
            }
        }
        throw new IllegalStateException("There is no starting point.");
    }

    private static List<Direction> parseDirections(final List<List<String>> directions) {
        return directions.stream()
                .flatMap(List::stream)
                .reduce(String::concat)
                .stream()
                .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                .map(Day15::convertToDirection)
                .toList();
    }

    private static Direction convertToDirection(final Character c) {
        return switch (c) {
            case '^' -> Direction.U;
            case '>' -> Direction.R;
            case 'v' -> Direction.D;
            case '<' -> Direction.L;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private static Point moveRobot(final Point robotPoint, final Direction direction, final Matrix<Character> map) {
        Point searchingPoint = robotPoint;

        do {
            searchingPoint = searchingPoint.add(direction.step);
            final char c = map.at(searchingPoint);

            if (c == '#') {
                return robotPoint;
            }

            if (c == '.') {
                var pointToMoveTo = searchingPoint;

                while (true) {
                    final var pointToMoveFrom = pointToMoveTo.add(direction.opposite().step);
                    map.set(pointToMoveTo, map.at(pointToMoveFrom));

                    if (pointToMoveFrom.equals(robotPoint)) {
                        map.set(pointToMoveFrom, '.');
                        return pointToMoveTo;
                    }

                    pointToMoveTo = pointToMoveFrom;
                }
            }
        } while (!robotPoint.isOutsideOf(map));

        return robotPoint;
    }

    private static long calculateSumOfGpsCoordinates(final Matrix<Character> map) {
        long sum = 0;

        for (int y = 0; y < map.height; y++) {
            for (int x = 0; x < map.width; x++) {
                final char c = map.at(x, y);
                if (c == 'O') {
                    sum += 100L * y + x;
                }
            }
        }

        return sum;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfGpsCoordinates(getInputLinesSplitByBlankLine()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getInputAsString());
    }

    private record Input(Matrix<Character> map, Point start, List<Direction> moves) {
    }
}
