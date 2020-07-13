package com.itutry.demo1;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "App")
public class App {

  public static void main(String[] args) throws InterruptedException {
    test2();
  }

  private static void test2() throws InterruptedException {
    ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10);
    for (int i = 0; i < 15; i++) {
      int j = i;
      threadPool.execute(() -> {
        try {
          Thread.sleep(10000000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.debug("{}", j);
      });
    }
  }

  private static void test1() throws InterruptedException {
    ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10);
    for (int i = 0; i < 5; i++) {
      int j = i;
      threadPool.execute(() -> log.debug("{}", j));
    }

    Thread.sleep(2000);
    threadPool.execute(() -> log.debug("{}", 5));
  }
}
