package org.example;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.example.Day09RopeBridge.Rope.Knot;

public class Day09RopeBridge {


  public static void main(String[] args) throws IOException {

    var rope = new Rope(9);
    Files.readAllLines(Path.of("./src/main/resources/09_input")).stream()
        .forEach(rope::move);

    System.out.println("Part 2: " + rope.visitedByTail.size());

  }

  static class Rope {

    private static Knot head = new Knot(0, 0);
    private static LinkedList<Knot> knots = new LinkedList<>();
    private Set<Point> visitedByTail = new HashSet<>();

    public Rope(int length) {
      for (int i = 0; i < length; i++) {
        knots.add(new Knot(0, 0));
        if (i == length - 1) {
          knots.getLast().setTail(true);
        }
      }
      visitedByTail.add(new Point(0, 0));
    }

    public static Knot getHead() {
      return head;
    }

    public static List<Knot> getKnots() {
      return knots;
    }

    public static class Knot extends Point {


      public Knot(int x, int y) {
        super(x, y);
      }

      public Knot(Knot p) {
        super(p);
        this.tail = p.tail;
      }

      private boolean tail = false;

      public boolean isTail() {
        return tail;
      }

      public void setTail(boolean tail) {
        this.tail = tail;
      }
    }


    //########################################################
    private void saveTailPoint(Point currentPoint) {
      visitedByTail.add(new Point(currentPoint));
    }

    public void move(final String move) {
      var currentHead = head;
      head.move(getXHeadChange(move), getYHeadChange(move));
//      System.out.println("Head new position: " + positionHead);
      for (Knot knot : knots) {
        if (areNearby(currentHead, knot)) {
          break;
        }
        moveKnot(currentHead, knot);
        currentHead = knot;
      }
      print(move, 30);
    }

    private boolean areNearby(Knot head, Knot tail) {
      return Math.abs(head.x - tail.x) <= 1 && Math.abs(head.y - tail.y) <= 1;
    }

    private void moveKnot(final Knot head, final Knot knot) {
      var initial = new Knot(knot);
      var absDistX = Math.abs(head.x - knot.x);
      var absDistY = Math.abs(head.y - knot.y);
      var dirX = absDistX == 0 ? 0 : (head.x - knot.x) / absDistX;
      var dirY = absDistY == 0 ? 0 : (head.y - knot.y) / absDistY;
      if (absDistX < absDistY) {
        knot.translate(dirX * absDistX, (absDistY - 1) * dirY);
      } else if (absDistX > absDistY) {
        knot.translate((absDistX - 1) * dirX, dirY * absDistY);
      } else {
        knot.translate((absDistX - 1) * dirX, (absDistY - 1) * dirY);
      }

      if (knot.isTail()) {
        trackEachMoveBetween(knot, initial);
      }
    }

    private void trackEachMoveBetween(final Knot head, final Knot tail) {
      var absDistX = Math.abs(head.x - tail.x);
      var absDistY = Math.abs(head.y - tail.y);
      var dirX = absDistX == 0 ? 0 : (head.x - tail.x) / absDistX;
      var dirY = absDistY == 0 ? 0 : (head.y - tail.y) / absDistY;
      while (absDistX >= 1 || absDistY >= 1) {
        tail.translate(dirX, dirY);
        visitedByTail.add(new Point(tail));
        System.out.println("tail visited point: " + tail);
        absDistX = Math.abs(head.x - tail.x);
        absDistY = Math.abs(head.y - tail.y);
        dirX = absDistX == 0 ? 0 : (head.x - tail.x) / absDistX;
        dirY = absDistY == 0 ? 0 : (head.y - tail.y) / absDistY;
      }
    }


    private int getYHeadChange(String move) {
      var moveAsArr = move.split(" ");
      var dir = moveAsArr[0];
      var scale = moveAsArr[1];
      return head.y + switch (dir) {
        case "U" -> Integer.parseInt(scale);
        case "D" -> -Integer.parseInt(scale);
        default -> 0;
      };
    }

    private int getXHeadChange(String move) {
      var moveAsArr = move.split(" ");
      var dir = moveAsArr[0];
      var scale = moveAsArr[1];
      return head.x + switch (dir) {
        case "R" -> Integer.parseInt(scale);
        case "L" -> -Integer.parseInt(scale);
        default -> 0;
      };
    }
  }

  private static void print(String move, int fieldSize) {
    System.out.println("############## Move " + move + "#############");
    List<List<String>> matrix = IntStream.range(0, fieldSize)
        .mapToObj(
            row -> IntStream.range(0, fieldSize).mapToObj(i -> ".").collect(Collectors.toList()))
        .collect(
            Collectors.toList());
    var adj = fieldSize / 2;
    matrix.get(Rope.getHead().y + adj).set(Rope.getHead().x + adj, "H");
    for (int i = 0; i < Rope.getKnots().size(); i++) {
      Knot knot = Rope.getKnots().get(i);
      matrix.get(knot.y + adj).set(knot.x + adj, String.valueOf(i + 1));
    }
    matrix.get(adj).set(adj, "s");
    Collections.reverse(matrix);
    matrix.stream().map(list -> String.join("", list)).forEach(System.out::println);
  }
}

