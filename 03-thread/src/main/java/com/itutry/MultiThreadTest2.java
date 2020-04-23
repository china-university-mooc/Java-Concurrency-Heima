package com.itutry;

/**
 * @author itutry
 * @create 2020-04-23_13:51
 */
public class MultiThreadTest2 {

  public static void main(String[] args) {
    Runnable r = () -> {
      while(true) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {}
      }
    };

    new Thread(r, "t1").start();
    new Thread(r, "t2").start();
  }
}
