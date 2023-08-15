package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05SupplyStacks {


  private static Map<Integer, Deque<Integer>> chart = new HashMap<>();

  public static void main(String[] args) throws IOException {
    Files.readAllLines(Path.of("./src/main/resources/05_input"))
        .stream()
        .filter(line -> !line.startsWith("move") && !line.isBlank())
        .forEach(Day05SupplyStacks::addToChart);

    Files.readAllLines(Path.of("./src/main/resources/05_input"))
        .stream()
        .filter(line -> line.startsWith("move"))
        .map(Operation::new)
        .forEach(Operation::moveBetter);

    System.out.println(IntStream.range(0, chart.size()).mapToObj(chart::get).map(Deque::getFirst)
        .map(integer -> String.valueOf((char) integer.intValue())).collect(Collectors.joining()));


  }

  private static void addToChart(String line) {
    var array = line.replaceAll("\s{4}", "[-] ").chars().filter(c -> (c < 91 && c > 64) || c == 45)
        .toArray();
    for (int i = 0; i < array.length; i++) {
      if (chart.get(i) == null) {
        chart.put(i, new ArrayDeque<>());
      }
      if (array[i] != '-') {
        chart.get(i).add(array[i]);
      }
    }
  }


  private static class Operation {

    private int quantity;
    private int fromIndex;
    private int toIndex;

    public Operation(int quantity, int fromIndex, int toIndex) {
      this.quantity = quantity;
      this.fromIndex = fromIndex;
      this.toIndex = toIndex;
    }

    public Operation(final String line) {
      String[] split = line.replace("move ", "").replace("from ", "").replace("to ", "").split(" ");

      this.quantity = Integer.parseInt(split[0]);
      this.fromIndex = Integer.parseInt(split[1]) - 1;
      this.toIndex = Integer.parseInt(split[2]) - 1;
    }

    public void move() {
      for (int i = 0; i < this.quantity; i++) {
        var load = chart.get(this.fromIndex).poll();
        chart.get(this.toIndex).push(load);
      }
    }

    public void moveBetter() {
      var buffer = new ArrayDeque<Integer>();
      for (int i = 0; i <quantity; i++) {
        buffer.push(Objects.requireNonNull(chart.get(this.fromIndex).poll()));
      }
      for (int i = 0; i <quantity; i++) {
        chart.get(this.toIndex).push(buffer.poll());
      }
    }
  }

}
