package com.itutry.demo7;

import java.sql.Connection;
import java.util.concurrent.atomic.AtomicIntegerArray;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CasPool implements Pool {

  private Connection[] connections;
  private AtomicIntegerArray status;
  private int poolSize;

  public CasPool(int poolSize) {
    this.poolSize = poolSize;
    this.connections = new Connection[poolSize];
    status = new AtomicIntegerArray(new int[poolSize]);
    for (int i = 0; i < poolSize; i++) {
      connections[i] = new MockConnection("连接" + (i + 1));
    }
  }

  @Override
  public Connection borrow() {
    while (true) {
      for (int i = 0; i < poolSize; i++) {
        if (status.get(i) == 0 && status.compareAndSet(i, 0, 1)) {
          return connections[i];
        }
      }

      synchronized (this) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void free(Connection conn) {
    for (int i = 0; i < poolSize; i++) {
      if (connections[i] == conn) {
        status.set(i, 0);

        synchronized (this) {
          notifyAll();
        }
        break;
      }
    }
  }
}
