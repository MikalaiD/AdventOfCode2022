package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Day06TuningTrouble {

  public static final int THRESHOLD_GAP = 14;
  private static Integer gapCount = 0;
  private static Integer i = 0;
  private static Map<Character, Integer> indexes = new HashMap<>();

  public static void main(String[] args) throws IOException {

    String input = Files.readAllLines(Path.of("./src/main/resources/06_input")).stream().limit(1)
        .collect(Collectors.joining());

    for (char c : input.toCharArray()) {
      check(c);
      if (gapCount == THRESHOLD_GAP) {
        System.out.println("Answer: " + (i));
        return;
      }
    }

  }

  public static void check(char c) {
    if (!indexes.containsKey(c)) {
      gapCount++;
    } else {
      var actualGap = i - indexes.get(c);
      if (actualGap < THRESHOLD_GAP && actualGap <= gapCount) {
          gapCount = actualGap;
      } else {
        gapCount++;
      }
    }
    indexes.put(c, i++);
  }
}
