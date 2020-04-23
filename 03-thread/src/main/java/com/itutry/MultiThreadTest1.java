package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-23_12:08
 */
@Slf4j(topic = "c.MultiThreadTest1")
public class MultiThreadTest1 {

  public static void main(String[] args) {
    final Runnable r = () -> {
      while (true) {
        log.debug("running");
      }
    };

    new Thread(r, "t1").start();
    new Thread(r, "t2").start();
  }
}
