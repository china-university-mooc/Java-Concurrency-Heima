package com.itutry.demo3;

import com.itutry.util.Sleeper;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class App {

  public static void main(String[] args) {
    List<Integer> list = new CopyOnWriteArrayList<>();

    list.add(1);
    list.add(2);
    list.add(3);

    Iterator<Integer> it = list.iterator();

    new Thread(() -> {
      list.remove(0);
      System.out.println(list);
    }).start();

    Sleeper.sleep(1);
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
