package aoc2023;

import lombok.AllArgsConstructor;
import lombok.Getter;
import util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;
import static util.StringUtil.isDigitAt;

public class Day01 extends Puzzle2023 {

    public Day01() {
        super(1);
    }

    public static void main(String[] args) {
        new Day01().printSolutions();
    }

    private List<String> convertWordsToDigits(List<String> input) {
        List<String> inputWithConvertedWords = new ArrayList<>();

        for (String line : input) {
            line = convertFirstWordToDigit(line);
            line = convertLastWordToDigit(line);

            inputWithConvertedWords.add(line);
        }

        return inputWithConvertedWords;
    }

    private String convertFirstWordToDigit(String line) {
        for (int offset = 0; offset < line.length() - 2; offset++) {
            if (isDigitAt(line, offset)) {
                break;
            }

            for (Digits digit : Digits.values()) {
                if (line.startsWith(digit.getWord(), offset)) {
                    return StringUtil.replaceAt(line, digit.getValue(), offset, offset + digit.getWord().length());
                }
            }
        }
        return line;
    }

    private String convertLastWordToDigit(String line) {
        for (int offset = line.length() - 2; offset >= 0; offset--) {
            if (isDigitAt(line, offset)) {
                break;
            }

            for (Digits digit : Digits.values()) {
                if (line.startsWith(digit.getWord(), offset)) {
                    return StringUtil.replaceAt(line, digit.getValue(), offset, offset + digit.getWord().length());
                }
            }
        }
        return line;
    }

    private long sumNumbersMadeOfFristAndLastDigitsOfEveryLine(List<String> input) {
        return input.stream()
                .map(StringUtil::leaveOnlyDigits)
                .collect(teeing(
                        mapping(StringUtil::getFirstCharacter, toList()),
                        mapping(StringUtil::getLastCharacter, toList()),
                        (firstDigits, lastDigits) -> IntStream.range(0, firstDigits.size())
                                .mapToObj(i -> firstDigits.get(i) + lastDigits.get(i))
                                .mapToLong(Long::valueOf)
                                .sum()));
    }

    @Override
    public String solvePart1() {
        return String.valueOf(sumNumbersMadeOfFristAndLastDigitsOfEveryLine(getInputLines()));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(
                sumNumbersMadeOfFristAndLastDigitsOfEveryLine(
                        convertWordsToDigits(getInputLines())));
    }

    @Getter
    @AllArgsConstructor
    private enum Digits {
        ONE("one", "1"),
        TWO("two", "2"),
        THREE("three", "3"),
        FOUR("four", "4"),
        FIVE("five", "5"),
        SIX("six", "6"),
        SEVEN("seven", "7"),
        EIGHT("eight", "8"),
        NINE("nine", "9");

        private final String word;
        private final String value;
    }
}
