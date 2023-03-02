package aoc2022;

import util.MathFunction;
import util.Util;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static util.Util.divisible;
import static util.Util.getLastCharacter;

public class Day11 {

    /* --- Part One --- */
    public static long monkeyInTheMiddlePart1(List<String> input) {

        Function<Long, Long> reliefFunction = worry -> worry / 3;

        return playRoundsAndCalculateMonkeyBusiness(input, 20, reliefFunction);
    }


    /* --- Part Two --- */
    public static long monkeyInTheMiddlePart2(List<String> input) {

        // Use the chinese remainder theorem in order to cap the worry level and stop it from growing quadratically.
        BiFunction<Long, Long, Long> reliefFunction = (worry, moduliProduct) -> worry % moduliProduct;

        return playRoundsAndCalculateMonkeyBusiness(input, 10000, reliefFunction);
    }


    private static long playRoundsAndCalculateMonkeyBusiness(List<String> input, int numberOfRounds,
                                                             Function<Long, Long> reliefFunction) {
        return playRoundsAndCalculateMonkeyBusiness(input, numberOfRounds, (u, v) -> reliefFunction.apply(u));
    }

    private static long playRoundsAndCalculateMonkeyBusiness(List<String> input, int numberOfRounds,
                                                             BiFunction<Long, Long, Long> reliefFunction) {
        List<Monkey> monkeys = parseInputAndCreateMonkeys(input, reliefFunction);

        for (int i = 0; i < numberOfRounds; i++) {
            playRound(monkeys);
        }

        return calulateMonkeyBusinessLevel(monkeys);
    }


    private static class Monkey {
        private final List<Monkey> monkeys;
        private long moduliProduct;
        private final Deque<Long> items = new ArrayDeque<>();
        private int itemsInspected = 0;
        private Function<Long, Long> operation;
        private int divisibleBy = -1;
        private Map<Boolean, Integer> monkeyToThrowToRule;
        private final BiFunction<Long, Long, Long> reliefFunction;

        private Monkey(List<Monkey> monkeys, BiFunction<Long, Long, Long> reliefFunction) {
            this.monkeys = monkeys;
            this.reliefFunction = reliefFunction;
        }

        public int getDivisibleBy() {
            return divisibleBy;
        }

        public int getItemsInspected() {
            return itemsInspected;
        }

        public void addItem(long newItem) {
            items.addLast(newItem);
        }

        public void addItems(List<Long> newItems) {
            newItems.forEach(items::addLast);
        }

        public void setModuliProduct(int moduliProduct) {
            this.moduliProduct = moduliProduct;
        }

        public void setOperation(Function<Long, Long> operation) {
            this.operation = operation;
        }

        public void setDivisibleBy(int divisibleBy) {
            this.divisibleBy = divisibleBy;
        }

        public void setMonkeyToThrowToRule(Map<Boolean, Integer> monkeyToThrowToRule) {
            this.monkeyToThrowToRule = monkeyToThrowToRule;
        }

        public void play() {
            Iterator<Long> itemsIterator = items.iterator();

            while (itemsIterator.hasNext()) {
                long item = itemsIterator.next();

                long newWorryLevel = operation.apply(item);
                long worryLevelAfterRelief = reliefFunction.apply(newWorryLevel, moduliProduct);

                int monkeyToThrowTo = monkeyToThrowToRule.get(divisible(worryLevelAfterRelief, divisibleBy));

                monkeys.get(monkeyToThrowTo).addItem(worryLevelAfterRelief);

                itemsIterator.remove();
                itemsInspected++;
            }
        }

    }

    private static List<Monkey> parseInputAndCreateMonkeys(List<String> input, BiFunction<Long, Long, Long> reliefFunction) {

        int lineNumber = 0;
        List<Monkey> monkeys = new ArrayList<>();

        while (lineNumber < input.size()) {

            if (input.get(lineNumber).startsWith("Monkey")) {

                Monkey monkey = new Monkey(monkeys, reliefFunction);

                monkey.addItems(parseMonkeyItems(input, ++lineNumber));
                monkey.setOperation(parseMonkeyOperation(input, ++lineNumber));
                monkey.setDivisibleBy(parseDivisibleBy(input, ++lineNumber));
                monkey.setMonkeyToThrowToRule(parseMonkeysToThrowTo(input, ++lineNumber));

                monkeys.add(monkey);
            }

            lineNumber++;
        }

        setModuliProduct(monkeys);
        return monkeys;
    }

    private static void setModuliProduct(List<Monkey> monkeys) {
        int moduliProduct = calculateModuliProduct(monkeys);

        monkeys.forEach(monkey -> monkey.setModuliProduct(moduliProduct));
    }

    private static int calculateModuliProduct(List<Monkey> monkeys) {
        return monkeys.stream()
                .map(Monkey::getDivisibleBy)
                .reduce((n1, n2) -> n1 * n2)
                .orElse(-1);
    }

    private static List<Long> parseMonkeyItems(List<String> input, int lineNumber) {
        String line = input.get(lineNumber);

        String[] itemsStrings = line.substring(18).split(",");

        return Arrays.stream(itemsStrings)
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }

    private static Function<Long, Long> parseMonkeyOperation(List<String> input, int lineNumber) {
        String[] functionAndTerm = input.get(lineNumber).substring(23).split(" ");

        MathFunction function = MathFunction.fromOperator(functionAndTerm[0].charAt(0));

        final Optional<Long> term = getTerm(functionAndTerm);

        return switch (function) {
            case ADD -> old -> old + term.orElse(old);
            case SUB -> old -> old - term.orElse(old);
            case MUL -> old -> old * term.orElse(old);
            case DIV -> old -> old / term.orElse(old);
        };
    }

    private static Optional<Long> getTerm(String[] functionAndTerm) {
        Optional<Long> term;

        // Use try{} to catch a NumberFormatException, which is thrown by valueOf(), if it doesn't find a string,
        // which can be converted into a number. This will happen, when "old" is provided.
        try {
            term = Optional.of(Long.parseLong(functionAndTerm[1].trim()));
        } catch (NumberFormatException e) {
            term = Optional.empty();
        }
        return term;
    }

    private static int parseDivisibleBy(List<String> input, int lineNumber) {
        return Integer.parseInt(input.get(lineNumber).substring(21));
    }

    private static Map<Boolean, Integer> parseMonkeysToThrowTo(List<String> input, int lineNumber) {
        Map<Boolean, Integer> monkeyToThrowToRule = new HashMap<>(2);

        int monkeyIfTrue = Integer.parseInt(getLastCharacter(input.get(lineNumber)));
        int monkeyIfFalse = Integer.parseInt(getLastCharacter(input.get(++lineNumber)));

        monkeyToThrowToRule.put(true, monkeyIfTrue);
        monkeyToThrowToRule.put(false, monkeyIfFalse);

        return monkeyToThrowToRule;
    }

    private static void playRound(List<Monkey> monkeys) {
        monkeys.forEach(Monkey::play);
    }

    private static long calulateMonkeyBusinessLevel(List<Monkey> monkeys) {
        return monkeys.stream()
                .sorted(Comparator.comparing(Monkey::getItemsInspected).reversed())
                .limit(2)
                .mapToLong(Monkey::getItemsInspected)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }


    public static void main(String[] args) {
        List<String> input = Util.getLinesFromPuzzleFile("aoc2022/input/day_11.txt");

        System.out.println("Part 1: " + Day11.monkeyInTheMiddlePart1(input));
        System.out.println("Part 2: " + Day11.monkeyInTheMiddlePart2(input));
    }
}
