package org.algo.spoj.dynamic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ACODE {
  private static  Map<String, Long> memo = new HashMap<>();
  public static void main(String[] args) throws java.lang.Exception {
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()){
      List<String> list = Stream.of(scanner.nextLine().split("")).collect(Collectors.toList());
      if(list.size()==1 && list.get(0).equals("0")) {
        break;
      }
      long output = traverse(list, 0);
      System.out.println(output);
    }
  }

  private static long traverse(List<String> list, int offset){
    var str = list.stream().skip(offset).collect(Collectors.joining());
    if(str.isEmpty()) {
      return 0;
    }
    if(memo.containsKey(str)){
      return memo.get(str);
    }
    if(str.length()==1){
      return 1;
    }
    if(str.length()==2){
      var val = Long.parseLong(str) > 26 || Long.parseLong(str)==10 ? 1L : 2L;
      memo.put(str, val);
      return val;
    }
    if(Long.parseLong(str.substring(0,2))==10){
      long subVal = traverse(list, offset+2);
      memo.put(str, subVal);
      return subVal;
    } else if(Long.parseLong(str.substring(0,2))>26){
      long subVal = traverse(list, offset+1);
      memo.put(str, subVal);
      return subVal;
    } else {
      long subVal = traverse(list, offset+1)+traverse(list, offset+2);
      memo.put(str, subVal);
      return subVal;
    }
  }
}
