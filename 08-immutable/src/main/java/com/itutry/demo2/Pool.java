package com.itutry.demo2;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicIntegerArray;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pool {

  private final int poolSize;

  private Connection[] connections;

  /**
   * 0 表示空闲，1 表示繁忙
   */
  private AtomicIntegerArray status;

  public Pool(int poolSize) {
    this.poolSize = poolSize;
    connections = new Connection[poolSize];
    status = new AtomicIntegerArray(new int[poolSize]);
    for (int i = 0; i < poolSize; i++) {
      connections[i] = new MockConnection("链接" + (i + 1));
    }
  }

  public Connection borrow() {
    while(true) {
      for (int i = 0; i < poolSize; i++) {
        if (status.get(i) == 0 && status.compareAndSet(i, 0, 1)) {
          log.debug("borrow {}", connections[i]);
          return connections[i];
        }
      }

      synchronized(this) {
        try {
          log.debug("wait...");
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void free(Connection conn) {
    for (int i = 0; i < poolSize; i++) {
      if (connections[i] == conn) {
        status.set(i, 0);

        synchronized (this) {
          log.debug("free {}", conn);
          notifyAll();
        }
        break;
      }
    }
  }
}
