package com.itutry.demo2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class WordCounter {

  public static void main(String[] args) {
    demo(
        ConcurrentHashMap<String, LongAdder>::new,

        (map, words) -> {
          words.forEach(w -> {
            // 如果缺少key就计算生成一个值，放入map
            LongAdder value = map.computeIfAbsent(w, k -> new LongAdder());
            value.increment();
          });
        }
    );
  }

  private static void test2() {
    BiConsumer<Map<String, AtomicInteger>, List<String>> consumer = (map, words) -> {
      words.forEach(w -> {
//        AtomicInteger num = map.putIfAbsent(w, new AtomicInteger(0));
        AtomicInteger value = map.get(w);
        if (value == null) {
          synchronized (map) {
            value = map.get(w);
            if (value == null) {
              value = new AtomicInteger(0);
              map.put(w, value);
            }
          }
        }

        value.incrementAndGet();
      });
    };
    Supplier<Map<String, AtomicInteger>> supplier = ConcurrentHashMap::new;
    demo(supplier, consumer);
  }

  private static void test1() {
    BiConsumer<Map<String, Integer>, List<String>> consumer = (map, words) -> {
      words.forEach(w -> {
        Integer num = map.getOrDefault(w, 0);
        map.put(w, num + 1);
      });
    };
    Supplier<Map<String, Integer>> supplier = HashMap::new;
    demo(supplier, consumer);
  }

  private static <V> void demo(Supplier<Map<String, V>> supplier,
      BiConsumer<Map<String, V>, List<String>> consumer) {
    final Map<String, V> counterMap = supplier.get();

    List<Thread> ts = new ArrayList<>();
    for (int i = 0; i < 26; i++) {
      int idx = i;

      final Thread thread = new Thread(() -> {
        List<String> words = readFromFile(idx);
        consumer.accept(counterMap, words);
      });
      ts.add(thread);
    }

    ts.forEach(Thread::start);
    for (Thread t : ts) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    System.out.println(counterMap);
  }

  private static List<String> readFromFile(int idx) {
    List<String> words = new ArrayList<>();
    try (Scanner scanner = new Scanner(new FileInputStream("temp/" + (idx + 1) + ".txt"))) {
      while (scanner.hasNext()) {
        words.add(scanner.next());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return words;
  }
}
