package util;

import java.util.*;

public class Tree {

    private File root;
    private final List<File> postOrderDfs = new ArrayList<>();

    public Tree() {
    }

    public File getRoot() {
        return root;
    }

    public List<File> getPostOrderDfs() {
        return postOrderDfs;
    }

    public void setRoot(File root) {
        this.root = root;
    }

    public void setNodeSizesAndInitializePostOrderDfsList() {
        setNodeSizesAndInitializePostOrderDfsList(root);
    }

    private void setNodeSizesAndInitializePostOrderDfsList(File node) {
        if (node == null)
            return;

        setNodeSizesAndInitializePostOrderDfsList(node.leftmostChild());

        if (node.isDir()) {
            node.setSize(node.getChildren().stream()
                    .mapToInt(File::getSize)
                    .sum());
        }
        postOrderDfs.add(node);

        setNodeSizesAndInitializePostOrderDfsList(node.rightSibling());
    }
}
