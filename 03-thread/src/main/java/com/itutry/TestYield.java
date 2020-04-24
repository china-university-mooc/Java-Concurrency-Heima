package com.itutry;

/**
 * @author itutry
 * @create 2020-04-24_09:40
 */
public class TestYield {

  public static void main(String[] args) {
    Runnable r1 = () -> {
      int count = 0;
      for (;;) {
        System.out.println("---->1  " + count++);
      }
    };

    Runnable r2 = () -> {
      int count = 0;
      for (;;) {
//        Thread.yield();
        System.out.println("---->2  " + count++);
      }
    };

    final Thread t1 = new Thread(r1, "t1");
    final Thread t2 = new Thread(r2, "t2");
//    t1.setPriority(Thread.MAX_PRIORITY);
//    t2.setPriority(Thread.MIN_PRIORITY);

    t1.start();
    t2.start();
  }
}
