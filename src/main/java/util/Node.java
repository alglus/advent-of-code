package util;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final String name;
    private int size;
    private final Types type;
    private final Node parent;

    private int ownIndexAmongSiblings;
    private final List<Node> children = new ArrayList<>();

    public Node(String name, int size, Types type, Node parent) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOwnIndexAmongSiblings(int index) {
        this.ownIndexAmongSiblings = index;
    }

    public boolean isFile() {
        return type == Types.FILE;
    }

    public boolean isDir() {
        return type == Types.DIR;
    }

    public void addChild(Node child) {
        child.setOwnIndexAmongSiblings(this.children.size());
        children.add(child);
    }

    public Node getChildByName(String searchedName) {
        return children.stream()
                .filter(node -> node.getName().equals(searchedName))
                .findFirst()
                .orElse(null);
    }

    public Node leftmostChild() {
        if (isFile())
            return null;

        return children.get(0);
    }

    public Node rightSibling() {
        int rightSiblingIndex = ownIndexAmongSiblings + 1;

        if (parent == null || parent.getChildren().size() == rightSiblingIndex) {
            return null;
        }

        return parent.getChildren().get(rightSiblingIndex);
    }

    public enum Types {
        DIR, FILE
    }
}
