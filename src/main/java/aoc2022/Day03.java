package aoc2022;

import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day03 {

    /* --- Part One --- */
    public static long rucksackReorganizationPart1(List<String> input) {

        long prioritiesSum = 0;

        for (String itemsList : input) {

            char commonItem = findCommonItemInBothCompartments(itemsList);
            int priority = convertItemToPriority(commonItem);
            prioritiesSum += priority;
        }

        return prioritiesSum;
    }


    /* --- Part Two --- */
    public static long rucksackReorganizationPart2(List<String> input) {

        long prioritiesSum = 0;

        for (int groupStart = 0; groupStart < input.size(); groupStart += 3) {
            String itemsElf1 = input.get(groupStart);
            String itemsElf2 = input.get(groupStart + 1);
            String itemsElf3 = input.get(groupStart + 2);

            char commonItem = findCommonItemInAllThreeRucksacks(itemsElf1, itemsElf2, itemsElf3);
            int priority = convertItemToPriority(commonItem);

            prioritiesSum += priority;
        }

        return prioritiesSum;
    }


    private static char findCommonItemInBothCompartments(String itemsList) {
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


    private static char findCommonItemInAllThreeRucksacks(String itemsElf1, String itemsElf2, String itemsElf3) {
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

    private static int convertItemToPriority(char commonItem) {
        if (commonItem == 0) return 0;

        if (Character.isLowerCase(commonItem)) {
            return commonItem - 'a' + 1;
        } else {
            return commonItem - 'A' + 27;
        }
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_03.txt");

        System.out.println("Part 1: " + rucksackReorganizationPart1(input));
        System.out.println("Part 2: " + rucksackReorganizationPart2(input));
    }
}
