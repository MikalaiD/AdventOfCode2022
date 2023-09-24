package org.algo.spoj.dynamic;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class AIBOHP {

  static Map<EdgesPointer, Integer> memoPoint = new HashMap<>();

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
  public static int solvePoint(final String input){
    for(int i = 1; i<input.length(); i++){
      var keyPoint = new EdgesPointer(0, i);
      var value = solveWithPointersRecursively(input, keyPoint);
      memoPoint.put(keyPoint, value);
    }
    return memoPoint.get(new EdgesPointer(0,input.length()-1));
  }

  private static int solveWithPointersRecursively(final String input, final EdgesPointer pointer) {
    if (isAlreadyPolyndrome(input, pointer)) {
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
      int rightSubstringResult = solveWithPointersRecursively(input, pointer.subEdgeSkewedRight());
      int leftSubstringResult = solveWithPointersRecursively(input, pointer.subEdgeSkewedLeft());
      int value = Math.min(leftSubstringResult, rightSubstringResult) + 1;
      memoPoint.put(pointer, value);
      return value;
    }
  }

  private static boolean isAlreadyPolyndrome(String input, EdgesPointer pointer) {
    for(int i = 0; i<=(pointer.y-pointer.x)/2; i++){
      if (input.charAt(pointer.x+i)!=input.charAt(pointer.y-i)){
        return false;
      }
    }
    return true;
  }

  private static class EdgesPointer {
    private final int x;
    private final int y;

    public EdgesPointer(int x, int y) {
      this.x=x;
      this.y=y;
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

    @Override
    public String toString() {
      return "X: " +this.x + " Y: " + this.y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;

      EdgesPointer that = (EdgesPointer) o;

      if (x != that.x)
        return false;
      return y == that.y;
    }

    @Override
    public int hashCode() {
      int result = x;
      result = 31 * result + y;
      return result;
    }
  }
}
