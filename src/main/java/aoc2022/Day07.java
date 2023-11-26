package aoc2022;

import util.File;
import util.Tree;

import java.util.List;

public class Day07 extends Puzzle2022 {

    public Day07() {
        super(7);
    }

    public static void main(String[] args) {
        new Day07().printSolutions();
    }

    private Tree createTree(List<String> input) {
        var tree = new Tree();
        File currentFile = null;

        for (String line : input) {
            if (line.equals("$ cd /")) {
                var root = new File("/", -1, File.Types.DIR, null);
                tree.setRoot(root);
                currentFile = root;

            } else if (line.startsWith("$")) {
                currentFile = executeCommand(line, currentFile);

            } else {
                addNodeToTree(line, currentFile);
            }
        }

        return tree;
    }

    private File executeCommand(String line, File currentFile) {
        if (line.equals("$ ls"))
            return currentFile;

        if (line.equals("$ cd ..")) {
            return currentFile.getParent();
        }

        if (line.startsWith("$ cd ")) {
            String dirName = line.substring(5);
            return currentFile.getChildByName(dirName);
        }

        return null;
    }

    private void addNodeToTree(String nodeInput, File parent) {
        var newFile = parseNodeInput(nodeInput, parent);

        parent.addChild(newFile);
    }

    private File parseNodeInput(String line, File parent) {
        var nodeData = line.split(" ");

        if (nodeData[0].equals("dir")) {
            return new File(nodeData[1], -1, File.Types.DIR, parent);

        } else {
            return new File(nodeData[1], Integer.parseInt(nodeData[0]), File.Types.FILE, parent);
        }
    }

    private int getTotalSizeOfDirsSmallerOrEqualThanMax(Tree tree, int maxSize) {
        return tree.getPostOrderDfs().stream()
                .filter(File::isDir)
                .filter(file -> file.getSize() <= maxSize)
                .mapToInt(File::getSize)
                .sum();
    }

    private int getSmallestDirSizeEnoughToPerformUpdate(Tree tree, int totalDiskSpace, int spaceForUpdate) {
        int unusedSpace = totalDiskSpace - tree.getRoot().getSize();
        int requiredDeletionSpace = spaceForUpdate - unusedSpace;

        assert (requiredDeletionSpace > 0);

        return tree.getPostOrderDfs().stream()
                .filter(File::isDir)
                .mapToInt(File::getSize)
                .filter(size -> size >= requiredDeletionSpace)
                .sorted()
                .findFirst()
                .getAsInt();
    }

    @Override
    public String solvePart1() {
        var tree = createTree(getInputLines());

        tree.setNodeSizesAndInitializePostOrderDfsList();

        return String.valueOf(getTotalSizeOfDirsSmallerOrEqualThanMax(tree, 100000));
    }

    @Override
    public String solvePart2() {
        var tree = createTree(getInputLines());

        tree.setNodeSizesAndInitializePostOrderDfsList();

        return String.valueOf(getSmallestDirSizeEnoughToPerformUpdate(tree, 70000000, 30000000));
    }
}
