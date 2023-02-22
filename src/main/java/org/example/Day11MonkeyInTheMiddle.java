package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class Day11MonkeyInTheMiddle {

  private static List<Monkey> monkeys = new ArrayList<>();

  public static void main(String[] args) throws IOException {

    Path path = Path.of("./src/main/resources/11_input");

    try (BufferedReader reader = Files.newBufferedReader(path)) {
      long n = 1;
      String line =reader.readLine();
      List<String> monkeyBundle = new LinkedList<>();
      do {
        if(n%7!=0){
          monkeyBundle.add(line);
          n++;
        } else {
          monkeys.add(new Monkey(monkeyBundle));
          monkeyBundle=new LinkedList<>();
          n=1;
        }
      } while ((line = reader.readLine())!=null);
      monkeys.add(new Monkey(monkeyBundle));
    }

    IntStream.range(0,20).forEach(i->monkeys.forEach(Monkey::inspectAndThrow));
    Optional<Integer> answer = monkeys.stream().map(  Monkey::getThrowCounter).sorted((a, b) -> b - a)
        .limit(2).reduce((a, b) -> a * b);
    var list = monkeys.stream().map(  Monkey::getThrowCounter).sorted((a, b) -> b - a).toList();

    System.out.println("Part 1: " + answer.get());
  }

  private static class Monkey {

    private final LinkedList<Long> items;
    private final UnaryOperator<Long> operation;
    private final Predicate<Long> test;
    private final HashMap<Boolean, Integer> otherMonkeysIds = new HashMap<>();
    private int throwCounter = 0;

    public Monkey(final List<String> input) {
      this.items = extractItems(input.get(1));
      this.operation = extractOperation(input.get(2));
      this.test = extractTest(input.get(3));
      extractOtherMonkeysIds(input.get(4), input.get(5));
    }

    private LinkedList<Long> extractItems(final String line) {
      return Arrays.stream(line.split(": ")[1].split(", ")).map(Long::valueOf).collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
    }

    private UnaryOperator<Long> extractOperation(final String line) {
      String[] functionAsArray = line.split(" = ")[1].split(" ");
      return i -> {
        long a = functionAsArray[0].equals("old") ? i : Long.parseLong(functionAsArray[0]);
        long b = functionAsArray[2].equals("old") ? i : Long.parseLong(functionAsArray[2]);
        return switch (functionAsArray[1]) {
          case "*" -> a * b;
          case "/" -> a / b;
          case "+" -> a + b;
          case "-" -> a - b;
          default -> throw new RuntimeException();
        };
      };
    }

    private Predicate<Long> extractTest(final String line) {
      return x -> (x % Integer.parseInt(line.split(" by ")[1])) == 0;
    }

    private void extractOtherMonkeysIds(final String lineTrue, final String lineFalse) {
      otherMonkeysIds.put(true, Integer.valueOf(lineTrue.split(" to monkey ")[1]));
      otherMonkeysIds.put(false, Integer.valueOf(lineFalse.split(" to monkey ")[1]));
    }

    @Override
    public String toString() {
      return "###############\n" +
          "Monkey\n" +
          "Thrown times: " + this.throwCounter;
    }

    public int getThrowCounter() {
      return throwCounter;
    }

    public void inspectAndThrow() {
      while (!items.isEmpty()) {
        final var item = operation.apply(items.poll())/3;
        final var index = otherMonkeysIds.get(test.test(item));
        monkeys.get(index).items.add(item);
        throwCounter++;
      }
    }
  }
}

