package com.ituyty.limit.cpu;

/**
 * @author itutry
 * @create 2020-04-24_14:53
 */
public class LimitCpuSleep {

  public static void main(String[] args) {
    notLimit();
//    limit();
  }

  private static void notLimit() {
    new Thread(() -> {
      while (true) {

      }
    }).start();
  }

  private static void limit() {
    new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
