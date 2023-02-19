package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10CathodeRayTube {


  public static void main(String[] args) throws IOException {


    Files.readAllLines(Path.of("./src/main/resources/10_input")).stream()
        .map(Bundle::new).forEach(Processor::process);

    Monitor.display();
  }

  private class Monitor{
    private static List<List<String>> monitor = IntStream.range(0, 6).mapToObj(i->IntStream.range(0,40).mapToObj(j->".").collect(
        Collectors.toList())).collect(Collectors.toList());

    private static void sync(final int sprite, final int tick){
      if(Math.abs(sprite-tick%40)<2) {
        final int i = ((tick) / 40);
        final int j = tick % 40;
        monitor.get(i).set(j,"#");
      }
    }

    private static void display(){
      for (var row : monitor) {
        for(var sign : row){
          System.out.print(sign+" ");
        }
        System.out.println();
      }
    }
  }

  private class Processor {

    private static int cycles = 0;
    private static int x= 1;

    public static void process(final Bundle bundle) {
      for (int i = 0; i < bundle.command.cycles; i++) {
        Monitor.sync(x, cycles);
        System.out.println("Tik " + (++cycles));
      }
      execute(bundle);
      System.out.println(" x = "+x);
    }

    private static void execute(final Bundle bundle){
      if(bundle.command==Command.ADDX) {
        x+=Integer.parseInt(bundle.arg.get(0));
      }
    }
  }



  private enum Command {
    NOOP("noop"),
    ADDX("addx");
    private final int cycles;
    private final String name;

    Command(String name) {
      this.name = name;
      if (this.name.equals("addx")) {
        cycles = 2;
      } else {
        cycles = 1;
      }
    }
  }

  private static class Bundle{

    public Bundle(final String line) {
      String[] commandElements = line.split(" ");
      this.command = Command.valueOf(commandElements[0].toUpperCase());
      this.arg = Arrays.stream(commandElements).skip(1).toList();
    }

    private final Command command;
    private final List<String>  arg;
  }
}

