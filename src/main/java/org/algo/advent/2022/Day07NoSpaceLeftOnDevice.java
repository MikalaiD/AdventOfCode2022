package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;
import javax.sound.midi.Soundbank;
import org.w3c.dom.Node;

public class Day07NoSpaceLeftOnDevice {

  private static int LIMIT = 70000000;
  private static int REQUIRED_SPACE = 30000000;

  public static void main(String[] args) throws IOException {

    List<String> commands = Files.readAllLines(Path.of("./src/main/resources/07_input")).stream()
        .toList();

    var cursor = new Node(true);
    for (String command : commands
    ) {
      cursor = cursor.map(command);
    }
    var root = cursor.getRoot();
    var amountToFree = REQUIRED_SPACE-LIMIT+root.getSize();

    List<Node> nodes = root.getAllSubDirs().toList();
//    System.out.println("Part 1: " + nodes.stream().filter(n->n.getSize()<=100000).mapToInt(Node::getSize).sum() );
    System.out.println("Part 2: " + nodes.stream().map(Node::getSize).filter(s->s>=amountToFree)
        .sorted().findFirst().get());

  }

  private static class Node {

    private final boolean root;
    private int size;
    private boolean dir;
    private String name;
    private Node parent;
    private LinkedHashMap<String, Node> children;

    public boolean isRoot() {
      return root;
    }

    public Node(boolean root) {
      this.root = root;
      if (root) {
        this.dir = true;
        this.name = "/";
      }
    }

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public boolean isDir() {
      return dir;
    }

    public void setDir(boolean dir) {
      this.dir = dir;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }

    public LinkedHashMap<String, Node> getChildren() {
      return children;
    }

    public void setChildren(
        LinkedHashMap<String, Node> children) {
      this.children = children;
    }

    public Node map(final String command) {
      System.out.println(command);
      switch (command.substring(0, 4)) {
        case "$ cd":
          return cd(command.substring(5));
        case "$ ls":
          children = new LinkedHashMap<>();
          return this;
        default:
          return create(command);
      }
    }

    private Node create(final String line) {
      var command = line.split(" ");
      if (command[0].equals("dir")) {
        createDirectoryUnderCurrentNode(command[1].trim());
        return this;
      } else {
        createFileUnderCurrentNode(Integer.parseInt(command[0]), command[1]);
        return this;
      }
    }

    private Node cd(final String destination) {
      if (destination.equals("/")) {
        return getRoot();
      } else if (destination.equals("..")) {
        return this.getParent();
      } else {
        return this.getChildren().get(destination);
      }
    }

    public Node getRoot() {
      Node output = this;
      while (!output.isRoot()) {
        output = output.getParent();
      }
      return output;
    }

    private void createDirectoryUnderCurrentNode(final String dirName) {
      var directory = new Node(false);
      directory.setDir(true);
      directory.setSize(0);
      directory.setName(dirName);
      directory.setParent(this);
      this.children.put(dirName, directory);
    }

    private void createFileUnderCurrentNode(final int size, final String fileName) {
      var file = new Node(false);
      file.setDir(false);
      file.setSize(size);
      file.setName(fileName);
      file.setParent(this);
      this.children.put(fileName, file);
      this.recalculateSize(size);
    }

    private void recalculateSize(int size) {
      this.size += size;
      if (!root) {
        this.getParent().recalculateSize(size);
      }
    }

    public Stream<Node> getAllSubDirs() {

      if(this.getChildren()==null || this.getChildren().size()==0) {
        return Stream.of(this);
      }

      return Stream.concat(Stream.of(this),
          this.getChildren().values().stream().filter(Node::isDir).flatMap(Node::getAllSubDirs));
    }
  }
}

