package org.algo.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Milk {
  static long output = 0;
  static long it = 0;

  static long solve(int[][] matrix, long limit) {
    final var memo = convertToSmallRectangulars(matrix, limit);
    output += memo.size();
    var queue = new ConcurrentLinkedQueue<>(memo.keySet());
    while (!queue.isEmpty()) {
      //      System.out.println("iteration "+it++);
      Rectangular next = queue.remove();
      checkAndAddAtRight(memo, next, queue, limit);
      checkAndAddAtBottom(memo, next, queue, limit);
    }
    return output;
  }

  private static void checkAndAddAtBottom(
      final Map<Rectangular, Long> memo,
      final Rectangular rec,
      final ConcurrentLinkedQueue<Rectangular> queue,
      final long limit) {
    Rectangular deltaRectangular = rec.deltaDown();
    var value = memo.getOrDefault(deltaRectangular, null);
    if (value == null) {
      return;
    }
    Rectangular largerRectangular = rec.extendDown(value);
    if (memo.containsKey(largerRectangular) || largerRectangular.getValue() > limit) {
      return;
    }
    output++;
    memo.put(largerRectangular, largerRectangular.getValue());
    //    memo.remove(rec);
    queue.add(largerRectangular);
  }

  private static void checkAndAddAtRight(
      final Map<Rectangular, Long> memo,
      final Rectangular rec,
      final ConcurrentLinkedQueue<Rectangular> queue,
      final long limit) {
    Rectangular deltaRectangular = rec.deltaRight();
    var value = memo.getOrDefault(deltaRectangular, null);
    if (value == null) {
      return;
    }
    Rectangular largerRectangular = rec.extendRight(value);
    if (memo.containsKey(largerRectangular) || largerRectangular.getValue() > limit) {
      return;
    }
    output++;
    memo.put(largerRectangular, largerRectangular.getValue());
    //    memo.remove(rec);
    queue.add(largerRectangular);
  }

  private static Map<Rectangular, Long> convertToSmallRectangulars(
      final int[][] matrix, long limit) {
    Map<Rectangular, Long> memo = new HashMap<>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        long value = matrix[i][j];
        if (value <= limit) {
          Rectangular rectangular = new Rectangular(j, i, value);
          memo.put(rectangular, value);
        }
      }
    }
    return memo;
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

  Rectangular deltaDown() {
    return new Rectangular(x1, y2 + 1, x2, y2 + 1);
  }

  boolean isFlat() {
    return x1 == x2 || y1 == y2;
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
