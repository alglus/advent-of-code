package util;

import java.util.*;

public class Tree {

    private Node root;
    private final List<Node> postOrderDfs = new ArrayList<>();

    public Tree() {
    }

    public Node getRoot() {
        return root;
    }

    public List<Node> getPostOrderDfs() {
        return postOrderDfs;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setNodeSizesAndInitializePostOrderDfsList() {
        setNodeSizesAndInitializePostOrderDfsList(root);
    }

    private void setNodeSizesAndInitializePostOrderDfsList(Node node) {
        if (node == null)
            return;

        setNodeSizesAndInitializePostOrderDfsList(node.leftmostChild());

        if (node.isDir()) {
            node.setSize(node.getChildren().stream()
                    .mapToInt(Node::getSize)
                    .sum());
        }
        postOrderDfs.add(node);

        setNodeSizesAndInitializePostOrderDfsList(node.rightSibling());
    }
}
