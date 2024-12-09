package aoc2022;

import lombok.Getter;
import lombok.Setter;
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

    public static void main(final String[] args) {
        new Day11().printSolutions();
    }

    private long playRoundsAndCalculateMonkeyBusiness(final List<String> input, final int numberOfRounds,
                                                      final Function<Long, Long> reliefFunction) {
        return playRoundsAndCalculateMonkeyBusiness(input, numberOfRounds, (u, v) -> reliefFunction.apply(u));
    }

    private long playRoundsAndCalculateMonkeyBusiness(final List<String> input, final int numberOfRounds,
                                                      final BiFunction<Long, Long, Long> reliefFunction) {
        final var monkeys = parseInputAndCreateMonkeys(input, reliefFunction);

        for (int i = 0; i < numberOfRounds; i++) {
            playRound(monkeys);
        }

        return calulateMonkeyBusinessLevel(monkeys);
    }

    private List<Monkey> parseInputAndCreateMonkeys(final List<String> input, final BiFunction<Long, Long, Long> reliefFunction) {
        int lineNumber = 0;
        final List<Monkey> monkeys = new ArrayList<>();

        while (lineNumber < input.size()) {

            if (input.get(lineNumber).startsWith("Monkey")) {
                final var monkey = new Monkey(monkeys, reliefFunction);

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

    private void setModuliProduct(final List<Monkey> monkeys) {
        final int moduliProduct = calculateModuliProduct(monkeys);

        monkeys.forEach(monkey -> monkey.setModuliProduct(moduliProduct));
    }

    private int calculateModuliProduct(final List<Monkey> monkeys) {
        return monkeys.stream()
                .map(Monkey::getDivisibleBy)
                .reduce((n1, n2) -> n1 * n2)
                .orElse(-1);
    }

    private List<Long> parseMonkeyItems(final List<String> input, final int lineNumber) {
        final var line = input.get(lineNumber);

        final var itemsStrings = line.substring(18).split(",");

        return Arrays.stream(itemsStrings)
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }

    private Function<Long, Long> parseMonkeyOperation(final List<String> input, final int lineNumber) {
        final var functionAndTerm = input.get(lineNumber).substring(23).split(" ");

        final var function = MathFunction.fromOperator(functionAndTerm[0].charAt(0));
        final var term = getTerm(functionAndTerm);

        return switch (function) {
            case ADD -> old -> old + term.orElse(old);
            case SUB -> old -> old - term.orElse(old);
            case MUL -> old -> old * term.orElse(old);
            case DIV -> old -> old / term.orElse(old);
        };
    }

    private Optional<Long> getTerm(final String[] functionAndTerm) {
        Optional<Long> term;

        // Use try{} to catch a NumberFormatException, which is thrown by valueOf() if it doesn't find a string,
        // which can be converted into a number. This will happen, when "old" is provided.
        try {
            term = Optional.of(Long.parseLong(functionAndTerm[1].trim()));
        } catch (final NumberFormatException e) {
            term = Optional.empty();
        }
        return term;
    }

    private int parseDivisibleBy(final List<String> input, final int lineNumber) {
        return Integer.parseInt(input.get(lineNumber).substring(21));
    }

    private Map<Boolean, Integer> parseMonkeysToThrowTo(final List<String> input, int lineNumber) {
        final Map<Boolean, Integer> monkeyToThrowToRule = new HashMap<>(2);

        final int monkeyIfTrue = Integer.parseInt(getLastCharacter(input.get(lineNumber)));
        final int monkeyIfFalse = Integer.parseInt(getLastCharacter(input.get(++lineNumber)));

        monkeyToThrowToRule.put(true, monkeyIfTrue);
        monkeyToThrowToRule.put(false, monkeyIfFalse);

        return monkeyToThrowToRule;
    }

    private void playRound(final List<Monkey> monkeys) {
        monkeys.forEach(Monkey::play);
    }

    private long calulateMonkeyBusinessLevel(final List<Monkey> monkeys) {
        return monkeys.stream()
                .sorted(Comparator.comparing(Monkey::getItemsInspected).reversed())
                .limit(2)
                .mapToLong(Monkey::getItemsInspected)
                .reduce(Math::multiplyExact)
                .orElseThrow();
    }

    @Override
    public String solvePart1() {
        final Function<Long, Long> reliefFunction = worry -> worry / 3;

        return String.valueOf(playRoundsAndCalculateMonkeyBusiness(getInputLines(), 20, reliefFunction));
    }

    @Override
    public String solvePart2() {
        // Use the chinese remainder theorem in order to cap the worry level and stop it from growing quadratically.
        final BiFunction<Long, Long, Long> reliefFunction = (worry, moduliProduct) -> worry % moduliProduct;

        return String.valueOf(playRoundsAndCalculateMonkeyBusiness(getInputLines(), 10000, reliefFunction));
    }

    private static class Monkey {
        private final List<Monkey> monkeys;
        private final Deque<Long> items = new ArrayDeque<>();
        private final BiFunction<Long, Long, Long> reliefFunction;

        @Setter
        private long moduliProduct;

        @Getter
        private int itemsInspected = 0;

        @Setter
        private Function<Long, Long> operation;

        @Getter
        @Setter
        private int divisibleBy = -1;

        @Setter
        private Map<Boolean, Integer> monkeyToThrowToRule;

        private Monkey(final List<Monkey> monkeys, final BiFunction<Long, Long, Long> reliefFunction) {
            this.monkeys = monkeys;
            this.reliefFunction = reliefFunction;
        }

        public void addItem(final long newItem) {
            items.addLast(newItem);
        }

        public void addItems(final List<Long> newItems) {
            newItems.forEach(items::addLast);
        }

        public void play() {
            final Iterator<Long> itemsIterator = items.iterator();

            while (itemsIterator.hasNext()) {
                final long item = itemsIterator.next();

                final long newWorryLevel = operation.apply(item);
                final long worryLevelAfterRelief = reliefFunction.apply(newWorryLevel, moduliProduct);

                final int monkeyToThrowTo = monkeyToThrowToRule.get(isDivisible(worryLevelAfterRelief, divisibleBy));

                monkeys.get(monkeyToThrowTo).addItem(worryLevelAfterRelief);

                itemsIterator.remove();
                itemsInspected++;
            }
        }

    }
}
