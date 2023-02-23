package aoc2022;

import util.Util;

import java.util.Arrays;
import java.util.List;

public class Day08 {

    /* --- Part One --- */
    public static int treetopTreeHousePart1(List<String> input) {

        Tree[][] grid = convertToTreeGrid(input);

        markHiddenTrees(grid);

        return countVisibleTrees(grid);
    }


    /* --- Part Two --- */
    public static int treetopTreeHousePart2(List<String> input) {

        Tree[][] grid = convertToTreeGrid(input);

        return findHighestScenicScore(grid);
    }


    private static Tree[][] convertToTreeGrid(List<String> input) {
        int gridWidth = input.get(0).length();
        int gridHeight = input.size();

        Tree[][] grid = new Tree[gridHeight][gridWidth];

        for (int r = 0; r < gridHeight; r++) {
            for (int c = 0; c < gridWidth; c++) {
                grid[r][c] = new Tree(input.get(r).substring(c, c + 1));
            }
        }

        return grid;
    }

    private static void markHiddenTrees(Tree[][] grid) {
        markFromLeftToRight(grid);
        markFromRightToLeft(grid);
        markFromTopToBottom(grid);
        markFromBottomToTop(grid);
    }

    private static void markFromLeftToRight(Tree[][] grid) {
        checkHorizontally(grid, 0, grid[0].length - 2, +1, true);
    }

    private static void markFromRightToLeft(Tree[][] grid) {
        checkHorizontally(grid, grid[0].length - 1, 1, -1, false);
    }

    private static void markFromTopToBottom(Tree[][] grid) {
        checkVertically(grid, 0, grid.length - 2, +1, false);
    }

    private static void markFromBottomToTop(Tree[][] grid) {
        checkVertically(grid, grid.length - 1, 1, -1, false);
    }

    private static void checkHorizontally(Tree[][] grid, int startIndex, int endIndex, int increment, boolean firstCheck) {
        int gridHeight = grid.length;

        int maxTreeHeight = -1;

        for (int row = 1; row < gridHeight - 1; row++) {
            for (int col = startIndex; increment > 0 ? col <= endIndex : col >= endIndex; col += increment) {
                maxTreeHeight = markTreeHiddenOrRemarkAsVisible(grid[row][col], firstCheck, maxTreeHeight);
            }
            maxTreeHeight = -1;
        }
    }

    private static void checkVertically(Tree[][] grid, int startIndex, int endIndex, int increment, boolean firstCheck) {
        int gridWidth = grid[0].length;

        int maxTreeHeight = -1;

        for (int col = 1; col < gridWidth - 1; col++) {
            for (int row = startIndex; increment > 0 ? row <= endIndex : row >= endIndex; row += increment) {
                maxTreeHeight = markTreeHiddenOrRemarkAsVisible(grid[row][col], firstCheck, maxTreeHeight);
            }
            maxTreeHeight = -1;
        }
    }

    private static int markTreeHiddenOrRemarkAsVisible(Tree tree, boolean firstCheck, int maxTreeHeight) {
        int treeHeight = tree.getHeight();

        if (treeHeight <= maxTreeHeight) {
            if (firstCheck) {
                tree.setHidden();
            }
        } else {
            tree.setVisible();
            maxTreeHeight = treeHeight;
        }

        return maxTreeHeight;
    }

    private static int countVisibleTrees(Tree[][] grid) {
        return (int) Arrays.stream(grid).flatMap(Arrays::stream).filter(Tree::isVisible).count();
    }

    private static class Tree {
        private final int height;

        private boolean hidden = false;

        public Tree(String heightString) {
            this.height = Integer.parseInt(heightString);
        }

        public int getHeight() {
            return height;
        }

        public boolean isVisible() {
            return !hidden;
        }

        public void setHidden() {
            this.hidden = true;
        }

        public void setVisible() {
            this.hidden = false;
        }

        @Override
        public String toString() {
            return height + (hidden ? "H" : " ");
        }

    }

    private static int findHighestScenicScore(Tree[][] grid) {
        int highestScenicScore = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                int scenicScore = calculateScenicScore(grid, row, col);
                highestScenicScore = Math.max(scenicScore, highestScenicScore);
            }
        }

        return highestScenicScore;
    }

    private static int calculateScenicScore(Tree[][] grid, int row, int col) {
        int viewingDistanceLeft = measureViewingDistanceLeft(grid, row, col);
        int viewingDistanceRight = measureViewingDistanceRight(grid, row, col);
        int viewingDistanceUp = measureViewingDistanceUp(grid, row, col);
        int viewingDistanceDown = measureViewingDistanceDown(grid, row, col);

        return viewingDistanceLeft * viewingDistanceRight * viewingDistanceUp * viewingDistanceDown;
    }

    private static int measureViewingDistanceLeft(Tree[][] grid, int row, int col) {
        int treeHeight = grid[row][col].getHeight();
        int visibleTrees = 0;

        for (int c = col - 1; c >= 0; c--) {
            int otherTreeHeight = grid[row][c].getHeight();

            if (otherTreeHeight >= treeHeight) {
                visibleTrees++;
                break;
            }
            visibleTrees++;
        }
        return visibleTrees;
    }

    private static int measureViewingDistanceRight(Tree[][] grid, int row, int col) {
        int treeHeight = grid[row][col].getHeight();
        int visibleTrees = 0;

        for (int c = col + 1; c < grid[row].length; c++) {
            int otherTreeHeight = grid[row][c].getHeight();

            if (otherTreeHeight >= treeHeight) {
                visibleTrees++;
                break;
            }
            visibleTrees++;
        }
        return visibleTrees;
    }

    private static int measureViewingDistanceUp(Tree[][] grid, int row, int col) {
        int treeHeight = grid[row][col].getHeight();
        int visibleTrees = 0;

        for (int r = row - 1; r >= 0; r--) {
            int otherTreeHeight = grid[r][col].getHeight();

            if (otherTreeHeight >= treeHeight) {
                visibleTrees++;
                break;
            }
            visibleTrees++;
        }
        return visibleTrees;
    }

    private static int measureViewingDistanceDown(Tree[][] grid, int row, int col) {
        int treeHeight = grid[row][col].getHeight();
        int visibleTrees = 0;

        for (int r = row + 1; r < grid.length; r++) {
            int otherTreeHeight = grid[r][col].getHeight();

            if (otherTreeHeight >= treeHeight) {
                visibleTrees++;
                break;
            }
            visibleTrees++;
        }
        return visibleTrees;
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_08.txt");

        System.out.println("Part 1: " + Day08.treetopTreeHousePart1(input));
        System.out.println("Part 2: " + Day08.treetopTreeHousePart2(input));
    }
}
