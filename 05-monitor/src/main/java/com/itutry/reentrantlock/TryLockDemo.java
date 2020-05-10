package com.itutry.reentrantlock;

import com.itutry.util.Sleeper;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 尝试获取锁，获取不到立即失败
 *
 * @author itutry
 * @create 2020-05-09_23:41
 */
@Slf4j(topic = "c.TryLockDemo")
public class TryLockDemo {

  public static void main(String[] args) {
    final ReentrantLock lock = new ReentrantLock();
    final Thread t1 = new Thread(() -> {
      log.debug("尝试获取锁");
      if (!lock.tryLock()) {
        log.debug("获取不到锁，立即失败");
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
      log.debug("获得锁");
      t1.start();
      Sleeper.sleep(2);
    } finally {
      lock.unlock();
    }
  }
}
