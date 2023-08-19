package org.algo.spoj.dynamic;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ACODE {

  public static void main(String[] args) throws java.lang.Exception {
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      List<Integer> list = Stream.of(scanner.nextLine().split("")).map(Integer::parseInt)
          .collect(Collectors.toList());
      if (list.size() == 1 && list.get(0).equals("0")) {
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
      if (i == list.size() - 1 || getEncoded(list, i) > 26 || getEncoded(list, i) == 10) {
        output *= customFibonacci(i - j + 1);
        if (i != list.size() - 1 && getEncoded(list, i) == 10) {
          j = i + 2;
          i++;
        } else {
          j = i + 1;
        }
      }
      i++;
    }
    return output;
//    if (list.size()-offset==1) {
//      return 1;
//    } else if(list.size()==offset){
//      return 0;
//    }
//    var encoded = list.get(offset)*10+list.get(offset+1);
//    if (memo[offset]!=0) {
//      return memo[offset];
//    }
//    if (encoded == 10) {
//      long subVal = traverse(list, offset + 2);
//      memo[offset]=subVal;
//      return subVal;
//    } else if (encoded > 26) {
//      long subVal = traverse(list, offset + 1);
//      memo[offset]=subVal;
//      return subVal;
//    } else {
//      long subVal = traverse(list, offset + 1) + traverse(list, offset + 2);
//      memo[offset]=subVal;
//      return subVal;
//    }
  }

  private static int getEncoded(List<Integer> list, int i) {
    return list.get(i) * 10 + list.get(i + 1);
  }

  private static long customFibonacci(int size) {
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
