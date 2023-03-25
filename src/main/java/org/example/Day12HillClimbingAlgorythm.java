package org.example;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Day12HillClimbingAlgorythm {

  private static AtomicInteger minLength = new AtomicInteger(Integer.MAX_VALUE);

  public static void main(String[] args) throws IOException, InterruptedException {

    Path path = Path.of("./src/main/resources/12_input");
    List<List<PointWithValue>> matrix = new ArrayList<>();

    List<String> lines = Files.readAllLines(path).stream().toList();

    for (int i = 0; i < lines.size(); i++) {
      matrix.add(new ArrayList<>());
      for (int j = 0; j < lines.get(i).length(); j++) {
        matrix.get(i).add(new PointWithValue(i, j, lines.get(i).charAt(j)));
      }
    }

    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < matrix.get(i).size(); j++) {
        final var point = matrix.get(i).get(j);
        if (i > 0 && (matrix.get(i - 1).get(j).val-point.val<=1 || point.val=='S')) {
          point.adjacentPoints.add(matrix.get(i - 1).get(j));
        }
        if (i < matrix.size() - 1 && (matrix.get(i + 1).get(j).val-point.val<=1|| point.val=='S')) {
          point.adjacentPoints.add(matrix.get(i + 1).get(j));
        }
        if (j > 0 && (matrix.get(i).get(j - 1).val-point.val<=1 || point.val=='S')) {
          point.adjacentPoints.add(matrix.get(i).get(j - 1));
        }
        if (j < matrix.get(i).size() - 1 && (matrix.get(i).get(j + 1).val-point.val<=1 || point.val=='S')) {
          point.adjacentPoints.add(matrix.get(i).get(j + 1));
        }
      }
    }

    Pathway pathway = new Pathway(matrix.get(0).get(0));
    ConcurrentLinkedQueue<Pathway> pathways = new ConcurrentLinkedQueue<>();

    pathway.move()
        .filter(Pathway::isSuccessful)
        .subscribe(pathways::add);

    Thread.sleep(10000);

    System.out.println("Time is over");
    System.out.println("Answer: " + pathways.stream().sorted().findFirst().orElseThrow().pathPoints.size());

  }

  private static class PointWithValue extends Point  {

    private final char val;
    private final List<PointWithValue> adjacentPoints;

    public PointWithValue(int y, int x, char val) {
      super(x, y);
      this.val = val;
      adjacentPoints = new ArrayList<>();
    }

    public List<PointWithValue> getAdjacentPoints() {
      return adjacentPoints;
    }
  }

  private static class Pathway implements Comparable<Pathway> {

    private final Set<PointWithValue> pathPoints;
    private final PointWithValue start;
    private boolean successful;

    public boolean isSuccessful() {
      return successful;
    }

    public Pathway(final PointWithValue start) {
      pathPoints = new CopyOnWriteArraySet<>();
      pathPoints.add(start);
      this.start = start;
    }

    private Pathway(final Set<PointWithValue> pathPoints, PointWithValue start) {
      this.pathPoints = new CopyOnWriteArraySet<>(pathPoints);
      this.pathPoints.add(start);
      this.start = start;
    }

    public Flux<Pathway> move() {
      return Flux.fromIterable(start.getAdjacentPoints()).publishOn(Schedulers.parallel())
          .filter(point -> !pathPoints.contains(point))
          .flatMap(point -> {
            if(point.val=='E' && start.adjacentPoints.stream().allMatch(p->p.val<start.val)){
              this.successful=true;

              return Mono.fromCallable(()->this).flux();
            }
            Pathway pathway = new Pathway(pathPoints, point);
            return pathway.move();
          })
          .doOnError(throwable -> System.out.println("Error!"));
    }

    @Override
    public String toString() {
      return "The path is " + pathPoints.stream().map(p -> "[x:" + p.x + "][y:" + p.y + "]")
          .collect(Collectors.joining(" "));
    }

    @Override
    public int compareTo(Pathway o) {
      return this.pathPoints.size()-o.pathPoints.size();
    }
  }


}


