package aoc2023;

import util.Range;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day05 extends Puzzle2023 {

    public Day05() {
        super(5);
    }

    public static void main(String[] args) {
        new Day05().printSolutions();
    }

    private long getLowestLocationNumber(Stream<Long> seeds, List<ConversionMap> maps) {
        return seeds
                .map(seed -> getLocation(seed, maps))
                .mapToLong(Long::longValue)
                .min()
                .orElseThrow();
    }

    private long getLowestLocationOfSeedRanges(Stream<Long> seedsInput, List<ConversionMap> maps) {
        var seedsList = seedsInput.toList();
        var seedRanges = IntStream.range(0, seedsList.size() / 2)
                .mapToObj(i -> new Range<>(
                        seedsList.get(i * 2),
                        seedsList.get(i * 2) + seedsList.get(i * 2 + 1)));

        return seedRanges
                .map(range -> {
                    var seeds = LongStream.range(range.fromInc(), range.toInc() + 1).boxed();
                    return getLowestLocationNumber(seeds, maps);
                })
                .mapToLong(Long::longValue)
                .min()
                .orElseThrow();
    }

    private Stream<Long> getSeeds(List<String> input) {
        return Arrays.stream(StringUtil.splitByWhitespaces(
                        input.get(0).substring(7)))
                .mapToLong(Long::parseLong).boxed();
    }

    private List<ConversionMap> getMaps(List<String> input) {
        return splitInputIntoMaps(input).stream()
                .map(map -> map.stream()
                        .dropWhile(line -> !StringUtil.isDigitAt(line, 0))
                        .map(StringUtil::splitByWhitespaces)
                        .map(ConversionRange::of)
                        .collect(Collectors.toList()))
                .map(ConversionMap::new)
                .toList();
    }

    private List<List<String>> splitInputIntoMaps(List<String> input) {
        List<List<String>> maps = new ArrayList<>();
        List<String> map = new ArrayList<>();

        for (int i = 2; i < input.size(); i++) {
            var line = input.get(i);

            if (line.isBlank()) {
                maps.add(map);
                map = new ArrayList<>();
            } else {
                map.add(line);

                if (i == input.size() - 1) {
                    maps.add(map);
                }
            }
        }

        return maps;
    }

    private long getLocation(Long seed, List<ConversionMap> maps) {
        var number = seed;

        for (ConversionMap map : maps) {
            number = convertNumber(number, map);
        }

        return number;
    }

    private long convertNumber(Long number, ConversionMap map) {
        for (ConversionRange range : map.conversionRanges) {
            var sourceRange = range.sourceRange;
            if (sourceRange.contains(number)) {
                return range.destinationStart + (number - sourceRange.fromInc());
            }
        }
        return number;
    }

    @Override
    public String solvePart1() {
        var input = getInputLines();
        return String.valueOf(getLowestLocationNumber(
                getSeeds(input),
                getMaps(input)
        ));
    }

    @Override
    public String solvePart2() {
        var input = getInputLines();
        return String.valueOf(getLowestLocationOfSeedRanges(
                getSeeds(input),
                getMaps(input)
        ));
    }

    private record ConversionMap(
            List<ConversionRange> conversionRanges

    ) {
    }

    private record ConversionRange(
            Range<Long> sourceRange,
            long destinationStart
    ) {
        public static ConversionRange of(String[] rangeInput) {
            if (rangeInput.length != 3) {
                throw new IllegalArgumentException("There should be 3 values in the array.");
            }

            var destinationStart = Long.parseLong(rangeInput[0]);
            var sourceRangeStart = Long.parseLong(rangeInput[1]);
            var sourceRangeEnd = sourceRangeStart + Long.parseLong(rangeInput[2]);

            return new ConversionRange(new Range<>(sourceRangeStart, sourceRangeEnd), destinationStart);
        }
    }
}
