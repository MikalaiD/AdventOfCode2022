package org.algo.spoj.dynamic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AIBOHP {

  static Map<String, Integer> memo = new HashMap<>();
  static int minLength = -1;

  public static void main(String[] args) throws java.lang.Exception {
    Scanner in = new Scanner(System.in);
    var cases = in.nextInt();
    while (in.hasNext() && cases != 0) {
      final var input = in.nextLine();
      minLength = input.length() * 2 - 1;
      int output = solve(input, 0, input.length() - 1, 0);
      System.out.println(output);
      cases--;
    }
  }

  public static int solve(final String input,
      final int offsetLeft,
      final int offsetRight,
      final int count) {
    var leftChar = input.charAt(offsetLeft);
    var rightChar = input.charAt(offsetRight);
    if((offsetRight==offsetLeft) || (offsetRight-offsetLeft==1 && leftChar == rightChar)){
      return 0;
    }
    if (offsetRight-offsetLeft==1){
      return 1;
    }
    final var key = input.substring(offsetLeft, offsetRight + 1);
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    if (leftChar == rightChar) {
      memo.put(key, solve(input, offsetLeft + 1, offsetRight - 1, count));
      return memo.get(key);
    } else {
      memo.put(key, Math.min(solve(input, offsetLeft + 1, offsetRight, count + 1),
          solve(input, offsetLeft, offsetRight - 1, count + 1))+1);
      return memo.get(key);
    }
  }
}
