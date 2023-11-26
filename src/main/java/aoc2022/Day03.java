package aoc2022;

import java.util.HashMap;
import java.util.Map;

public class Day03 extends Puzzle2022 {

    public Day03() {
        super(3);
    }

    public static void main(String[] args) {
        new Day03().printSolutions();
    }

    private char findCommonItemInBothCompartments(String itemsList) {
        int halfLength = itemsList.length() / 2;
        Map<Character, Integer> foundChars = new HashMap<>();

        for (int i1 = 0; i1 < halfLength; i1++) {
            foundChars.merge(itemsList.charAt(i1), 1, Integer::sum);
        }

        for (int i2 = halfLength; i2 < itemsList.length(); i2++) {
            char itemHalf2 = itemsList.charAt(i2);

            if (foundChars.containsKey(itemHalf2)) {
                return itemHalf2;
            }
        }

        return 0;
    }

    private char findCommonItemInAllThreeRucksacks(String itemsElf1, String itemsElf2, String itemsElf3) {
        Map<Character, Integer> foundChars = new HashMap<>();

        for (int i1 = 0; i1 < itemsElf1.length(); i1++) {
            foundChars.put(itemsElf1.charAt(i1), 1);
        }

        for (int i2 = 0; i2 < itemsElf2.length(); i2++) {
            char itemElf2 = itemsElf2.charAt(i2);

            if (foundChars.containsKey(itemElf2)) {
                foundChars.put(itemElf2, 2);
            }
        }

        for (int i3 = 0; i3 < itemsElf3.length(); i3++) {
            char itemElf3 = itemsElf3.charAt(i3);

            if (foundChars.containsKey(itemElf3) && foundChars.get(itemElf3) == 2) {
                return itemElf3;
            }
        }

        return 0;
    }

    private int convertItemToPriority(char commonItem) {
        if (commonItem == 0) return 0;

        if (Character.isLowerCase(commonItem)) {
            return commonItem - 'a' + 1;
        } else {
            return commonItem - 'A' + 27;
        }
    }

    @Override
    public String solvePart1() {
        long prioritiesSum = 0;

        for (String itemsList : getInputLines()) {
            var commonItem = findCommonItemInBothCompartments(itemsList);
            var priority = convertItemToPriority(commonItem);

            prioritiesSum += priority;
        }

        return String.valueOf(prioritiesSum);
    }

    @Override
    public String solvePart2() {
        long prioritiesSum = 0;
        var input = getInputLines();

        for (int groupStart = 0; groupStart < input.size(); groupStart += 3) {
            var itemsElf1 = input.get(groupStart);
            var itemsElf2 = input.get(groupStart + 1);
            var itemsElf3 = input.get(groupStart + 2);

            var commonItem = findCommonItemInAllThreeRucksacks(itemsElf1, itemsElf2, itemsElf3);
            var priority = convertItemToPriority(commonItem);

            prioritiesSum += priority;
        }

        return String.valueOf(prioritiesSum);
    }
}
