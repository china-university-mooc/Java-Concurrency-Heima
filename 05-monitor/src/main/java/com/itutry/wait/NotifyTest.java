package com.itutry.wait;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-29_23:18
 */
@Slf4j(topic = "c.WaitNotifyTest")
public class NotifyTest {

  public static void main(String[] args) {
    final Object lock = new Object();

    new Thread(() -> {
      synchronized(lock) {
        log.debug("条件不满足，等待");
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.debug("条件满足了，继续执行");
      }
    }, "t1").start();

    new Thread(() -> {
      synchronized(lock) {
        log.debug("条件不满足，等待");
        try {
          lock.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.debug("条件满足了，继续执行");
      }
    }, "t2").start();

    sleep(1);
    synchronized (lock) {
      log.debug("唤醒lock上的等待线程");
//      lock.notify();
      lock.notifyAll();
    }
  }
}
