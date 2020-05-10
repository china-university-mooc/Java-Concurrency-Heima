package com.itutry.activeness;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * 死锁：互相持有对方的锁，导致谁也无法获得锁
 *
 * @author itutry
 * @create 2020-05-08_08:56
 */
@Slf4j(topic = "c.DeadLockDemo")
public class DeadLockDemo {

  public static void main(String[] args) {
    final Object A = new Object();
    final Object B = new Object();

    new Thread(() -> {
      synchronized (A) {
        log.debug("lock A");
        sleep(1);
        synchronized (B) {
          log.debug("lock B");
          log.debug("操作...");
        }
      }
    }, "t1").start();

    new Thread(() -> {
      synchronized (B) {
        log.debug("lock B");
        sleep(0.5);
        synchronized (A) {
          log.debug("lock A");
          log.debug("操作...");
        }
      }
    }, "t2").start();
  }
}
