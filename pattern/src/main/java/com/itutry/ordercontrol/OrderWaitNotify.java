package com.itutry.ordercontrol;

import lombok.extern.slf4j.Slf4j;

/**
 * 用 wait&notify 实现线程顺序执行
 *
 * @author itutry
 * @create 2020-05-10_10:51
 */
@Slf4j(topic = "c.OrderWaitNotify")
public class OrderWaitNotify {

  private static Object lock = new Object();
  private  static boolean thread2End = false;

  public static void main(String[] args) {

    final Thread t1 = new Thread(() -> {
      synchronized (lock) {
        while(!thread2End) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      log.debug("1");
    }, "t1");
    final Thread t2 = new Thread(() -> {
      log.debug("2");
      synchronized (lock) {
        thread2End = true;
        lock.notifyAll();
      }
    }, "t2");

    t1.start();
    t2.start();
  }
}
