package com.itutry.ordercontrol;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 用 ReentrantLock 的await&signal 实现线程顺序执行
 *
 * @author itutry
 * @create 2020-05-10_10:51
 */
@Slf4j(topic = "c.OrderWaitNotify")
public class OrderAwaitSignal {

  private static ReentrantLock lock = new ReentrantLock();
  private static Condition endCondition = lock.newCondition();
  private  static boolean thread2End = false;

  public static void main(String[] args) {

    final Thread t1 = new Thread(() -> {
      lock.lock();
      try {
        while (!thread2End) {
          endCondition.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
      log.debug("1");
    }, "t1");
    final Thread t2 = new Thread(() -> {
      log.debug("2");
      lock.lock();
      try {
        thread2End = true;
        endCondition.signalAll();
      } finally {
        lock.unlock();
      }
    }, "t2");

    t1.start();
    t2.start();
  }
}
