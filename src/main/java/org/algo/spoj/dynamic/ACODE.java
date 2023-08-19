package org.algo.spoj.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ACODE {

  final static HashMap<Integer, Long> memo = new HashMap<>();

  public static void main(String[] args) throws java.lang.Exception {
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      List<Integer> list = Stream.of(scanner.nextLine().split("")).map(Integer::parseInt)
          .collect(Collectors.toList());
      if (list.get(0).equals("0")) {
        break;
      }
      long output = traverse(list);
      System.out.println(output);
    }
  }

  public static long traverse(List<Integer> list) {
    long output = 1L;
    int j = 0;
    for (int i = 0; i < list.size();) {
      if (i == list.size() - 1 || getEncoded(list, i) > 26 || getEncoded(list, i) == 10 || getEncoded(list, i) == 20) {
        if (i != list.size() - 1 && (getEncoded(list, i) == 10 || getEncoded(list, i)==20)) {
          output *= getOrCalculatePermutations(i - j);
          j = i + 2;
          i++;
        } else {
          output *= getOrCalculatePermutations(i - j + 1);
          j = i + 1;
        }
      }
      i++;
    }
    return output;
  }

  private static int getEncoded(List<Integer> list, int i) {
    return list.get(i) * 10 + list.get(i + 1);
  }

  private static long getOrCalculatePermutations(int size) {
    memo.putIfAbsent(size, calculateCustomFibonacciFor(size));
    return memo.get(size);
  }

  private static long calculateCustomFibonacciFor(int size) {
    var output = 1L;
    var f1 = 1L;
    var f2 = 1L;
    for (int i = 0; i < size - 1; i++) {
      long fNext = f1 + f2;
      output = fNext;
      f1 = f2;
      f2 = fNext;
    }
    return output;
  }
}
