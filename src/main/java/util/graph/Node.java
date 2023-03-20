package util.graph;

import java.util.List;

public interface Node {

    String getId();

    String getName();

    void setName(String name);

    List<Edge> getEdges();

    void addEdge(Edge edge);

}
