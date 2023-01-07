package org.example;

import java.util.ArrayList;
import java.util.List;

public class Utils {

  public static <T> List<List<T>> transpose(final List<List<T>> lines) {
    int lineLength = lines.get(0).size();
    final List<List<T>> output = new ArrayList<>(lineLength);
    for (int i = 0; i < lineLength; i++) {
      output.add(new ArrayList<>());
      for (List<T> line : lines) {
        output.get(i).add(line.get(i));
      }
    }
    return output;
  }
}
