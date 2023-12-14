package aoc2023;

import util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Character.isDigit;

public class Day07 extends Puzzle2023 {

    private static final Comparator<Hand> handComparator = (hand1, hand2) -> {
        var comparisonByHandTypes = hand1.handType.compareTo(hand2.handType);

        if (comparisonByHandTypes != 0) {
            return comparisonByHandTypes;
        }

        for (int i = 0; i < hand1.hand.length(); i++) {
            int comparisonByCard;

            if (hand1.deckType == DeckType.NORMAL) {
                var cardHand1 = Card.of(hand1.hand.charAt(i));
                var cardHand2 = Card.of(hand2.hand.charAt(i));
                comparisonByCard = cardHand1.compareTo(cardHand2);
            } else {
                var cardHand1 = CardWithJoker.of(hand1.hand.charAt(i));
                var cardHand2 = CardWithJoker.of(hand2.hand.charAt(i));
                comparisonByCard = cardHand1.compareTo(cardHand2);
            }

            if (comparisonByCard != 0) {
                return comparisonByCard;
            }
        }

        return 0;
    };

    public Day07() {
        super(7);
    }

    public static void main(String[] args) {
        new Day07().printSolutions();
    }

    private long calculateTotalWinnings(List<String> input, DeckType deckType) {
        var sortedHands = input.stream()
                .map(StringUtil::splitByWhitespaces)
                .map(handAndBid -> Hand.of(handAndBid, deckType))
                .sorted(Collections.reverseOrder(handComparator))
                .toList();

        return IntStream.rangeClosed(1, sortedHands.size())
                .map(rank -> rank * sortedHands.get(rank - 1).bid)
                .sum();
    }

    @Override
    public String solvePart1() {
        return String.valueOf(calculateTotalWinnings(getInputLines(), DeckType.NORMAL));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(calculateTotalWinnings(getInputLines(), DeckType.WITH_JOKER));
    }

    private enum Card {
        A, K, Q, J, T, N9, N8, N7, N6, N5, N4, N3, N2;

        public static Card of(char card) {
            if (isDigit(card)) {
                return Card.valueOf("N" + card);
            }
            return Card.valueOf(String.valueOf(card));
        }
    }

    private enum CardWithJoker {
        A, K, Q, T, N9, N8, N7, N6, N5, N4, N3, N2, J;

        public static CardWithJoker of(char card) {
            if (isDigit(card)) {
                return CardWithJoker.valueOf("N" + card);
            }
            return CardWithJoker.valueOf(String.valueOf(card));
        }
    }

    enum HandType {
        FIVEKIND, FOURKIND, FULLHOUSE, THREEKIND, TWOPAIR, ONEPAIR, HIGHCARD;

        public static HandType of(String hand, DeckType deckType) {
            var cardTypeCount = sortHandByCardTypeCount(hand, deckType);
            return mapCardTypeCountToHand(cardTypeCount);
        }

        private static String sortHandByCardTypeCount(String hand, DeckType deckType) {
            var countByCardType = getCountByCardType(hand);

            if (deckType == DeckType.NORMAL || !hand.contains("J")) {
                return convertToNormalCardTypeCount(countByCardType);
            } else {
                return convertToCardTypeCountWithJoker(countByCardType);
            }
        }

        private static Map<Character, Integer> getCountByCardType(String hand) {
            Map<Character, Integer> countByCardType = new HashMap<>();
            for (char card : hand.toCharArray()) {
                countByCardType.merge(card, 1, Integer::sum);
            }
            return countByCardType;
        }

        private static String convertToNormalCardTypeCount(Map<Character, Integer> countByCardType) {
            return countByCardType.values().stream()
                    .sorted(Comparator.reverseOrder())
                    .map(String::valueOf)
                    .collect(Collectors.joining());
        }

        private static String convertToCardTypeCountWithJoker(Map<Character, Integer> countByCardType) {
            if (countByCardType.size() == 1) {
                // If the hand has Jokers and the countByCardType map only has one entry, then it means that all
                // cards are Jokers, so it will definitely be a Five of a Kind.
                return "5";
            }

            var sortedCountByCardType = countByCardType.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .toList();

            for (Map.Entry<Character, Integer> cardCountMap : sortedCountByCardType) {
                var card = cardCountMap.getKey();
                if (card == 'J') {
                    continue;
                }

                var cardCount = cardCountMap.getValue();
                cardCountMap.setValue(cardCount + countByCardType.get('J'));
                break;
            }

            return sortedCountByCardType.stream()
                    .filter(cardCountMap -> cardCountMap.getKey() != 'J')
                    .map(Map.Entry::getValue)
                    .map(String::valueOf)
                    .collect(Collectors.joining());
        }

        private static HandType mapCardTypeCountToHand(String cardTypeCount) {
            return switch (cardTypeCount) {
                case "5" -> FIVEKIND;
                case "41" -> FOURKIND;
                case "32" -> FULLHOUSE;
                case "311" -> THREEKIND;
                case "221" -> TWOPAIR;
                case "2111" -> ONEPAIR;
                default -> HIGHCARD;
            };
        }
    }

    enum DeckType {NORMAL, WITH_JOKER}

    private record Hand(String hand, HandType handType, int bid, DeckType deckType) {

        public static Hand of(String[] handAndBid, DeckType deckType) {
            var hand = handAndBid[0];
            var bid = Integer.parseInt(handAndBid[1]);
            return new Hand(hand, HandType.of(hand, deckType), bid, deckType);
        }
    }

}
