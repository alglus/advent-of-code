package aoc2023;

import one.util.streamex.EntryStream;
import util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.toInt;

public class Day02 extends Puzzle2023 {

    public Day02() {
        super(2);
    }

    public static void main(String[] args) {
        new Day02().printSolutions();
    }

    private EntryStream<Integer, List<CubesSet>> extractSetsFromInput(List<String> input) {
        return EntryStream.of(input)
                .mapValues(this::removeGameNumber)
                .mapValues(this::divideIntoSets)
                .mapValues(this::saveAsCubesSets);
    }

    private String removeGameNumber(String line) {
        return line.substring(8);
    }

    private String[] divideIntoSets(String game) {
        return game.split(";");
    }

    private List<CubesSet> saveAsCubesSets(String[] sets) {
        return Arrays.stream(sets)
                .map(set -> CubesSet.of(
                        set.replaceAll(".*?(\\d+) red.*", "$1"),
                        set.replaceAll(".*?(\\d+) green.*", "$1"),
                        set.replaceAll(".*?(\\d+) blue.*", "$1")
                ))
                .collect(Collectors.toList());
    }

    private int sumOfIndexesOfPossibleGames(EntryStream<Integer, List<CubesSet>> cubesSets, CubesSet cubesInBag) {
        return cubesSets
                .mapValues(sets -> areCubesSetsPossible(sets, cubesInBag))
                .mapValues(Util::areAllTrue)
                .filterValues(gamePossible -> gamePossible)
                .keys()
                .map(indexZeroBased -> indexZeroBased + 1)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private List<Boolean> areCubesSetsPossible(List<CubesSet> cubesSets, CubesSet cubesInBag) {
        return cubesSets.stream()
                .map(set -> set.red <= cubesInBag.red
                        && set.green <= cubesInBag.green
                        && set.blue <= cubesInBag.blue)
                .collect(Collectors.toList());
    }

    private int sumOfPowersOfMinimalCubesSets(EntryStream<Integer, List<CubesSet>> cubesSets) {
        return cubesSets
                .values()
                .map(this::findMaxCubesPerColor)
                .map(this::powerOfAllColors)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private CubesSet findMaxCubesPerColor(List<CubesSet> cubesSets) {
        return cubesSets.stream()
                .reduce(new CubesSet(0, 0, 0),
                        this::keepMaximumCubesPerColor);
    }

    private CubesSet keepMaximumCubesPerColor(CubesSet set1, CubesSet set2) {
        return new CubesSet(
                Math.max(set1.red, set2.red),
                Math.max(set1.green, set2.green),
                Math.max(set1.blue, set2.blue)
        );
    }

    private int powerOfAllColors(CubesSet set) {
        return set.red * set.green * set.blue;
    }

    @Override
    public String solvePart1() {
        return String.valueOf(
                sumOfIndexesOfPossibleGames(
                        extractSetsFromInput(getInputLines()),
                        new CubesSet(12, 13, 14)));
    }

    @Override
    public String solvePart2() {
        return String.valueOf(
                sumOfPowersOfMinimalCubesSets(
                        extractSetsFromInput(getInputLines())));
    }

    private record CubesSet(
            int red,
            int green,
            int blue
    ) {
        public static CubesSet of(String red, String green, String blue) {
            return new CubesSet(toInt(red, 0), toInt(green, 0), toInt(blue, 0));
        }
    }

}
