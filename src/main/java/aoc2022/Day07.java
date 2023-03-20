package aoc2022;

import util.File;
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
        File currentFile = null;

        for (String line : input) {

            if (line.equals("$ cd /")) {

                File root = new File("/", -1, File.Types.DIR, null);
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

    private static File executeCommand(String line, File currentFile) {

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

    private static void addNodeToTree(String nodeInput, File parent) {

        File newFile = parseNodeInput(nodeInput, parent);

        parent.addChild(newFile);
    }

    private static File parseNodeInput(String line, File parent) {

        String[] nodeData = line.split(" ");

        if (nodeData[0].equals("dir")) {
            return new File(nodeData[1], -1, File.Types.DIR, parent);

        } else {
            return new File(nodeData[1], Integer.parseInt(nodeData[0]), File.Types.FILE, parent);
        }
    }

    private static int getTotalSizeOfDirsSmallerOrEqualThanMax(Tree tree, int maxSize) {
        return tree.getPostOrderDfs().stream()
                .filter(File::isDir)
                .filter(file -> file.getSize() <= maxSize)
                .mapToInt(File::getSize)
                .sum();
    }

    private static int getSmallestDirSizeEnoughToPerformUpdate(Tree tree, int totalDiskSpace, int spaceForUpdate) {
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


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_07.txt");

        System.out.println("Part 1: " + noSpaceLeftOnDevicePart1(input));
        System.out.println("Part 2: " + noSpaceLeftOnDevicePart2(input));
    }
}
