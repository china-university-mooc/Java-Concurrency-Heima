package com.itutry.reentrantlock;

import com.itutry.util.Sleeper;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 可被 interrupt() 中断
 *
 * @author itutry
 * @create 2020-05-09_23:26
 */
@Slf4j(topic = "c.InterruptableDemo")
public class InterruptableDemo {

  public static void main(String[] args) {
    final ReentrantLock lock = new ReentrantLock();
    final Thread t1 = new Thread(() -> {
      log.debug("waiting lock...");

//      lock.lock();
      try {
        lock.lockInterruptibly();
      } catch (InterruptedException e) {
        log.debug("等锁的过程中被打断");
        e.printStackTrace();
        return;
      }

      try {
        log.debug("获得锁");
      } finally {
        lock.unlock();
      }
    }, "t1");

    lock.lock();
    try {
      t1.start();
      Sleeper.sleep(1);
      log.debug("打断线程t1");
      t1.interrupt();
    } finally {
      lock.unlock();
    }
  }
}
