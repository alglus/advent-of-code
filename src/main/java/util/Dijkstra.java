package util;

import util.graph.Graph;
import util.graph.Node;

import java.util.*;
import java.util.List;
import java.util.function.Function;

public class Dijkstra {

    private final Graph graph;
    private final Node startNode;
    private final Function<Node, Boolean> goalReached;

    private final Map<Node, DijkstraNode> dijkstraNodes = new HashMap<>();
    private final Queue<DijkstraNode> dijkstraNodesToVisit = new PriorityQueue<>();
    private List<Node> shortestPath = null;
    private DijkstraNode endDijkstraNode = null;

    /**
     * Create an instance, holding the results of the dijkstra algorithm performed on a given graph.
     *
     * @param graph:       the graph, on which to perform the dijkstra algorithm
     * @param startNode:   starting node for the shortest path search
     * @param goalReached: function based on the current node being analyzed, that determines,
     *                     whether the search has been finished
     * @return new instance of Dijkstra with the shortest path already calculated
     */
    public static Dijkstra execute(Graph graph, Node startNode, Function<Node, Boolean> goalReached) {
        var dijkstra = new Dijkstra(graph, startNode, goalReached);
        dijkstra.findShortestPath();
        return dijkstra;
    }

    private Dijkstra(Graph graph, Node startNode, Function<Node, Boolean> goalReached) {
        this.graph = graph;
        this.startNode = startNode;
        this.goalReached = goalReached;
    }

    private void findShortestPath() {
        initAllDijkstraNodes();
        initStartDijkstraNode();

        while (!dijkstraNodesToVisit.isEmpty()) {

            var currentDijkstraNode = dijkstraNodesToVisit.poll();

            visitAllEdges(currentDijkstraNode);

            if (goalReached.apply(currentDijkstraNode.getNode())) {
                endDijkstraNode = currentDijkstraNode;
                break;
            }
        }
    }

    private void initAllDijkstraNodes() {
        graph.getNodes().forEach(node -> dijkstraNodes.put(node, new DijkstraNode(node)));
    }

    private void initStartDijkstraNode() {
        var startDijkstraNode = dijkstraNodes.get(startNode);
        startDijkstraNode.setDistance(0);
        dijkstraNodesToVisit.add(startDijkstraNode);
    }

    private void visitAllEdges(DijkstraNode dijkstraNode) {
        dijkstraNode.setVisited(true);

        for (var edge : dijkstraNode.getNode().getEdges()) {
            var toNode = edge.to();
            var toDijkstraNode = dijkstraNodes.get(toNode);

            int currentToNodeDistance = toDijkstraNode.getDistance();
            int newToNodeDistance = dijkstraNode.getDistance() + edge.weight();

            if (newToNodeDistance < currentToNodeDistance) {
                toDijkstraNode.setDistance(newToNodeDistance);
                toDijkstraNode.setParent(dijkstraNode);

                updateNodesToVisitWith(toDijkstraNode);
            }
        }
    }

    private void updateNodesToVisitWith(DijkstraNode newDijkstraNode) {
        if (!newDijkstraNode.isVisited()) {
            dijkstraNodesToVisit.add(newDijkstraNode);
        } else {
            resortQueueAfterUpdatingDistanceOf(newDijkstraNode);
        }
    }

    private void resortQueueAfterUpdatingDistanceOf(DijkstraNode dijkstraNode) {
        dijkstraNodesToVisit.remove(dijkstraNode);
        dijkstraNodesToVisit.add(dijkstraNode);
    }

    /**
     * Get the length of the shortest path in the given graph of this Dijkstra instance,
     * from the given start node to the node, defined by the 'goalReached' function.
     *
     * @return an empty Optional, if no path has been found, or an Optional containing the length of the sortest path
     */
    public Optional<Integer> getShortestPathLength() {
        return (endDijkstraNode == null) ?
                Optional.empty() :
                Optional.of(endDijkstraNode.getDistance());
    }

    /**
     * Get the shortest path in the given graph of this Dijkstra instance, from the given start node to the node
     * defined by the 'goalReached' function.
     *
     * @return list of nodes on the path from the start node to the goal. If no path has been found, an empty list is returned.
     */
    public List<Node> getShortestPath() {
        if (shortestPath == null)
            shortestPath = buildShortestPath();

        return shortestPath;
    }

    private List<Node> buildShortestPath() {
        List<Node> shortestPath = new ArrayList<>();
        var currentDijkstraNode = endDijkstraNode;

        while (true) {
            if (currentDijkstraNode == null) {
                return Collections.emptyList();
            }

            shortestPath.add(currentDijkstraNode.getNode());

            if (currentDijkstraNode.getNode() == startNode) {
                break;
            }

            currentDijkstraNode = currentDijkstraNode.getParent();
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }

    private static class DijkstraNode implements Comparable<DijkstraNode> {

        private final Node node;
        private int distance = Integer.MAX_VALUE;
        private boolean visited = false;
        private DijkstraNode parent = null;

        public DijkstraNode(Node node) {
            this.node = node;
        }

        public Node getNode() {
            return node;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public DijkstraNode getParent() {
            return parent;
        }

        public void setParent(DijkstraNode parent) {
            this.parent = parent;
        }

        @Override
        public int compareTo(DijkstraNode other) {
            var compareResult = Integer.compare(distance, other.getDistance());

            if (compareResult == 0)
                compareResult = node.getId().compareTo(other.getNode().getId());

            return compareResult;
        }
    }

}
