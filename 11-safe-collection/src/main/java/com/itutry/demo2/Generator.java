package com.itutry.demo2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Generator {

  private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";

  public static void main(String[] args) {
    int len = ALPHA.length();
    int count = 200;

    List<String> list = new ArrayList<>(len * count);

    for (int i = 0; i < len; i++) {
      char c = ALPHA.charAt(i);
      for (int j = 0; j < count; j++) {
        list.add(String.valueOf(c));
      }
    }

    Collections.shuffle(list);
    for (int i = 0; i < len; i++) {
      try (PrintWriter out = new PrintWriter(
          new OutputStreamWriter(new FileOutputStream("temp/" + (i + 1) + ".txt")))) {
        String collect = list.subList(i * count, (i + 1) * count).stream()
            .collect(Collectors.joining("\n"));
        out.print(collect);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
