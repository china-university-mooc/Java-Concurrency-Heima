package com.itutry.happensbefore;

import com.itutry.util.Sleeper;

/**
 * 解锁前的变量写 hb 加锁后的变量读
 *
 * @author itutry
 * @create 2020-05-20_09:18
 */
public class HbLock {

  static int x = 0;
  static Object m = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      while (true) {
        synchronized (m) {
          if (x == 10) {
            break;
          }
        }
      }
    }, "t2").start();

    Sleeper.sleep(1);
    new Thread(() -> {
      synchronized (m) {
        x = 10;
      }
    }, "t1").start();
  }
}
