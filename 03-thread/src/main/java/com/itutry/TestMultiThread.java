package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_12:12
 */
@Slf4j(topic = "c.TestMultiThread")
public class TestMultiThread {

  public static void main(String[] args) {
    test1();
    test2();
  }

  private static void test2() {
    Runnable r = () -> {
      while (true) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
        }
      }
    };

    new Thread(r, "t1").start();
    new Thread(r, "t2").start();
  }

  private static void test1() {
    final Runnable r = () -> {
      while (true) {
        log.debug("running");
      }
    };

    new Thread(r, "t1").start();
    new Thread(r, "t2").start();
  }
}
