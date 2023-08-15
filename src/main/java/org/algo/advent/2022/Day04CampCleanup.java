package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day04CampCleanup {


  public static void main(String[] args) throws IOException {
    long sum1 = Files.readAllLines(Path.of("./src/main/resources/04_input"))
        .stream()
        .filter(Day04CampCleanup::isTotalOverlap)
        .count();

    long sum2 = Files.readAllLines(Path.of("./src/main/resources/04_input"))
        .stream()
        .filter(Day04CampCleanup::isPartialOverlap)
        .count();


    System.out.println("Part 1: " + sum1);
    System.out.println("Part 2: " + sum2);

  }

  public static boolean isTotalOverlap(final String string) {
    List<Integer> integers = getIntegers(string);

    return isFirstWithinSecond(integers)
        || isSecondWithingFirst(integers);
  }

  private static boolean isSecondWithingFirst(List<Integer> integers) {
    return integers.get(2) >= integers.get(0) && integers.get(3) <= integers.get(1);
  }

  private static boolean isFirstWithinSecond(List<Integer> integers) {
    return integers.get(0) >= integers.get(2) && integers.get(1) <= integers.get(3);
  }
  public static boolean isPartialOverlap(final String string) {
    List<Integer> integers = getIntegers(string);

    return isFirstEndOverlapsSecondEnd(integers) ||
        isSecondEndOverlapsFirstStar(integers);
  }

  private static boolean isSecondEndOverlapsFirstStar(List<Integer> integers) {
    return integers.get(3) >= integers.get(0) && integers.get(0) >= integers.get(2);
  }

  private static boolean isFirstEndOverlapsSecondEnd(List<Integer> integers) {
    return integers.get(1) >= integers.get(2) && integers.get(0) <= integers.get(2);
  }

  private static List<Integer> getIntegers(String string) {
    return Arrays.stream(string.split(","))
        .flatMap(el -> Arrays.stream(el.split("-")))
        .map(Integer::valueOf)
        .toList();
  }
}
