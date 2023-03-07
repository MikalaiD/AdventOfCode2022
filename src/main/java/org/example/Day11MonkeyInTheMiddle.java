package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
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
      String line = reader.readLine();
      List<String> monkeyBundle = new LinkedList<>();
      do {
        if (n % 7 != 0) {
          monkeyBundle.add(line);
          n++;
        } else {
          monkeys.add(new Monkey(monkeyBundle));
          monkeyBundle = new LinkedList<>();
          n = 1;
        }
      } while ((line = reader.readLine()) != null);
      monkeys.add(new Monkey(monkeyBundle));
    }

    final BigInteger stressDivisor = monkeys.stream().map(monkey -> monkey.testDivisor)
        .reduce(BigInteger.ONE, BigInteger::multiply);
    monkeys.forEach(monkey -> monkey.stressDivisor=stressDivisor);

    IntStream.range(0, 10000).peek(i -> System.out.println("###### Round " + i))
        .peek(i -> IntStream.range(0, monkeys.size())
            .forEach(j -> System.out.println("Monkey " + j + ": " + monkeys.get(j).items.stream().map(item -> item.currentValue).toList())))
        .forEach(i -> monkeys.forEach(Monkey::inspectAndThrow));

    IntStream.range(0, monkeys.size()).forEach(i -> System.out.println(
        "Monkey " + i + " inspected items " + monkeys.get(i).throwCounter + " times"));

    Optional<BigInteger> answer = monkeys.stream().map(Monkey::getThrowCounter).sorted((a, b) -> b - a)
        .map(BigInteger::valueOf)
        .limit(2).reduce(BigInteger::multiply);
    System.out.println("Part 2: " + answer.get());
  }

  private static class Monkey {

    private final LinkedList<Item> items;
    private final UnaryOperator<BigInteger> operation;
    private final Predicate<BigInteger> test;
    private BigInteger testDivisor;
    private BigInteger stressDivisor;
    private final HashMap<Boolean, Integer> otherMonkeysIds = new HashMap<>();
    private int throwCounter = 0;

    public Monkey(final List<String> input) {
      this.items = extractItems(input.get(1));
      this.operation = extractOperation(input.get(2));
      this.test = extractTest(input.get(3));
      extractOtherMonkeysIds(input.get(4), input.get(5));
    }

    private LinkedList<Item> extractItems(final String line) {
      return Arrays.stream(line.split(": ")[1].split(", ")).map(Item::new)
          .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);
    }

    private UnaryOperator<BigInteger> extractOperation(final String line) {
      String[] functionAsArray = line.split(" = ")[1].split(" ");
      return i -> {
        BigInteger a = functionAsArray[0].equals("old") ? i : new BigInteger(functionAsArray[0]);
        BigInteger b = functionAsArray[2].equals("old") ? i : new BigInteger(functionAsArray[2]);
        return switch (functionAsArray[1]) {
          case "*" ->  a.multiply(b);
          case "+" -> a.add(b);
          default -> throw new RuntimeException();
        };
      };
    }

    private Predicate<BigInteger> extractTest(final String line) {
      this.testDivisor = new BigInteger(line.split(" by ")[1]);
      return x -> (x.mod(testDivisor)).equals(BigInteger.ZERO);
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
        var item = items.poll();
        item.currentValue = operation.apply(item.currentValue).mod(stressDivisor);
        final var index = otherMonkeysIds.get(test.test(item.currentValue));
        monkeys.get(index).items.add(item);
        throwCounter++;
      }
    }

    private static class Item {

      private final BigInteger originalValue;
      private BigInteger currentValue;
      public Item(final String originalValue) {
        this.originalValue = new BigInteger(originalValue);
        this.currentValue = new BigInteger(originalValue);
      }
    }
  }
}


