package aoc2022;

import util.*;
import util.graph.*;

import java.util.List;
import java.util.Optional;

import static util.Util.concatenate;
import static util.Util.convertCharInputIntoMatrix;

public class Day12 {

    /* --- Part One --- */
    public static int hillClimbingPart1(List<String> input) {

        var heightMatrix = convertCharInputIntoMatrix(input);
        var graph = buildGraphFromMatrix(heightMatrix);

        var startNode = graph.getNodes().stream()
                .filter(node -> node.getName().equals("S"))
                .findFirst().orElseThrow();

        return Dijkstra
                .execute(graph, startNode, node -> node.getName().equals("E"))
                .getShortestPathLength().orElseThrow();
    }


    /* --- Part Two --- */
    public static int hillClimbingPart2(List<String> input) {

        var heightMatrix = convertCharInputIntoMatrix(input);
        var graph = buildGraphFromMatrix(heightMatrix);

        return graph.getNodes().stream()
                .filter(node -> node.getName().equals("a") || node.getName().equals("S"))
                .map(node -> Dijkstra.execute(graph, node, n -> n.getName().equals("E")).getShortestPathLength())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted()
                .findFirst().orElseThrow();
    }


    private static Graph buildGraphFromMatrix(final char[][] heightMatrix) {

        final var graph = new Graph();
        final int weight = 1;

        final int matrixWidth = heightMatrix[0].length;
        final int matrixHeight = heightMatrix.length;

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {

                var currentPosition = new Position(x, y, new Range(0, matrixWidth - 1), new Range(0, matrixHeight - 1));
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

    private static char correctHeightOfStartAndEndNodes(char height) {
        return switch (height) {
            case 'S' -> 'a';
            case 'E' -> 'z';
            default -> height;
        };
    }

    private static boolean isPossibleToStepOntoNeighborPosition(char currentHeight, char neighborHeight) {
        return neighborHeight - currentHeight <= 1;
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_12.txt");

        System.out.println("Part 1: " + Day12.hillClimbingPart1(input));
        System.out.println("Part 2: " + Day12.hillClimbingPart2(input));
    }

}
