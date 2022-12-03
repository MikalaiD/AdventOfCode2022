package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day01CalorieCount {

  public static void main(String[] args) throws IOException {
    final List<String> lines = Files.readAllLines(Path.of("./src/main/resources/01_input")).stream()
        .toList();

    Integer elfCalories = 0;
    List<Integer> summary = new ArrayList<>();
    for (String line : lines) {
      if(!line.isEmpty()){
        elfCalories+=Integer.parseInt(line);
      } else {
        summary.add(elfCalories);
        elfCalories=0;
      }
    }

    System.out.println("Part 1:" + summary.stream().mapToInt(Integer::intValue).max().getAsInt());
    System.out.println("Part 2:" + summary.stream().sorted((x,y)->y-x).limit(3).mapToInt(Integer::intValue).sum());
  }

}
