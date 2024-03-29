package aoc2023;

import lombok.AllArgsConstructor;
import util.Direction;
import util.Matrix;
import util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptySet;
import static util.Direction.*;

public class Day10 extends Puzzle2023 {

    public Day10() {
        super(10);
    }

    public static void main(String[] args) {
        new Day10().printSolutions();
    }

    private int getStepsToFarthestPointFromStart(Matrix<Character> matrix) {
        var maze = convertInputMatrixToMaze(matrix);
        var loopPath = findPathBackToStart(maze);
        return loopPath.size() / 2;
    }

    private Maze convertInputMatrixToMaze(Matrix<Character> matrix) {
        var tiles = new Tile[matrix.heigth][matrix.width];
        Tile start = null;

        for (int y = 0; y < matrix.heigth; y++) {
            for (int x = 0; x < matrix.width; x++) {

                var tile = Tile.of(Point.at(x, y), matrix.at(x, y));
                tiles[y][x] = tile;

                if (tile.type == TileType.START) {
                    start = tile;
                }
            }
        }

        return new Maze(Matrix.of(tiles), Objects.requireNonNull(start));
    }

    private List<Tile> findPathBackToStart(Maze maze) {
        var pipeExit = findDirectionLeavingTheStart(maze);
        var nextTile = maze.getAdjacentTile(maze.start, pipeExit);

        List<Tile> loop = new ArrayList<>(List.of(nextTile));

        while (!nextTile.equals(maze.start)) {
            var pipeEntry = pipeExit.opposite();
            pipeExit = nextTile.getPipeExit(pipeEntry);
            nextTile = maze.getAdjacentTile(nextTile, pipeExit);
            loop.add(nextTile);
        }

        return loop;
    }

    private Direction findDirectionLeavingTheStart(Maze maze) {
        for (Direction startExitDirection : Direction.values()) {
            var neighbourTilePosition = maze.start.point.add(startExitDirection.step);
            var neighbourTile = maze.tiles.at(neighbourTilePosition);

            for (Direction neighbourEntryDirection : neighbourTile.type.pipeExits) {
                if (neighbourEntryDirection.isOpposite(startExitDirection)) {
                    return startExitDirection;
                }
            }
        }
        throw new IllegalStateException("Strange... The start tile should have at least one exit.");
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getStepsToFarthestPointFromStart(Matrix.of(getInputAsCharMatrix())));
    }

    @Override
    public String solvePart2() {
        return null;
    }

    @AllArgsConstructor
    private enum TileType {
        START(emptySet()), GROUND(emptySet()), VERTICAL(Set.of(U, D)), HORIZONTAL(Set.of(L, R)),
        NE_BEND(Set.of(U, R)), ES_BEND(Set.of(R, D)), SW_BEND(Set.of(D, L)), WN_BEND(Set.of(L, U));

        public final Set<Direction> pipeExits;

        public static TileType of(char tile) {
            return switch (tile) {
                case 'S' -> START;
                case '.' -> GROUND;
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'L' -> NE_BEND;
                case 'F' -> ES_BEND;
                case '7' -> SW_BEND;
                case 'J' -> WN_BEND;
                default -> throw new IllegalArgumentException("Unknown tile type!");
            };
        }
    }

    private record Tile(
            Point point,
            TileType type
    ) {
        public static Tile of(Point position, char tile) {
            return new Tile(position, TileType.of(tile));
        }

        public Direction getPipeExit(Direction pipeEntry) {
            return this.type.pipeExits.stream()
                    .filter(d -> d != pipeEntry)
                    .findFirst()
                    .orElseThrow();
        }
    }

    private record Maze(
            Matrix<Tile> tiles,
            Tile start
    ) {
        public Tile getAdjacentTile(Tile startingTile, Direction direction) {
            return tiles.at(startingTile.point.add(direction.step));
        }
    }
}
