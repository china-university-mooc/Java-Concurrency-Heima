package com.itutry.demo1;

import com.itutry.util.Sleeper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * fail-fast和fail-safe机制
 */
public class App {

  public static void main(String[] args) {
//    Map<String, Integer> map = new HashMap<>();
    Map<String, Integer> map = new ConcurrentHashMap<>();
    for (int i = 0; i < 100; i++) {
      map.put("key" + i, i);
    }

    new Thread(() -> {
      Set<Entry<String, Integer>> entrySet = map.entrySet();
      Iterator<Entry<String, Integer>> it = entrySet.iterator();
      while (it.hasNext()) {
        Sleeper.sleep(0.01);
        System.out.println(it.next());
      }
//      map.entrySet().forEach(System.out::println);
    }).start();

    new Thread(() -> {
      System.out.println("修改");
//      map.put("key101", 101);
      map.remove("key99");
    }).start();
  }
}
