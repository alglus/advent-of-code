package aoc2022;

import util.Util;

import java.util.*;

import static util.Util.splitByCommaIgnoringSquareBrackets;

public class Day13 {

    /* --- Part One --- */
    public static int distressSignalPart1(List<String> input) {

        var packetPairsInRightOrder = new ArrayList<Integer>();

        for (int i = 0, pairIndex = 1; i < input.size(); i += 3, pairIndex++) {

            var packets = new Packets(input.get(i), input.get(i + 1));
            var areInRightOrder = arePacketsInRightOrder(packets);

            if (areInRightOrder.isEmpty()) {
                throw new IllegalArgumentException("Both packets are identical. This should not happen, because the " +
                        "exercise does not define a behavior for this case and the input should not have such packet pairs.");
            } else if (areInRightOrder.get()) {
                packetPairsInRightOrder.add(pairIndex);
            }
        }

        return packetPairsInRightOrder.stream()
                .reduce(Integer::sum)
                .orElse(0);
    }


    /* --- Part Two --- */
    public static int distressSignalPart2(List<String> input) {
        List<String> packets = Util.removeEmptyLines(input);
        packets.add("[[2]]");
        packets.add("[[6]]");

        List<String> orderedPackets = orderPackets(packets);

        return calculateDecoderKey(orderedPackets);
    }


    private record Packets(String l /* =left */, String r /* =right */) {
        public String[] values() {
            return new String[]{l, r};
        }

        @Override
        public String toString() {
            return "Packets(l=" + l + ", r=" + r + ")";
        }
    }

    private static Optional<Boolean> arePacketsInRightOrder(Packets packets) {

        var packetsContent = extractValuesFromList(packets);

        return compareListValues(packetsContent);
    }

    private static Packets extractValuesFromList(Packets packets) {
        var extractedStrings = Arrays.stream(packets.values())
                .map(packet -> {
                    if (isList(packet)) return removeSurroundingBrackets(packet);
                    return packet;
                })
                .toArray(String[]::new);

        return new Packets(extractedStrings[0], extractedStrings[1]);
    }

    private static String removeSurroundingBrackets(String list) {
        return list.substring(1, list.length() - 1);
    }

    private static Optional<Boolean> compareListValues(Packets packetsContent) {
        var leftValues = splitIntoValues(packetsContent.l());
        var rightValues = splitIntoValues(packetsContent.r());

        for (int i = 0; i < Math.max(leftValues.length, rightValues.length); i++) {

            if (i == leftValues.length) return Optional.of(true);
            if (i == rightValues.length) return Optional.of(false);

            String leftVal = leftValues[i];
            String rightVal = rightValues[i];

            if (isList(leftVal) || isList(rightVal)) {
                var isSubListInRightOrder = arePacketsInRightOrder(new Packets(leftVal, rightVal));
                if (isSubListInRightOrder.isEmpty())
                    continue;
                return isSubListInRightOrder;
            }

            if (Integer.parseInt(leftVal) < Integer.parseInt(rightVal)) return Optional.of(true);
            if (Integer.parseInt(leftVal) > Integer.parseInt(rightVal)) return Optional.of(false);
        }

        return Optional.empty();
    }

    private static boolean isList(String value) {
        return value.length() > 0 && value.charAt(0) == '[';
    }

    private static String[] splitIntoValues(String list) {
        if (list.isBlank())
            return new String[0];
        return splitByCommaIgnoringSquareBrackets(list).toArray(String[]::new);
    }

    private static List<String> orderPackets(List<String> packets) {

        List<String> orderedPackets = new ArrayList<>(packets);

        Set<Packets> wrongPairs = new HashSet<>();

        while (true) {
            System.out.println("-----");
            orderedPackets.forEach(System.out::println);

            boolean allOrdered = true;
            var indexesToSwap = new ArrayList<Integer>(2);

            for (int i = 0; i < orderedPackets.size(); i++) {
                if (i == orderedPackets.size() - 1) {
                    if (indexesToSwap.size() == 0) throw new IllegalStateException("no no no");
                    indexesToSwap.add(i);
                    swapPackets(orderedPackets, indexesToSwap);
                    allOrdered = false;
                    break;
                }
                var pairTest = new Packets(orderedPackets.get(i), orderedPackets.get(i + 1));
                var pairIsOrdered = arePacketsInRightOrder(pairTest);

                if (pairIsOrdered.isEmpty() || !pairIsOrdered.get()) {
                    if (indexesToSwap.size() == 1 && !wrongPairs.contains(pairTest)) {
                        indexesToSwap.add(i);
                        wrongPairs.add(pairTest);
                    }

                    if (indexesToSwap.isEmpty()) {
                        indexesToSwap.add(i);
                    }

                    if (indexesToSwap.size() == 2) {
                        swapPackets(orderedPackets, indexesToSwap);
                        allOrdered = false;
                        break;
                    }
                }

                if (indexesToSwap.size() == 1) {
                    allOrdered = false;
                }
            }

            if (allOrdered) break;
        }

        return orderedPackets;
    }

    private static void swapPackets(List<String> packets, ArrayList<Integer> indexesToSwap) {
        int i1 = indexesToSwap.get(0);
        int i2 = indexesToSwap.get(1);

        String packet1 = packets.get(i1);
        packets.set(i1, packets.get(i2));
        packets.set(i2, packet1);
    }

    private static int calculateDecoderKey(List<String> packets) {
        int decoderKey = 1;
        boolean found2 = false;
        boolean found6 = false;

        for (int i = 1; i <= packets.size(); i++) {
            var packet = packets.get(i);

            if (packet.equals("[[2]]")) {
                found2 = true;
                decoderKey *= i;
            }

            if (packet.equals("[[6]]")) {
                found6 = true;
                decoderKey *= i;
            }

            if (found2 && found6) break;
        }
        return decoderKey;
    }


    public static void main(String[] args) {
//        String s1 = "[1,1,5,1,1]";
//        String s2 = "[[1],[2,3,4]]";
//
//        System.out.println(arePacketsInRightOrder(new Packets(s1,s2)));

        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_13.txt");

        System.out.println("Part 1: " + Day13.distressSignalPart1(input));
    }
}
