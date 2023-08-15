package org.example;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08TreetopTreeHouse {


  public static void main(String[] args) throws IOException {

    var lines = Files.readAllLines(Path.of("./src/main/resources/08_input")).stream()
        .map(line -> Arrays.stream(line.split("")).map(Integer::valueOf).toList())
        .toList();

//    int sum = Stream.concat(lines.stream(), transpose(lines).stream())
//        .mapToInt(Day08TreetopTreeHouse::assess).sum();

    int height = lines.size();
    int width = lines.get(0).size();
    List<List<Tree>> matrixNormal = IntStream.range(0, height)
        .mapToObj(y -> IntStream.range(0, width).mapToObj(x -> new Tree(x, y, lines.get(y).get(x)))
            .toList()).toList();

    Set<Tree> setNormal = getVisibleTrees(matrixNormal);
    Set<Tree> setTransposed = getVisibleTrees(Utils.transpose(matrixNormal));

    setNormal.addAll(setTransposed);

    System.out.println("Part 1: " + setNormal.size());

    findNeighbours(matrixNormal);

    OptionalInt max = matrixNormal.stream().flatMap(Collection::stream).parallel()
        .mapToInt(Tree::countVisibleTrees).max();

    System.out.println("Part 2: " + max.getAsInt());

  }

  private static Set<Tree> getVisibleTrees(List<List<Tree>> matrixNormal) {
    return matrixNormal.stream().flatMap(line -> assess(line).stream()).collect(
        Collectors.toSet());
  }

  private static void findNeighbours(List<List<Tree>> matrix) {
    for (int y = 0; y < matrix.size() - 1; y++) {
      for (int x = 0; x < matrix.get(0).size() - 1; x++) {
        var currentTree = matrix.get(y).get(x);
        var nextTreeDown = matrix.get(y + 1).get(x);
        var nextTreeRight = matrix.get(y).get(x + 1);
        currentTree.setDown(nextTreeDown);
        currentTree.setRight(nextTreeRight);
        nextTreeDown.setUp(currentTree);
        nextTreeRight.setLeft(currentTree);
      }
    }
  }


  private static Set<Tree> assess(List<Tree> line) {

    final Set<Tree> output = new HashSet<>();
    Tree maxLeftTree = line.get(0);
    output.add(maxLeftTree);
    final Tree maxRightTree = line.get(line.size() - 1);
    output.add(maxRightTree);
    LinkedList<Tree> rightQueue = new LinkedList<>();

    for (int i = 1; i < line.size() - 1; i++) {
      final Tree tree = line.get(i);
      if (tree.compareTo(maxLeftTree) > 0) {
        output.add(tree);
        maxLeftTree = tree;
        rightQueue = new LinkedList<>();
      } else if (tree.compareTo(maxRightTree) > 0) {
        while (rightQueue.size() > 0 && rightQueue.peek().compareTo(tree) <= 0) {
          rightQueue.poll();
        }
        rightQueue.push(tree);
      }

    }
    output.addAll(rightQueue);
    return output;
  }

  private static class Tree extends Point implements Comparable {

    private int height;
    private Tree up;
    private Tree down;
    private Tree left;
    private Tree right;

    public Tree(int a, int b, int height) {
      super(a, b);
      this.height = height;
    }

    public int getHeight() {
      return height;
    }


    @Override
    public int compareTo(Object o) {
      return this.getHeight() - ((Tree) o).getHeight();
    }

    @Override
    public String toString() {
      return "Tree{" +
          "height=" + height +
          '}';

    }

    public Tree getUp() {
      return up;
    }

    public void setUp(Tree up) {
      this.up = up;
    }

    public Tree getDown() {
      return down;
    }

    public void setDown(Tree down) {
      this.down = down;
    }

    public Tree getLeft() {
      return left;
    }

    public void setLeft(Tree left) {
      this.left = left;
    }

    public Tree getRight() {
      return right;
    }

    public void setRight(Tree right) {
      this.right = right;
    }

    public int countVisibleTrees() {
      return visibilityDown(this.height)*visibilityRight(this.height)*visibilityUp(this.height)*visibilityLeft(this.height);
    }

    public int visibilityUp(int thisHeight) {
      if (up == null) {
        return 0;
      } else if (up.height >= thisHeight) {
        return 1;
      } else {
        return 1 + up.visibilityUp(thisHeight);
      }
    }

    public int visibilityRight(int thisHeight) {
      if (right == null) {
        return 0;
      } else if (right.height >= thisHeight){
        return 1;
      } else {
        return 1+right.visibilityRight(thisHeight);
      }
    }

    public int visibilityDown(int thisHeight) {
      if (down == null) {
        return 0;
      } else if (down.height >= thisHeight){
        return 1;
      } else {
        return 1+down.visibilityDown(thisHeight);
      }
    }

    public int visibilityLeft(int thisHeight) {
      if (left == null) {
        return 0;
      } else if (left.height >=thisHeight){
        return 1;
      } else {
        return 1+left.visibilityLeft(thisHeight);
      }
    }
  }


}

