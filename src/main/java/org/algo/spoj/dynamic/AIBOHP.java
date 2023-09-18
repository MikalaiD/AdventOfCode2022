package org.algo.spoj.dynamic;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AIBOHP {

  static Map<String, Integer> memo = new HashMap<>();

  public static void main(String[] args) throws java.lang.Exception {
    Scanner in = new Scanner(System.in);
    var cases = in.nextInt();
    while (in.hasNext() && cases != 0) {
      final var input = in.nextLine();
      if (input.isEmpty()) continue;
      int output = solve2(input);
      System.out.println(output);
      cases--;
    }
  }
  //stack overflow here with 6000 letters if used purely
  public static int solve(final String input, final int offsetLeft, final int offsetRight) {
    if ((offsetRight == offsetLeft)) {
      return 0;
    }
    var leftChar = input.charAt(offsetLeft);
    var rightChar = input.charAt(offsetRight);
    if (offsetRight - offsetLeft == 1 ) {
      return  leftChar == rightChar ? 0 : 1;
    }
    final var key = input.substring(offsetLeft, offsetRight + 1);
//    final var key = normalise(input.substring(offsetLeft, offsetRight + 1));
    if (memo.containsKey(key)) {
      return memo.get(key);
    }
    if (leftChar == rightChar) {
      return solve(input, offsetLeft + 1, offsetRight - 1);
    } else {
      memo.put(
          key,
          Math.min(
                  solve(input, offsetLeft + 1, offsetRight),
                  solve(input, offsetLeft, offsetRight - 1))
              + 1);
      return memo.get(key);
    }
  }

  public static int solve2(final String input){
    for(int i = 1; i<=input.length(); i++){
      var key = input.substring(0, i);
      var value = solve(key, 0, key.length()-1);
      memo.put(key, value);
    }
    return memo.get(input);
  }

  private static String normalise(final String input) {
    var normalizedLetter = 'a';
    var output = new StringBuilder();
    Map<Character, Character> normalisationMap = new HashMap<>();
    for (int i = 0; i < input.length(); i++) {
      char oldLetter = input.charAt(i);
      if(!normalisationMap.containsKey(oldLetter)){
        normalisationMap.put(oldLetter,normalizedLetter);
        normalizedLetter++;
      }
      output.append(normalisationMap.get(input.charAt(i)));
    }
    return output.toString();
  }
}
