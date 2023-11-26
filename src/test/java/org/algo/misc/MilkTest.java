package org.algo.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class MilkTest {

//  @Test
  void solve_1() {
    int[][] matrix = new int[][]{
        {1},
    };
    assertEquals(1, new Milk().solve(matrix, 7));
  }

  @Test
  void solve_1_2() {
    int[][] matrix = new int[][]{
        {1, 2},
    };
    assertEquals(3, Milk.solve(matrix, 7));
  }
  @Test
  void solve_2_2() {
    int[][] matrix = new int[][]{
        {1, 2},
        {3, 1}
    };
    assertEquals(9, Milk.solve(matrix, 7));
  }
  @Test
  void solve_6_4() {
    Instant start = Instant.now();
    int[][] matrix = new int[][]{
        {1, 2, 3, 1, 4, 5},
        {1, 2, 1, 2, 3, 7},
        {2, 3, 1, 5, 2, 2},
        {8, 1, 2, 3, 2, 3}
    };
    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMinutes();
    assertEquals(75, Milk.solve(matrix, 7));
    System.out.println("Time elapsed: " + timeElapsed + " minutes");
    assertTrue(timeElapsed < 2);
  }
  @Test
  void solve_largest_possible() {
    Instant start = Instant.now();
    int size = 500;
    int limit = Integer.MAX_VALUE;
    int[][] matrix = IntStream.range(0, size)
        .mapToObj(i -> IntStream.range(0, size).map(j -> (int) (Math.random() * 100)).toArray())
        .toArray(int[][]::new);
    Instant finish = Instant.now();
    long timeElapsed = Duration.between(start, finish).toMinutes();
    Milk.solve(matrix, limit);
    System.out.println("Time elapsed: " + timeElapsed + " minutes");
    assertTrue(timeElapsed < 2);
  }
}
