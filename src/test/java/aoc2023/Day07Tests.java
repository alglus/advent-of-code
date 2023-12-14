package aoc2023;

import aoc2023.Day07.HandType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07Tests {

    private static Stream<Arguments> getAllTypesOfHands() {
        return Stream.of(
                Arguments.of("AAAAA", HandType.FIVEKIND),
                Arguments.of("AAAA8", HandType.FOURKIND),
                Arguments.of("8AAAA", HandType.FOURKIND),
                Arguments.of("AA8AA", HandType.FOURKIND),
                Arguments.of("AAA88", HandType.FULLHOUSE),
                Arguments.of("88AAA", HandType.FULLHOUSE),
                Arguments.of("A8A8A", HandType.FULLHOUSE),
                Arguments.of("AAA42", HandType.THREEKIND),
                Arguments.of("4AAA2", HandType.THREEKIND),
                Arguments.of("A4A2A", HandType.THREEKIND),
                Arguments.of("AA884", HandType.TWOPAIR),
                Arguments.of("A8A84", HandType.TWOPAIR),
                Arguments.of("4AA88", HandType.TWOPAIR),
                Arguments.of("A48A8", HandType.TWOPAIR),
                Arguments.of("AA123", HandType.ONEPAIR),
                Arguments.of("123AA", HandType.ONEPAIR),
                Arguments.of("12AA3", HandType.ONEPAIR),
                Arguments.of("12345", HandType.HIGHCARD),
                Arguments.of("AKQJT", HandType.HIGHCARD)
        );
    }

    @Test
    public void aoc_example_part1() {
        assertEquals("6440", new Day07().asTest().solvePart1());
    }

    @Test
    public void aoc_example_part2() {
        assertEquals("5905", new Day07().asTest().solvePart2());
    }

    @ParameterizedTest
    @MethodSource("getAllTypesOfHands")
    void handType_is_correctly_inferred_from_a_hand(String hand, HandType expectedHandType) {
        assertEquals(expectedHandType, HandType.of(hand, Day07.DeckType.NORMAL));
    }
}
