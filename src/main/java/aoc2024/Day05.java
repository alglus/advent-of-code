package aoc2024;

import util.CollectionUtil;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day05 extends Puzzle2024 {

    public Day05() {
        super(5);
    }

    public static void main(final String[] args) {
        new Day05().printSolutions();
    }

    private static int getSumOfMiddlePagesOfCorrectlyOrderedUpdates(final List<List<String>> inputParts) {

        final SafetyProtocol safetyProtocol = extractSafetyProtocol(inputParts);
        final UpdatesData updatesData = extractUpdatesData(inputParts);

        return IntStream.range(0, updatesData.updates().size())
                .filter(updateIndex -> isCorrectlyOrdered(updateIndex, updatesData, safetyProtocol))
                .mapToObj(indexOfCorrectlyOrderedUpdate -> updatesData.updates().get(indexOfCorrectlyOrderedUpdate))
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();
    }

    private static SafetyProtocol extractSafetyProtocol(final List<List<String>> inputParts) {
        final List<OrderingRule> rules = new ArrayList<>();
        final Map<Integer, List<OrderingRule>> beforeValues = new HashMap<>();
        final Map<Integer, List<OrderingRule>> afterValues = new HashMap<>();

        inputParts.get(0).stream()
                .flatMap(rule -> Pattern.compile("(\\d+)\\|(\\d+)")
                        .matcher(rule)
                        .results()
                        .map(match -> new OrderingRule(
                                Integer.parseInt(match.group(1)),
                                Integer.parseInt(match.group(2)))))
                .forEachOrdered(rule -> {
                    rules.add(rule);
                    beforeValues.computeIfAbsent(rule.before(), k -> new ArrayList<>()).add(rule);
                    afterValues.computeIfAbsent(rule.after, k -> new ArrayList<>()).add(rule);
                });

        return new SafetyProtocol(rules, beforeValues, afterValues);
    }

    private static UpdatesData extractUpdatesData(final List<List<String>> inputParts) {
        final List<List<Integer>> updates = new ArrayList<>();
        final List<Map<Integer, Integer>> pageIndexes = new ArrayList<>();

        inputParts.get(1).stream()
                .map(updateString -> Arrays
                        .stream(updateString.split(","))
                        .map(Integer::parseInt)
                        .toList())
                .forEachOrdered(pagesNumbers -> {
                    final Map<Integer, Integer> pageIndex = new HashMap<>();

                    for (int i = 0; i < pagesNumbers.size(); i++) {
                        pageIndex.put(pagesNumbers.get(i), i);
                    }

                    pageIndexes.add(pageIndex);
                    updates.add(pagesNumbers);
                });

        return new UpdatesData(updates, pageIndexes);
    }

    private static boolean isCorrectlyOrdered(final int updateIndex,
                                              final UpdatesData updatesData,
                                              final SafetyProtocol safetyProtocol) {
        final var update = updatesData.updates().get(updateIndex);
        final var pageIndexes = updatesData.pageIndexes().get(updateIndex);

        for (int pageIndex = 0; pageIndex < update.size(); pageIndex++) {
            final var page = update.get(pageIndex);

            if (safetyProtocol.beforeValues.containsKey(page)) {
                for (final OrderingRule rule : safetyProtocol.beforeValues.get(page)) {
                    final var afterPageNumber = rule.after();
                    if (update.contains(afterPageNumber) && pageIndexes.get(afterPageNumber) < pageIndex) {
                        return false;
                    }
                }
            }

            if (safetyProtocol.afterValues.containsKey(page)) {
                for (final OrderingRule rule : safetyProtocol.afterValues.get(page)) {
                    final var beforePageNumber = rule.before();
                    if (update.contains(beforePageNumber) && pageIndexes.get(beforePageNumber) > pageIndex) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private int getSumOfMiddlePagesOfFixedUpdates(final List<List<String>> inputParts) {

        final SafetyProtocol safetyProtocol = extractSafetyProtocol(inputParts);
        final UpdatesData updatesData = extractUpdatesData(inputParts);

        return IntStream.range(0, updatesData.updates().size())
                .filter(updateIndex -> !isCorrectlyOrdered(updateIndex, updatesData, safetyProtocol))
                .mapToObj(indexOfIncorrectlyOrderedUpdate -> getFixedUpdate(indexOfIncorrectlyOrderedUpdate, updatesData, safetyProtocol))
                .mapToInt(update -> update.get(update.size() / 2))
                .sum();
    }

    private List<Integer> getFixedUpdate(final int indexOfIncorrectlyOrderedUpdate, final UpdatesData updatesData, final SafetyProtocol safetyProtocol) {
        List<Integer> update = new ArrayList<>(updatesData.updates().get(indexOfIncorrectlyOrderedUpdate));
        boolean isCorrectlyOrdered;

        do {
            isCorrectlyOrdered = true;
            for (int pageIndex = 0; pageIndex < update.size(); pageIndex++) {
                final var page = update.get(pageIndex);

                if (safetyProtocol.beforeValues.containsKey(page)) {
                    for (final OrderingRule rule : safetyProtocol.beforeValues.get(page)) {

                        final var afterPage = rule.after();
                        final var afterPageIndex = update.indexOf(afterPage);

                        if (update.contains(afterPage) && afterPageIndex < pageIndex) {
                            update = CollectionUtil.moveElementToIndex(update, afterPageIndex, pageIndex);
                            isCorrectlyOrdered = false;
                            break;
                        }
                    }
                }

                if (safetyProtocol.afterValues.containsKey(page)) {
                    for (final OrderingRule rule : safetyProtocol.afterValues.get(page)) {

                        final var beforePage = rule.before();
                        final var beforePageIndex = update.indexOf(beforePage);

                        if (update.contains(beforePage) && beforePageIndex > pageIndex) {
                            update = CollectionUtil.moveElementToIndex(update, beforePageIndex, pageIndex);
                            isCorrectlyOrdered = false;
                            break;
                        }
                    }
                }
                if (!isCorrectlyOrdered) {
                    break;
                }
            }

        } while (!isCorrectlyOrdered);

        return update;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getSumOfMiddlePagesOfCorrectlyOrderedUpdates(getInputLinesSplitByBlankLine()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getSumOfMiddlePagesOfFixedUpdates(getInputLinesSplitByBlankLine()));
    }

    private record OrderingRule(int before, int after) {
    }

    private record SafetyProtocol(
            List<OrderingRule> rules,
            Map<Integer, List<OrderingRule>> beforeValues,
            Map<Integer, List<OrderingRule>> afterValues
    ) {
    }

    private record UpdatesData(
            List<List<Integer>> updates,
            List<Map<Integer, Integer>> pageIndexes
    ) {
    }
}
