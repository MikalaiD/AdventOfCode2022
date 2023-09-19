package org.algo.spoj.dynamic;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class AIBOHP {

  static Map<Point, Integer> memoPoint = new HashMap<>();

  public static void main(String[] args) throws java.lang.Exception {
    Scanner in = new Scanner(System.in);
    var cases = in.nextInt();
    while (in.hasNext() && cases != 0) {
      final var input = in.nextLine();
      memoPoint=new HashMap<>();
      if (input.isEmpty()) continue;
      int output = solvePoint(input);
      System.out.println(output);
      cases--;
    }
  }
  public static int solveWithPointersRecursively(final String input, final EdgesPointer pointer) {
    if (pointer.isSame()) {
      return 0;
    }
    if (memoPoint.containsKey(pointer)) {
      return memoPoint.get(pointer);
    }
    var leftChar = input.charAt(pointer.x);
    var rightChar = input.charAt(pointer.y);
    if (pointer.isAdjacent()) {
      int value = leftChar == rightChar ? 0 : 1;
      memoPoint.put(pointer, value);
      return value;
    }
    if (leftChar == rightChar) {
      return solveWithPointersRecursively(input, pointer.subEdgeSymmetric());
    } else {
      int leftSubstringResult = solveWithPointersRecursively(input, pointer.subEdgeSkewedLeft());
      int rightSubstringResult = solveWithPointersRecursively(input, pointer.subEdgeSkewedRight());
      int value = Math.min(leftSubstringResult, rightSubstringResult) + 1;
      memoPoint.put(pointer, value);
      return value;
    }
  }

  public static int solvePoint(final String input){
    for(int i = 1; i<input.length(); i++){
      var keyPoint = new EdgesPointer(0, i);
      var value = solveWithPointersRecursively(input, keyPoint);
      memoPoint.put(keyPoint, value);
    }
    return memoPoint.get(new Point(0,input.length()-1));
  }

  private static class EdgesPointer extends Point {

    public EdgesPointer(int x, int y) {
      super(x, y);
    }

    protected EdgesPointer subEdgeSymmetric(){
      return new EdgesPointer(this.x+1, this.y-1);
    }
    protected EdgesPointer subEdgeSkewedLeft(){
      return new EdgesPointer(this.x, this.y-1);
    }
    protected EdgesPointer subEdgeSkewedRight(){
      return new EdgesPointer(this.x+1, this.y);
    }

    protected boolean isSame(){
      return this.x==this.y;
    }
    protected boolean isAdjacent(){
      return this.y-this.x==1;
    }
  }
}
