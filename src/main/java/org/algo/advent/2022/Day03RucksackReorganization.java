package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day03RucksackReorganization {



  public static void main(String[] args) throws IOException {
    int sum1 = Files.readAllLines(Path.of("./src/main/resources/03_input"))
        .stream()
        .map(line -> identifySameElement(line).get(0))
        .mapToInt(Day03RucksackReorganization::getValue)
        .sum();

    List<String> allLines = Files.readAllLines(Path.of("./src/main/resources/03_input"))
        .stream().toList();

    int sum2 = IntStream.range(0, allLines.size())
        .filter(i->i%3==0)
        .mapToObj(i->allLines.subList(i,i+3))
        .map(Day03RucksackReorganization::identifySameElement)
        .mapToInt(Day03RucksackReorganization::getValue)
        .sum();

    System.out.println("Part 1: " + sum1);
    System.out.println("Part 2: " + sum2);

  }

  private static int getValue(Integer element) {
      return element > 96 ? element-96 : element - 64 + 26;
  }

  private static List<Integer> identifySameElement(final String line) {
    final Set<Integer> rightSet = line.substring(line.length()/2).chars().collect(HashSet::new, HashSet::add, HashSet::addAll);
    final Set<Integer> leftSet = line.substring(0, line.length()/2).chars().collect(HashSet::new, HashSet::add, HashSet::addAll);

    return leftSet.stream().filter(rightSet::contains).toList();
  }

  private static Integer identifySameElement(final List<String> list) {

    return list.get(0).chars().filter(c->list.get(1).chars().anyMatch(c2->c2==c) && list.get(2).chars().anyMatch(c2->c2==c)).findFirst().getAsInt();
  }

}
