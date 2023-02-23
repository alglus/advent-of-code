package aoc2022;

import util.Node;
import util.Tree;
import util.Util;

import java.util.List;

public class Day07 {

    /* --- Part One --- */
    public static int noSpaceLeftOnDevicePart1(List<String> input) {

        Tree tree = createTree(input);

        tree.setNodeSizesAndInitializePostOrderDfsList();

        return getTotalSizeOfDirsSmallerOrEqualThanMax(tree, 100000);
    }


    /* --- Part Two --- */
    public static int noSpaceLeftOnDevicePart2(List<String> input) {

        Tree tree = createTree(input);

        tree.setNodeSizesAndInitializePostOrderDfsList();

        return getSmallestDirSizeEnoughToPerformUpdate(tree, 70000000, 30000000);
    }


    private static Tree createTree(List<String> input) {

        Tree tree = new Tree();
        Node currentNode = null;

        for (String line : input) {

            if (line.equals("$ cd /")) {

                Node root = new Node("/", -1, Node.Types.DIR, null);
                tree.setRoot(root);
                currentNode = root;

            } else if (line.startsWith("$")) {

                currentNode = executeCommand(line, currentNode);

            } else {

                addNodeToTree(line, currentNode);
            }
        }

        return tree;
    }

    private static Node executeCommand(String line, Node currentNode) {

        if (line.equals("$ ls"))
            return currentNode;

        if (line.equals("$ cd ..")) {
            return currentNode.getParent();
        }

        if (line.startsWith("$ cd ")) {
            String dirName = line.substring(5);
            return currentNode.getChildByName(dirName);
        }

        return null;
    }

    private static void addNodeToTree(String nodeInput, Node parent) {

        Node newNode = parseNodeInput(nodeInput, parent);

        parent.addChild(newNode);
    }

    private static Node parseNodeInput(String line, Node parent) {

        String[] nodeData = line.split(" ");

        if (nodeData[0].equals("dir")) {
            return new Node(nodeData[1], -1, Node.Types.DIR, parent);

        } else {
            return new Node(nodeData[1], Integer.parseInt(nodeData[0]), Node.Types.FILE, parent);
        }
    }

    private static int getTotalSizeOfDirsSmallerOrEqualThanMax(Tree tree, int maxSize) {
        return tree.getPostOrderDfs().stream()
                .filter(Node::isDir)
                .filter(node -> node.getSize() <= maxSize)
                .mapToInt(Node::getSize)
                .sum();
    }

    private static int getSmallestDirSizeEnoughToPerformUpdate(Tree tree, int totalDiskSpace, int spaceForUpdate) {
        int unusedSpace = totalDiskSpace - tree.getRoot().getSize();
        int requiredDeletionSpace = spaceForUpdate - unusedSpace;

        assert (requiredDeletionSpace > 0);

        return tree.getPostOrderDfs().stream()
                .filter(Node::isDir)
                .mapToInt(Node::getSize)
                .filter(size -> size >= requiredDeletionSpace)
                .sorted()
                .findFirst()
                .getAsInt();
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_07.txt");

        System.out.println("Part 1: " + noSpaceLeftOnDevicePart1(input));
        System.out.println("Part 2: " + noSpaceLeftOnDevicePart2(input));
    }
}
