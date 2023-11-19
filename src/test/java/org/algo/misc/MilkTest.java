package org.algo.misc;

import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals(3, new Milk().solve(matrix, 7));
  }
  @Test
  void solve_2_2() {
    int[][] matrix = new int[][]{
        {1, 2},
        {3, 1}
    };
    assertEquals(9, new Milk().solve(matrix, 7));
  }
  @Test
  void solve_6_4() {
    int[][] matrix = new int[][]{
        {1, 2, 3, 1, 4, 5},
        {1, 2, 1, 2, 3, 7},
        {2, 3, 1, 5, 2, 2},
        {8, 1, 2, 3, 2, 3}
    };
    assertEquals(75, new Milk().solve(matrix, 7));
  }
}
