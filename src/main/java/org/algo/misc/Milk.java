package org.algo.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Milk {
  static long solve(int[][] matrix, long limit) {
    var output = 0;
    var memo = new HashMap<Rectangular, Rectangular>();
    output += checkAndPopulateMemoWithColumns(matrix, limit, memo);
    output += checkRectangularsToTheRight(limit, memo);
    return output;
  }

  private static long checkRectangularsToTheRight(
      final long limit, final Map<Rectangular, Rectangular> memo) {
    var queue = new ConcurrentLinkedQueue<>(memo.keySet());
    var count = 0;
    while (!queue.isEmpty()) {
      var current = queue.remove();
      var delta = current.deltaRight();
      count +=
          Optional.ofNullable(memo.get(delta))
              .map(current::extendWith)
              .filter(rectangular -> rectangular.getValue() <= limit)
              .map(queue::add)
              .map(rectangular -> 1)
              .orElse(0);
    }
    return count;
  }

  private static long checkAndPopulateMemoWithColumns(
      final int[][] matrix, final long limit, final Map<Rectangular, Rectangular> memo) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        long value = matrix[i][j];
        if (value <= limit) {
          Rectangular rectangular = new Rectangular(j, i, value);
          memo.put(rectangular, rectangular);
        }
      }
    }
    Queue<Rectangular> queue = new ConcurrentLinkedQueue<>(memo.keySet());
    while (!queue.isEmpty()) {
      var current = queue.remove();
      var delta = current.deltaDown();
      Optional.ofNullable(memo.get(delta))
          .map(current::extendWith)
          .filter(rectangular -> rectangular.getValue() <= limit)
          .map(rectangular -> memo.put(rectangular, rectangular))
          .ifPresent(queue::add);
    }
    return memo.size();
  }
}

class Rectangular {

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Rectangular that = (Rectangular) o;

    if (x1 != that.x1) return false;
    if (y1 != that.y1) return false;
    if (x2 != that.x2) return false;
    return y2 == that.y2;
  }

  @Override
  public int hashCode() {
    int result = x1;
    result = 31 * result + y1;
    result = 31 * result + x2;
    result = 31 * result + y2;
    return result;
  }

  private final int x1, y1, x2, y2;
  private long value;

  Rectangular(final int x, final int y, final long value) {
    this.x1 = x;
    this.y1 = y;
    this.x2 = x;
    this.y2 = y;
    this.value = value;
  }

  Rectangular(final int x1, final int y1, final int x2, final int y2, final long value) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.value = value;
  }

  Rectangular(final int x1, final int y1, final int x2, final int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  long getValue() {
    return value;
  }

  Rectangular extendRight(final long value) {
    return new Rectangular(x1, y1, x2 + 1, y2, this.getValue() + value);
  }

  Rectangular deltaRight() {
    return new Rectangular(x2 + 1, y1, x2 + 1, y2);
  }

  Rectangular extendDown(final long value) {
    return new Rectangular(x1, y1, x2, y2 + 1, this.getValue() + value);
  }

  Rectangular extendWith(final Rectangular another) {
    return new Rectangular(x1, y1, another.x2, another.y2, this.getValue() + another.getValue());
  }

  Rectangular deltaDown() {
    return new Rectangular(x1, y2 + 1, x2, y2 + 1);
  }

  @Override
  public String toString() {
    return "Rectangular{"
        + "x1="
        + x1
        + ", y1="
        + y1
        + ", x2="
        + x2
        + ", y2="
        + y2
        + ", value="
        + value
        + '}';
  }
}
