package aoc2022;

import util.*;
import util.graph.Edge;
import util.graph.Graph;
import util.graph.SimpleNode;

import java.util.Optional;

import static util.Input.convertCharInputIntoMatrix;
import static util.StringUtil.concatenate;

public class Day12 extends Puzzle2022 {

    public Day12() {
        super(12);
    }

    public static void main(final String[] args) {
        new Day12().printSolutions();
    }

    private Graph buildGraphFromMatrix(final Character[][] heightMatrix) {
        final var graph = new Graph();
        final int weight = 1;

        final int matrixWidth = heightMatrix[0].length;
        final int matrixHeight = heightMatrix.length;

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {

                final var currentPoint = new RangedPoint(Point.at(x, y), new Range<>(0, matrixWidth - 1), new Range<>(0, matrixHeight - 1));
                var currentHeight = heightMatrix[y][x];
                final var currentNode = graph.addNodeOrGetExisting(new SimpleNode(concatenate(".", x, y), currentHeight));
                currentHeight = correctHeightOfStartAndEndNodes(currentHeight);

                for (final Direction direction : Direction.cardinal()) {
                    final var neighborPosition = currentPoint.add(direction.step);

                    if (neighborPosition.isInRange()) {
                        var neighborHeight = heightMatrix[neighborPosition.point().y()][neighborPosition.point().x()];
                        final var neighborId = concatenate(".", neighborPosition.point().x(), neighborPosition.point().y());
                        final var neighborNode = graph.addNodeOrGetExisting(new SimpleNode(neighborId, neighborHeight));
                        neighborHeight = correctHeightOfStartAndEndNodes(neighborHeight);

                        if (isPossibleToStepOntoNeighborPosition(currentHeight, neighborHeight)) {
                            currentNode.addEdge(new Edge(neighborNode, weight));
                        }
                    }
                }
            }
        }

        return graph;
    }

    private char correctHeightOfStartAndEndNodes(final char height) {
        return switch (height) {
            case 'S' -> 'a';
            case 'E' -> 'z';
            default -> height;
        };
    }

    private boolean isPossibleToStepOntoNeighborPosition(final char currentHeight, final char neighborHeight) {
        return neighborHeight - currentHeight <= 1;
    }

    @Override
    public String solvePart1() {
        final var heightMatrix = convertCharInputIntoMatrix(getInputLines());
        final var graph = buildGraphFromMatrix(heightMatrix);

        final var startNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals("S"))
                .findFirst().orElseThrow();

        return String.valueOf(Dijkstra
                .execute(graph, startNode, node -> node.getName().equals("E"))
                .getShortestPathLength().orElseThrow());
    }

    @Override
    public String solvePart2() {
        final var heightMatrix = convertCharInputIntoMatrix(getInputLines());
        final var graph = buildGraphFromMatrix(heightMatrix);

        return String.valueOf(graph.getNodes().stream()
                .filter(node -> node.getName().equals("a") || node.getName().equals("S"))
                .map(node -> Dijkstra.execute(graph, node, n -> n.getName().equals("E")).getShortestPathLength())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .findFirst().orElseThrow());
    }

    private record RangedPoint(Point point, Range<Integer> rangeX, Range<Integer> rangeY) {
        public RangedPoint add(final Step step) {
            return new RangedPoint(point.add(step), rangeX, rangeY);
        }

        public boolean isInRange() {
            return rangeX.contains(point.x()) && rangeY.contains(point.y());
        }
    }
}
