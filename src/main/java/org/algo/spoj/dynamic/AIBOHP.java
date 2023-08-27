package org.algo.spoj.dynamic;

import java.util.Scanner;

public class AIBOHP {

  static int minLength = -1;
  public static void main(String[] args) throws java.lang.Exception {
    Scanner in = new Scanner(System.in);
    var cases = in.nextInt();
    while (in.hasNext()&&cases!=0) {
      StringBuilder input = new StringBuilder(in.nextLine());
      minLength=input.length()*2-1;
      solve(input,0);
      System.out.println((input.length()-minLength));
      cases--;
    }
  }

  public static void solve(final StringBuilder input, int offset){
    if(offset>=input.length()/2){
      minLength = Math.min(minLength, input.length());
    }
    if(input.length()>= minLength){
      return;
    }
    var leftChar = input.charAt(offset);
    var rightChar = input.charAt(input.length() - offset - 1);
    if(leftChar == rightChar){
      solve(input, offset+1);
    } else {
      var leftOption = new StringBuilder(input).insert(offset, rightChar);
      var rightOption = new StringBuilder(input).insert(input.length()-offset, leftChar);
      solve(leftOption, offset+1);
      solve(rightOption, offset+1);
    }
  }
}
