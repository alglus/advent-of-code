package aoc2023;

import one.util.streamex.EntryStream;
import util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.CollectionUtil.initializeAndPopulateList;

public class Day04 extends Puzzle2023 {

    public Day04() {
        super(4);
    }

    public static void main(final String[] args) {
        new Day04().printSolutions();
    }

    private int getTotalWinningPoints(final List<String> input) {
        return getMatchingNumbers(input).stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }

    private List<List<String>> getMatchingNumbers(final List<String> input) {
        return parseCards(input).stream()
                .map(card -> card.own.stream()
                        .filter(card.winning::contains)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<Card> parseCards(final List<String> input) {
        return input.stream()
                .map(this::removeCardNumber)
                .map(this::splitIntoWinningAndOwnNumbers)
                .map(this::convertToCard)
                .collect(Collectors.toList());
    }

    private String removeCardNumber(final String line) {
        return line.substring(8);
    }

    private String[] splitIntoWinningAndOwnNumbers(final String line) {
        return line.split("\\s+\\|\\s+");
    }

    private Card convertToCard(final String[] numbers) {
        return new Card(
                convertNumbersIntoList(numbers[0]),
                convertNumbersIntoList(numbers[1]));
    }

    private List<String> convertNumbersIntoList(final String numbers) {
        return Arrays.stream(StringUtil.splitByWhitespaces(numbers))
                .collect(Collectors.toList());
    }

    private int calculatePoints(final List<String> matchingNumbers) {
        final var numberOfMatches = matchingNumbers.size();
        if (numberOfMatches == 0) {
            return 0;
        } else {
            return (int) Math.pow(2, numberOfMatches - 1);
        }
    }

    private int getTotalNumberOfCards(final List<String> input) {
        final var cardCopies = initializeAndPopulateList(input.size(), 1);

        EntryStream.of(getMatchingNumbers(input))
                .mapValues(List::size)
                .forKeyValue((i, numberOfMatches) ->
                        addWonCardCopies(i, numberOfMatches, cardCopies)
                );

        return cardCopies.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private void addWonCardCopies(final int cardIndex, final int numberOfMatches, final List<Integer> cardCopies) {
        final var copies = cardCopies.get(cardIndex);

        for (int i = cardIndex + 1; i <= cardIndex + numberOfMatches && i < cardCopies.size(); i++) {
            cardCopies.set(i, cardCopies.get(i) + copies);
        }
    }

    @Override
    public String solvePart1() {
        return String.valueOf(getTotalWinningPoints(getInputLines())
        );
    }

    @Override
    public String solvePart2() {
        return String.valueOf(getTotalNumberOfCards(getInputLines()));
    }

    private record Card(
            List<String> winning,
            List<String> own
    ) {
    }

}
