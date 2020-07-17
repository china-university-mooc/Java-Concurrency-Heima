package com.itutry.demo5;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.locks.StampedLock;
import lombok.extern.slf4j.Slf4j;

public class StampedLockTest {

  public static void main(String[] args) {
    DataContainer dataContainer = new DataContainer(1);

    new Thread(() -> {
      dataContainer.read(1);
    }, "t1").start();

    sleep(0.5);

    new Thread(() -> {
      dataContainer.write(0);
    }, "t2").start();
  }
}

@Slf4j
class DataContainer {

  private int data;
  private final StampedLock lock = new StampedLock();

  public DataContainer(int data) {
    this.data = data;
  }

  public int read(int readTime) {
    long stamp = lock.tryOptimisticRead();
    log.debug("optimistic read lock {}", stamp);
    sleep(readTime);
    int d = this.data;
    if (lock.validate(stamp)) {
      log.debug("read finished {}", stamp);
      return d;
    }

    log.debug("updating to read lock {}", stamp);
    stamp = lock.readLock();
    log.debug("read lock {}", stamp);
    try {
      sleep(readTime);
      log.debug("read finished {}", stamp);
      return this.data;
    } finally {
      log.debug("read unlock {}", stamp);
      lock.unlockRead(stamp);
    }
  }

  public void write(int newData) {
    long stamp = lock.writeLock();
    log.debug("write lock {}", stamp);
    try {
      sleep(2);
      this.data = newData;
    } finally {
      log.debug("write unlock {}", stamp);
      lock.unlockWrite(stamp);
    }
  }
}
