package aoc2024;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import util.MathUtil;

import java.util.*;
import java.util.stream.IntStream;

public class Day09 extends Puzzle2024 {

    public Day09() {
        super(9);
    }

    public static void main(final String[] args) {
        new Day09().printSolutions();
    }

    private static long getFilesystemChecksum(final String input) {
        final BlocksFilesystem filesystem = createBlocksFilesystem(input);

        int indexToMoveFrom = filesystem.disk().size() - 1;
        int indexToMoveTo = filesystem.freeBlocks().pollFirst();
        Integer blockToMove;

        while (indexToMoveTo < indexToMoveFrom) {

            while (true) {
                blockToMove = filesystem.disk().get(indexToMoveFrom);
                if (blockToMove == null) {
                    indexToMoveFrom--;
                } else {
                    break;
                }
            }

            filesystem.disk().set(indexToMoveTo, blockToMove);

            indexToMoveTo = filesystem.freeBlocks().pollFirst();
            indexToMoveFrom--;
        }

        filesystem.disk().subList(indexToMoveFrom + 1, filesystem.disk().size()).clear();


        return IntStream.range(0, filesystem.disk().size())
                .mapToLong(i -> (long) i * filesystem.disk().get(i))
                .sum();
    }

    private static BlocksFilesystem createBlocksFilesystem(final String input) {
        final int[] diskMap = convertToDiskMap(input);

        final int blocksCount = Arrays.stream(diskMap).sum();

        final List<Integer> disk = new ArrayList<>(blocksCount);
        final Deque<Integer> freeBlocks = new ArrayDeque<>();
        int blocksUsed = 0;

        for (int i = 0; i < diskMap.length; i++) {
            final int blockSize = diskMap[i];

            for (int j = blocksUsed; j < blocksUsed + blockSize; j++) {
                if (MathUtil.isEven(i)) {
                    disk.add(i / 2);
                } else {
                    disk.add(null);
                    freeBlocks.add(j);
                }
            }
            blocksUsed += blockSize;
        }

        return new BlocksFilesystem(disk, freeBlocks);
    }

    private static int[] convertToDiskMap(final String input) {
        return input.chars()
                .mapToObj(c -> (char) c)
                .mapToInt(c -> c - '0')
                .toArray();
    }

    private static long compactFilesAndGetChecksum(final String input) {
        final Filesystem filesystem = createFilesystem(input);

        for (int i = filesystem.files().size() - 1; i >= 0; i--) {
            final File file = filesystem.files().get(i);

            for (final FreeBlock freeBlock : filesystem.freeBlocks()) {
                if (freeBlock.getStartIndex() >= file.startIndex()) {
                    break;
                }
                if (freeBlock.getLength() < file.length()) {
                    continue;
                }

                for (int k = freeBlock.getStartIndex(); k < freeBlock.getStartIndex() + file.length(); k++) {
                    filesystem.disk().set(k, file.id());
                }
                for (int k = file.startIndex(); k < file.startIndex() + file.length(); k++) {
                    filesystem.disk().set(k, null);
                }
                freeBlock.setLength(freeBlock.getLength() - file.length());
                freeBlock.setStartIndex(freeBlock.getStartIndex() + file.length());
                break;
            }
        }

        return IntStream.range(0, filesystem.disk().size())
                .mapToLong(i -> (long) i * (filesystem.disk().get(i) == null ? 0 : filesystem.disk().get(i)))
                .sum();
    }

    private static Filesystem createFilesystem(final String input) {
        final int[] diskMap = convertToDiskMap(input);

        final int blocksCount = Arrays.stream(diskMap).sum();

        final List<Integer> disk = new ArrayList<>(blocksCount);
        final List<FreeBlock> freeBlocks = new ArrayList<>();
        final List<File> files = new ArrayList<>();
        int blocksUsed = 0;

        for (int i = 0; i < diskMap.length; i++) {
            final int blockSize = diskMap[i];
            if (MathUtil.isEven(i)) {
                files.add(new File(blocksUsed, blockSize, i / 2));
            } else {
                freeBlocks.add(new FreeBlock(blocksUsed, blockSize));
            }

            for (int j = blocksUsed; j < blocksUsed + blockSize; j++) {
                if (MathUtil.isEven(i)) {
                    disk.add(i / 2);
                } else {
                    disk.add(null);
                }
            }
            blocksUsed += blockSize;
        }

        return new Filesystem(disk, freeBlocks, files);
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getFilesystemChecksum(getInputAsString()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(compactFilesAndGetChecksum(getInputAsString()));
    }

    private record BlocksFilesystem(List<Integer> disk, Deque<Integer> freeBlocks) {
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class FreeBlock {
        private int startIndex;
        private int length;
    }

    private record File(int startIndex, int length, int id) {
    }

    private record Filesystem(List<Integer> disk, List<FreeBlock> freeBlocks, List<File> files) {
    }
}
