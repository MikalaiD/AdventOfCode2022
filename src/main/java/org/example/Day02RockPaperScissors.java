package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02RockPaperScissors {

  private static Map<String, String> winningCombinations = new HashMap<>();
  private static Map<String, String> translatorCheatingOpenly = new HashMap<>();
  private static Map<String, Integer> handsValues = new HashMap<>();

  static {
    winningCombinations.put("A", "B");
    winningCombinations.put("B", "C");
    winningCombinations.put("C", "A");

    translatorCheatingOpenly.put("X", "A");
    translatorCheatingOpenly.put("Y", "B");
    translatorCheatingOpenly.put("Z", "C");

    handsValues.put("A", 1);
    handsValues.put("B", 2);
    handsValues.put("C", 3);
  }

  public static void main(String[] args) throws IOException {
    final List<GameCheater> part1 = Files.readAllLines(Path.of("./src/main/resources/02_input"))
        .stream()
        .map(line -> line.split(" "))
        .map(line -> new GameCheater(line[1], line[0]))
        .toList();

    System.out.println("Part 1: " + part1.stream().mapToInt(GameCheater::getTotalScore).sum());

    final List<? extends GameCheater> part2 = Files.readAllLines(
            Path.of("./src/main/resources/02_input"))
        .stream()
        .map(line -> line.split(" "))
        .map(line -> new SmartCheater(line[1], line[0]))
        .toList();
    System.out.println("Part 2: " + part2.stream().mapToInt(GameCheater::getTotalScore).sum());
  }


  private static class GameCheater {

    public GameCheater(String yourStrategy, String opponentsHand) {
      this.yourStrategy = yourStrategy;
      this.opponentsHand = opponentsHand;
    }

    protected final String yourStrategy;
    protected final String opponentsHand;

    public int getTotalScore() {
      String yourHand = getYourHand();
      return getScore(yourHand) + getHandValue(yourHand);
    }

    protected String getYourHand() {
      return translatorCheatingOpenly.get(yourStrategy);
    }

    private int getScore(String yourHand) {

      if (opponentsHand.equals(yourHand)) {
        return 3;
      } else if (winningCombinations.get(opponentsHand).equals(yourHand)) {
        return 6;
      }
      return 0;
    }

    private int getHandValue(String yourHand) {
      return handsValues.get(yourHand);
    }
  }

  private static class SmartCheater extends GameCheater {

    public SmartCheater(String yourHand, String opponentsHand) {
      super(yourHand, opponentsHand);
    }

    @Override
    protected String getYourHand() {
      if (super.yourStrategy.equals("X")) {
        return winningCombinations.get(winningCombinations.get(opponentsHand));
      } else if (super.yourStrategy.equals("Y")) {
        return opponentsHand;
      } else if (super.yourStrategy.equals("Z")) {
        return winningCombinations.get(opponentsHand);
      }
      return null;
    }
  }
}
