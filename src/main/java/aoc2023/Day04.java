package aoc2023;

import one.util.streamex.EntryStream;
import util.StringUtil;
import util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day04 extends Puzzle2023 {

    public Day04() {
        super(4);
    }

    public static void main(String[] args) {
        new Day04().printSolutions();
    }

    private int getTotalWinningPoints(List<String> input) {
        return getMatchingNumbers(input).stream()
                .mapToInt(this::calculatePoints)
                .sum();
    }

    private List<List<String>> getMatchingNumbers(List<String> input) {
        return parseCards(input).stream()
                .map(card -> card.own.stream()
                        .filter(card.winning::contains)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<Card> parseCards(List<String> input) {
        return input.stream()
                .map(this::removeCardNumber)
                .map(this::splitIntoWinningAndOwnNumbers)
                .map(this::convertToCard)
                .collect(Collectors.toList());
    }

    private String removeCardNumber(String line) {
        return line.substring(8);
    }

    private String[] splitIntoWinningAndOwnNumbers(String line) {
        return line.split("\\s+\\|\\s+");
    }

    private Card convertToCard(String[] numbers) {
        return new Card(
                convertNumbersIntoList(numbers[0]),
                convertNumbersIntoList(numbers[1]));
    }

    private List<String> convertNumbersIntoList(String numbers) {
        return Arrays.stream(StringUtil.splitByWhitespaces(numbers))
                .collect(Collectors.toList());
    }

    private int calculatePoints(List<String> matchingNumbers) {
        var numberOfMatches = matchingNumbers.size();
        if (numberOfMatches == 0) {
            return 0;
        } else {
            return (int) Math.pow(2, numberOfMatches - 1);
        }
    }

    private int getTotalNumberOfCards(List<String> input) {
        var cardCopies = Util.initializeAndPopulateList(input.size(), 1);

        EntryStream.of(getMatchingNumbers(input))
                .mapValues(List::size)
                .forKeyValue((i, numberOfMatches) ->
                        addWonCardCopies(i, numberOfMatches, cardCopies)
                );

        return cardCopies.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private void addWonCardCopies(int cardIndex, int numberOfMatches, List<Integer> cardCopies) {
        var copies = cardCopies.get(cardIndex);

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
