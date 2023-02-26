package aoc2022;

import util.QuadFunction;
import util.Util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day05 {

    /* --- Part One --- */
    public static String supplyStacksPart1(List<String> input) {

        int breakLine = findBreakLineBetweenStacksAndMoveInstructions(input);

        List<Deque<Character>> stacks = importStacks(input, breakLine);

        performAllMoves(input, breakLine, stacks, moveWithCrateMover9000);

        return getTopCratesInAllStacks(stacks);
    }


    /* --- Part Two --- */
    public static String supplyStacksPart2(List<String> input) {

        int breakLine = findBreakLineBetweenStacksAndMoveInstructions(input);

        List<Deque<Character>> stacks = importStacks(input, breakLine);

        performAllMoves(input, breakLine, stacks, moveWithCrateMover9001);

        return getTopCratesInAllStacks(stacks);
    }


    private static int findBreakLineBetweenStacksAndMoveInstructions(List<String> input) {

        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).isBlank()) {
                return i;
            }
        }

        return -1;
    }

    private static List<Deque<Character>> importStacks(List<String> input, int breakLine) {

        int lineWithStackNumbers = breakLine - 1;
        int lineWithStackNumbersLength = input.get(lineWithStackNumbers).length();
        int numberOfStacks = (int) Math.ceil(lineWithStackNumbersLength / 4.0);

        List<Deque<Character>> stacks = initializeListOfDeques(numberOfStacks);

        for (int i = lineWithStackNumbers - 1; i >= 0; i--) {

            for (int j = 0; j < numberOfStacks; j++) {

                char crate = input.get(i).charAt(1 + j * 4);

                if (crate != ' ') {
                    stacks.get(j).push(crate);
                }
            }
        }

        return stacks;
    }

    private static List<Deque<Character>> initializeListOfDeques(int numberOfStacks) {

        List<Deque<Character>> stacks = new ArrayList<>(numberOfStacks);

        for (int i = 0; i < numberOfStacks; i++) {
            stacks.add(new ArrayDeque<>());
        }

        return stacks;
    }

    private static void performAllMoves(List<String> input, int breakLine, List<Deque<Character>> stacks,
                                        QuadFunction<Integer, Integer, Integer, List<Deque<Character>>, Void> moveWithCrateMover) {

        Pattern pattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

        for (int i = breakLine + 1; i < input.size(); i++) {

            Matcher matcher = pattern.matcher(input.get(i));

            if (matcher.matches()) {

                int cratesToMove = Integer.parseInt(matcher.group(1));
                int fromStack = Integer.parseInt(matcher.group(2)) - 1;
                int toStack = Integer.parseInt(matcher.group(3)) - 1;

                moveWithCrateMover.apply(cratesToMove, fromStack, toStack, stacks);
            }
        }
    }

    private static final QuadFunction<Integer, Integer, Integer, List<Deque<Character>>, Void>
            moveWithCrateMover9000 = (cratesToMove, fromStack, toStack, stacks) ->
    {
        for (int j = 0; j < cratesToMove; j++) {
            char crateToMove = stacks.get(fromStack).pop();
            stacks.get(toStack).push(crateToMove);
        }

        return null;
    };

    private static final QuadFunction<Integer, Integer, Integer, List<Deque<Character>>, Void>
            moveWithCrateMover9001 = (cratesToMove, fromStack, toStack, stacks) ->
    {
        Deque<Character> intermediateStack = new ArrayDeque<>();

        for (int j = 0; j < cratesToMove; j++) {
            char crateToMove = stacks.get(fromStack).pop();
            intermediateStack.push(crateToMove);
        }

        for (int j = 0; j < cratesToMove; j++) {
            char crateToMove = intermediateStack.pop();
            stacks.get(toStack).push(crateToMove);
        }

        return null;
    };

    private static String getTopCratesInAllStacks(List<Deque<Character>> stacks) {

        StringBuilder topCrates = new StringBuilder(stacks.size());

        for (Deque<Character> stack : stacks) {
            topCrates.append(stack.pop());
        }

        return topCrates.toString();
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input_day_05.txt");

        System.out.println("Part 1: " + supplyStacksPart1(input));
        System.out.println("Part 2: " + supplyStacksPart2(input));
    }
}
