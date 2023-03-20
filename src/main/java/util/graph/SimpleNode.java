package util.graph;

import java.util.ArrayList;
import java.util.List;

public class SimpleNode implements Node {

    private final String id;
    private String name;
    private final List<Edge> edges = new ArrayList<>();

    public SimpleNode(String id) {
        this.id = id;
    }

    public SimpleNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleNode(String id, char name) {
        this.id = id;
        this.name = String.valueOf(name);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return name;
    }

}
