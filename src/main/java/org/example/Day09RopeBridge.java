package org.example;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09RopeBridge {


  public static void main(String[] args) throws IOException {

    var rope = new Rope(9);
    Files.readAllLines(Path.of("./src/main/resources/09_input")).stream()
        .forEach(rope::move);

    System.out.println("Part 2: " + rope.visitedByTail.size());

  }

  static class Rope {

    private LinkedList<Point> knots = new LinkedList<>();
    private Set<Point> visitedByTail = new HashSet<>();

    public Rope(int length) {
      for (int i = 0; i <= length; i++) {
        knots.add(new Point(0, 0));
      }
      visitedByTail.add(new Point(0, 0));
    }

    public void move(final String move){
      final var moveAsArr = move.split(" ");
      final var dir = moveAsArr[0];
      final var scale = Integer.parseInt(moveAsArr[1]);
      final var dX = convertDirToXChange(dir);
      final var dY = convertDirToYChange(dir);
      for (int i = 0; i < scale; i++) {
        moveKnotByKnot(dX, dY);
      }
    }

    private int convertDirToXChange(final String dir) {
      return switch (dir) {
        case "R" -> 1;
        case "L" -> -1;
        default -> 0;
      };
    }
    private int convertDirToYChange(final String dir) {
      return switch (dir) {
        case "U" -> 1;
        case "D" -> -1;
        default -> 0;
      };
    }

    private void moveKnotByKnot(final int dX, final int dY){
      Iterator<Point> iterator = knots.iterator();
      var head = iterator.next();
      Point tail=null;
      head.translate(dX, dY);
      while (iterator.hasNext() && (notNear(head, tail=iterator.next()))){
        var dXKnot = Math.abs(head.x - tail.x) == 0 ? 0 : (head.x - tail.x) / Math.abs(head.x - tail.x);
        var dYKnot = Math.abs(head.y - tail.y) == 0 ? 0 : (head.y - tail.y) / Math.abs(head.y - tail.y);
        tail.translate(dXKnot, dYKnot);
        head=tail;
        if(!iterator.hasNext()){
          visitedByTail.add(new Point(tail));
        }
      }
//      this.print(20);
    }

    private  boolean notNear(Point head, Point tail) {
      return Math.abs(head.x-tail.x)>1 || Math.abs(head.y-tail.y)>1;
    }

    public List<Point> getKnots() {
      return knots;
    }

    public void print(int fieldSize) {
      System.out.println("###########MOVE################");
      List<List<String>> matrix = IntStream.range(0, fieldSize)
          .mapToObj(
              row -> IntStream.range(0, fieldSize).mapToObj(i -> ".").collect(Collectors.toList())).collect(
              Collectors.toList());
      var adj = fieldSize / 2;
      matrix.get(this.getKnots().get(0).y + adj).set(this.getKnots().get(0).x + adj, "H");
      for (int i = 1; i < this.getKnots().size(); i++) {
        Point knot = this.getKnots().get(i);
        matrix.get(knot.y + adj).set(knot.x + adj, String.valueOf(i));
      }
      matrix.get(adj).set(adj, "s");
      Collections.reverse(matrix);
      matrix.stream().map(list -> String.join("", list)).forEach(System.out::println);
    }
  }


}

