package com.itutry.happensbefore;

import static com.itutry.util.Sleeper.sleep;

/**
 * @author itutry
 * @create 2020-05-20_09:27
 */
public class HbInterrupt {

  static int x;

  public static void main(String[] args) {
    Thread t2 = new Thread(() -> {
      while (true) {
        if (Thread.currentThread().isInterrupted()) {
          System.out.println(x);
          break;
        }
      }
    }, "t2");
    t2.start();

    new Thread(() -> {
      sleep(1);
      x = 10;
      t2.interrupt();
    }, "t1").start();

    while (!t2.isInterrupted()) {
      Thread.yield();
    }
    System.out.println(x);
  }
}
