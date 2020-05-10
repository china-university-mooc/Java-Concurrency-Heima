package com.itutry.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁，先竞争锁的先获得锁
 *
 * @author itutry
 * @create 2020-05-10_00:00
 */
public class FairDemo {

  public static void main(String[] args) throws InterruptedException {
    ReentrantLock lock = new ReentrantLock(true);
    lock.lock();

    for (int i = 0; i < 500; i++) {
      new Thread(() -> {
        lock.lock();
        try {
          System.out.println(Thread.currentThread().getName() + " running...");
        } finally {
          lock.unlock();
        }
      }, "t" + i).start();
    }

    // 1s 之后去争抢锁
    Thread.sleep(1000);
    new Thread(() -> {
      lock.lock();
      try {
        System.out.println(Thread.currentThread().getName() + " running...");
      } finally {
        lock.unlock();
      }
    }, "强行插入").start();

    lock.unlock();
  }
}
