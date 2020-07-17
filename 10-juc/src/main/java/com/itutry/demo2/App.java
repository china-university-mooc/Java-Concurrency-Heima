package com.itutry.demo2;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.locks.Lock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    Lock lock = new MyLock();

    new Thread(() -> {
      lock.lock();
      log.debug("locking...");
      lock.lock();
      log.debug("locking...");
      try {
        sleep(1);
      } finally {
        log.debug("unlocking...");
        lock.unlock();
        log.debug("unlocking...");
        lock.unlock();
      }
    }, "t1").start();

    new Thread(() -> {
      lock.lock();
      log.debug("locking...");
      try {
        sleep(1);
      } finally {
        log.debug("unlocking...");
        lock.unlock();
      }
    }, "t2").start();
  }
}
