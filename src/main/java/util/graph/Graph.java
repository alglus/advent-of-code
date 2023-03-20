package util.graph;

import java.util.*;

public class Graph {

    private final Map<String, Node> nodes = new HashMap<>();

    public Node addNodeOrGetExisting(Node node) {
        if (nodes.containsKey(node.getId()))
            return nodes.get(node.getId());

        nodes.put(node.getId(), node);
        return node;
    }

    public Collection<Node> getNodes() {
        return nodes.values();
    }

}
