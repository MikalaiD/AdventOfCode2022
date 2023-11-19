package org.algo.misc;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Milk {
  int solve(int[][] matrix, long limit) {
    final var memo = convertToSmallRectangulars(matrix);
    for (Entry<Rectangular, Rectangular> entry : memo.entrySet()) {
      synchronized (memo) {
        checkAndAddAtRight(memo, entry.getValue());
        checkAndAddAtBottom(memo, entry.getValue());
      }
    }
    return (int) memo.entrySet().stream().filter(rec -> rec.getValue().getValue() <= limit).count();
  }

  private void checkAndAddAtBottom(
      final Map<Rectangular, Rectangular> memo, final Rectangular rec) {
    Rectangular deltaRectangular = rec.deltaDown();
    var rectangularWithValue = memo.getOrDefault(deltaRectangular, null);
    if (rectangularWithValue == null) {
      return;
    }
    long value = rectangularWithValue.getValue();
    Rectangular largerRectangular = rec.extendDown(value);
    memo.put(largerRectangular, largerRectangular);
  }

  private void checkAndAddAtRight(final Map<Rectangular, Rectangular> memo, final Rectangular rec) {
    Rectangular deltaRectangular = rec.deltaRight();
    var rectangularWithValue = memo.getOrDefault(deltaRectangular, null);
    if (rectangularWithValue == null) {
      return;
    }
    long value = rectangularWithValue.getValue();
    Rectangular largerRectangular = rec.extendRight(value);
    memo.put(largerRectangular, largerRectangular);
  }

  private static Map<Rectangular, Rectangular> convertToSmallRectangulars(final int[][] matrix) {
    Map<Rectangular, Rectangular> memo = new ConcurrentHashMap<>();
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        int value = matrix[i][j];
        Rectangular rectangular = new Rectangular(j, i, value);
        memo.put(rectangular, rectangular);
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

  void setValue(final long value) {
    this.value = value;
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
