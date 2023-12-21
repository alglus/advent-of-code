package aoc2022;

import util.Dijkstra;
import util.Directions;
import util.Position;
import util.Range;
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

    public static void main(String[] args) {
        new Day12().printSolutions();
    }

    private Graph buildGraphFromMatrix(final Character[][] heightMatrix) {
        final var graph = new Graph();
        final int weight = 1;

        final int matrixWidth = heightMatrix[0].length;
        final int matrixHeight = heightMatrix.length;

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {

                var currentPosition = new Position(x, y, new Range<>(0, matrixWidth - 1), new Range<>(0, matrixHeight - 1));
                var currentHeight = heightMatrix[y][x];
                var currentNode = graph.addNodeOrGetExisting(new SimpleNode(concatenate(".", x, y), currentHeight));
                currentHeight = correctHeightOfStartAndEndNodes(currentHeight);

                for (Directions direction : Directions.values()) {
                    var neighborPosition = currentPosition.add(direction.step());

                    if (neighborPosition.isInRange()) {
                        var neighborHeight = heightMatrix[neighborPosition.y()][neighborPosition.x()];
                        var neighborId = concatenate(".", neighborPosition.x(), neighborPosition.y());
                        var neighborNode = graph.addNodeOrGetExisting(new SimpleNode(neighborId, neighborHeight));
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

    private char correctHeightOfStartAndEndNodes(char height) {
        return switch (height) {
            case 'S' -> 'a';
            case 'E' -> 'z';
            default -> height;
        };
    }

    private boolean isPossibleToStepOntoNeighborPosition(char currentHeight, char neighborHeight) {
        return neighborHeight - currentHeight <= 1;
    }

    @Override
    public String solvePart1() {
        var heightMatrix = convertCharInputIntoMatrix(getInputLines());
        var graph = buildGraphFromMatrix(heightMatrix);

        var startNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals("S"))
                .findFirst().orElseThrow();

        return String.valueOf(Dijkstra
                .execute(graph, startNode, node -> node.getName().equals("E"))
                .getShortestPathLength().orElseThrow());
    }

    @Override
    public String solvePart2() {
        var heightMatrix = convertCharInputIntoMatrix(getInputLines());
        var graph = buildGraphFromMatrix(heightMatrix);

        return String.valueOf(graph.getNodes().stream()
                .filter(node -> node.getName().equals("a") || node.getName().equals("S"))
                .map(node -> Dijkstra.execute(graph, node, n -> n.getName().equals("E")).getShortestPathLength())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .findFirst().orElseThrow());
    }
}
