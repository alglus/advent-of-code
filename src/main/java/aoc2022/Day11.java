package aoc2022;

import lombok.Getter;
import util.MathFunction;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static util.MathUtil.isDivisible;
import static util.StringUtil.getLastCharacter;

public class Day11 extends Puzzle2022 {

    public Day11() {
        super(11);
    }

    public static void main(String[] args) {
        new Day11().printSolutions();
    }

    private long playRoundsAndCalculateMonkeyBusiness(List<String> input, int numberOfRounds,
                                                      Function<Long, Long> reliefFunction) {
        return playRoundsAndCalculateMonkeyBusiness(input, numberOfRounds, (u, v) -> reliefFunction.apply(u));
    }

    private long playRoundsAndCalculateMonkeyBusiness(List<String> input, int numberOfRounds,
                                                      BiFunction<Long, Long, Long> reliefFunction) {
        var monkeys = parseInputAndCreateMonkeys(input, reliefFunction);

        for (int i = 0; i < numberOfRounds; i++) {
            playRound(monkeys);
        }

        return calulateMonkeyBusinessLevel(monkeys);
    }

    private List<Monkey> parseInputAndCreateMonkeys(List<String> input, BiFunction<Long, Long, Long> reliefFunction) {
        int lineNumber = 0;
        List<Monkey> monkeys = new ArrayList<>();

        while (lineNumber < input.size()) {

            if (input.get(lineNumber).startsWith("Monkey")) {
                var monkey = new Monkey(monkeys, reliefFunction);

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

    private void setModuliProduct(List<Monkey> monkeys) {
        int moduliProduct = calculateModuliProduct(monkeys);

        monkeys.forEach(monkey -> monkey.setModuliProduct(moduliProduct));
    }

    private int calculateModuliProduct(List<Monkey> monkeys) {
        return monkeys.stream()
                .map(Monkey::getDivisibleBy)
                .reduce((n1, n2) -> n1 * n2)
                .orElse(-1);
    }

    private List<Long> parseMonkeyItems(List<String> input, int lineNumber) {
        var line = input.get(lineNumber);

        var itemsStrings = line.substring(18).split(",");

        return Arrays.stream(itemsStrings)
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }

    private Function<Long, Long> parseMonkeyOperation(List<String> input, int lineNumber) {
        var functionAndTerm = input.get(lineNumber).substring(23).split(" ");

        var function = MathFunction.fromOperator(functionAndTerm[0].charAt(0));
        final var term = getTerm(functionAndTerm);

        return switch (function) {
            case ADD -> old -> old + term.orElse(old);
            case SUB -> old -> old - term.orElse(old);
            case MUL -> old -> old * term.orElse(old);
            case DIV -> old -> old / term.orElse(old);
        };
    }

    private Optional<Long> getTerm(String[] functionAndTerm) {
        Optional<Long> term;

        // Use try{} to catch a NumberFormatException, which is thrown by valueOf() if it doesn't find a string,
        // which can be converted into a number. This will happen, when "old" is provided.
        try {
            term = Optional.of(Long.parseLong(functionAndTerm[1].trim()));
        } catch (NumberFormatException e) {
            term = Optional.empty();
        }
        return term;
    }

    private int parseDivisibleBy(List<String> input, int lineNumber) {
        return Integer.parseInt(input.get(lineNumber).substring(21));
    }

    private Map<Boolean, Integer> parseMonkeysToThrowTo(List<String> input, int lineNumber) {
        Map<Boolean, Integer> monkeyToThrowToRule = new HashMap<>(2);

        int monkeyIfTrue = Integer.parseInt(getLastCharacter(input.get(lineNumber)));
        int monkeyIfFalse = Integer.parseInt(getLastCharacter(input.get(++lineNumber)));

        monkeyToThrowToRule.put(true, monkeyIfTrue);
        monkeyToThrowToRule.put(false, monkeyIfFalse);

        return monkeyToThrowToRule;
    }

    private void playRound(List<Monkey> monkeys) {
        monkeys.forEach(Monkey::play);
    }

    private long calulateMonkeyBusinessLevel(List<Monkey> monkeys) {
        return monkeys.stream()
                .sorted(Comparator.comparing(Monkey::getItemsInspected).reversed())
                .limit(2)
                .mapToLong(Monkey::getItemsInspected)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    @Override
    public String solvePart1() {
        Function<Long, Long> reliefFunction = worry -> worry / 3;

        return String.valueOf(playRoundsAndCalculateMonkeyBusiness(getInputLines(), 20, reliefFunction));
    }

    @Override
    public String solvePart2() {
        // Use the chinese remainder theorem in order to cap the worry level and stop it from growing quadratically.
        BiFunction<Long, Long, Long> reliefFunction = (worry, moduliProduct) -> worry % moduliProduct;

        return String.valueOf(playRoundsAndCalculateMonkeyBusiness(getInputLines(), 10000, reliefFunction));
    }

    private static class Monkey {
        private final List<Monkey> monkeys;
        private final Deque<Long> items = new ArrayDeque<>();
        private final BiFunction<Long, Long, Long> reliefFunction;
        private long moduliProduct;
        @Getter
        private int itemsInspected = 0;
        private Function<Long, Long> operation;
        @Getter
        private int divisibleBy = -1;
        private Map<Boolean, Integer> monkeyToThrowToRule;

        private Monkey(List<Monkey> monkeys, BiFunction<Long, Long, Long> reliefFunction) {
            this.monkeys = monkeys;
            this.reliefFunction = reliefFunction;
        }

        public void setDivisibleBy(int divisibleBy) {
            this.divisibleBy = divisibleBy;
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

        public void setMonkeyToThrowToRule(Map<Boolean, Integer> monkeyToThrowToRule) {
            this.monkeyToThrowToRule = monkeyToThrowToRule;
        }

        public void play() {
            Iterator<Long> itemsIterator = items.iterator();

            while (itemsIterator.hasNext()) {
                long item = itemsIterator.next();

                long newWorryLevel = operation.apply(item);
                long worryLevelAfterRelief = reliefFunction.apply(newWorryLevel, moduliProduct);

                final int monkeyToThrowTo = monkeyToThrowToRule.get(isDivisible(worryLevelAfterRelief, divisibleBy));

                monkeys.get(monkeyToThrowTo).addItem(worryLevelAfterRelief);

                itemsIterator.remove();
                itemsInspected++;
            }
        }

    }
}
