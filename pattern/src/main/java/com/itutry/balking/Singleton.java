package com.itutry.balking;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-05-19_23:00
 */
@Slf4j(topic = "c.Singleton")
public final class Singleton {

  private static Singleton INSTANCE = null;

  private Singleton() {

  }

  public static synchronized Singleton getInstance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }
    INSTANCE = new Singleton();
    return INSTANCE;
  }

  public static void main(String[] args) {
    final List<Thread> threads = IntStream.range(0, 10).mapToObj(i -> {
      return new Thread(() -> {
        log.debug(Singleton.getInstance().toString());
      }, "t" + i);
    }).collect(Collectors.toList());
    threads.forEach(Thread::start);
  }
}
