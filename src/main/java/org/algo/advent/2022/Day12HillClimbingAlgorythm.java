package org.example;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day12HillClimbingAlgorythm {
  final static HashMap<Point, Integer> shortestPathTo = new HashMap<>();
  static Integer shortestPathToFinish = Integer.MAX_VALUE;

  public static void main(String[] args) throws IOException, InterruptedException {
    Path path = Path.of("./src/main/resources/12_input");
    List<String> lines = Files.readAllLines(path).stream().toList();
    List<List<PointWithValue>> matrix = createMatrix(lines);
    updatePointsWithValidAdjacentPointsRef(matrix);

    System.out.println("Start");
    long start = System.nanoTime();

    traverse(matrix);
    long finish = System.nanoTime();
    System.out.println("Time is over: " + (finish - start)/1_000_000);
    System.out.println("Answer: " + shortestPathToFinish);
  }

  private static void traverse(final List<List<PointWithValue>> matrix) {
    final var start = matrix.get(0).get(0);
    shortestPathTo.put(start, 0);
    HashSet<Point> path = new HashSet<>();
    path.add(start);
    moveFurther(start, shortestPathTo, path);
  }

  private static void moveFurther(PointWithValue point,
      HashMap<Point, Integer> shortestPathTo,
      HashSet<Point> pointsInPath) {
    if (point.finish) {
      shortestPathToFinish=Math.min(shortestPathToFinish, pointsInPath.size()-1);
      return;
    }
    for (PointWithValue adjacentPoint : point.getAdjacentPoints()) {
      if (!pointsInPath.contains(adjacentPoint) && !(shortestPathTo.containsKey(adjacentPoint)
          && shortestPathTo.get(adjacentPoint) < pointsInPath.size())) {
        shortestPathTo.put(adjacentPoint, pointsInPath.size());
        HashSet<Point> newPathPoints = new HashSet<>(pointsInPath);
        newPathPoints.add(adjacentPoint);
//        System.out.println("Moving to "+ adjacentPoint);
        moveFurther(adjacentPoint, shortestPathTo, newPathPoints);
      }
    }
  }

  private static void updatePointsWithValidAdjacentPointsRef(List<List<PointWithValue>> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < matrix.get(i).size(); j++) {
        final var point = matrix.get(i).get(j);
        if (i > 0 && (matrix.get(i - 1).get(j).val - point.val <= 1)) {
          point.adjacentPoints.add(matrix.get(i - 1).get(j));
        }
        if (i < matrix.size() - 1 && (matrix.get(i + 1).get(j).val - point.val <= 1
            || point.val == 'S')) {
          point.adjacentPoints.add(matrix.get(i + 1).get(j));
        }
        if (j > 0 && (matrix.get(i).get(j - 1).val - point.val <= 1)) {
          point.adjacentPoints.add(matrix.get(i).get(j - 1));
        }
        if (j < matrix.get(i).size() - 1 && (
            matrix.get(i).get(j + 1).val - point.val <= 1 || point.val == 'S')) {
          point.adjacentPoints.add(matrix.get(i).get(j + 1));
        }
        point.adjacentPoints = point.adjacentPoints.stream()
            .sorted((a, b) -> b.val - a.val)
            .collect(LinkedList::new, List::add, List::addAll);
      }
    }
  }

  private static List<List<PointWithValue>> createMatrix(List<String> lines) {
    List<List<PointWithValue>> matrix = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      matrix.add(new ArrayList<>());
      for (int j = 0; j < lines.get(i).length(); j++) {

        char val = lines.get(i).charAt(j);
        matrix.get(i).add(new PointWithValue(i, j, val));
      }
    }
    return matrix;
  }

  private static class PointWithValue extends Point {

    private final char val;
    private LinkedList<PointWithValue> adjacentPoints;
    private boolean finish;

    public PointWithValue(int y, int x, char val) {
      super(x, y);
      if (val == 'S') {
        this.val = 'a';
      } else if (val == 'E') {
        this.finish = true;
        this.val = 'z';
      } else {
        this.val = val;
      }
      adjacentPoints = new LinkedList<>();
    }

    public LinkedList<PointWithValue> getAdjacentPoints() {
      return adjacentPoints;
    }
  }
}


