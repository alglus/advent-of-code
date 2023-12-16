package aoc2023;

import util.MathUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day08 extends Puzzle2023 {

    public Day08() {
        super(8);
    }

    public static void main(String[] args) {
        new Day08().printSolutions();
    }

    private long getStepsUntilZZZ(String instructions, Map<String, NextNodes> network) {
        return getStepsUntilReachingDefinedNode("AAA", instructions, network, node -> node.equals("ZZZ"));
    }

    private long getStepsUntilAllPathsEndWithZ(String instructions, Map<String, NextNodes> network) {
        var startNodes = getAllNodesEndingWithA(network);

        return startNodes.stream()
                .map(startNode -> getStepsUntilReachingDefinedNode(startNode, instructions, network, node -> node.endsWith("Z")))
                .reduce(MathUtil::lcm)
                .orElseThrow();
    }

    private long getStepsUntilReachingDefinedNode(String startNode, String instructions, Map<String, NextNodes> network,
                                                  Function<String, Boolean> hasReachedEndNode) {
        var currentNode = startNode;
        var steps = 0;
        var i = 0;

        while (!hasReachedEndNode.apply(currentNode)) {
            var instruction = instructions.charAt(i % instructions.length());
            currentNode = network.get(currentNode).get(instruction);
            steps++;
            i++;
        }

        return steps;
    }

    private List<String> getAllNodesEndingWithA(Map<String, NextNodes> network) {
        return network.keySet().stream()
                .filter(node -> node.endsWith("A"))
                .toList();
    }

    private String getInstructions(List<String> input) {
        return input.get(0);
    }

    private Map<String, NextNodes> getNetwork(List<String> input) {
        return input.stream()
                .skip(2)
                .map(line -> line.split(" = "))
                .collect(Collectors.toMap(ls -> ls[0], ls -> convertToNextNodes(ls[1])));
    }

    private NextNodes convertToNextNodes(String string) {
        var strignWithoutParenthesis = string.substring(1, string.length() - 1);
        var nextNodes = strignWithoutParenthesis.split(", ");
        return new NextNodes(nextNodes[0], nextNodes[1]);
    }

    @Override
    public String solvePart1() {
        var input = getInputLines();
        return String.valueOf(getStepsUntilZZZ(getInstructions(input), getNetwork(input)));
    }

    @Override
    public String solvePart2() {
        var input = getInputLines();
        return String.valueOf(getStepsUntilAllPathsEndWithZ(getInstructions(input), getNetwork(input)));
    }

    private record NextNodes(String left, String right) {
        public String get(char direction) {
            if (direction == 'L') {
                return left;
            } else if (direction == 'R') {
                return right;
            } else {
                throw new IllegalArgumentException("Only R or L allowed.");
            }
        }
    }

}
