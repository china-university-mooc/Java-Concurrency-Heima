package com.itutry.demo7;

import java.sql.Connection;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class SemaphorePool implements Pool {

  private Connection[] connections;
  private AtomicIntegerArray status;
  private int poolSize;

  private Semaphore semaphore;

  public SemaphorePool(int poolSize) {
    this.poolSize = poolSize;
    // 许可数与资源数一致
    this.semaphore = new Semaphore(poolSize);
    this.connections = new Connection[poolSize];
    status = new AtomicIntegerArray(new int[poolSize]);
    for (int i = 0; i < poolSize; i++) {
      connections[i] = new MockConnection("连接" + (i + 1));
    }
  }

  @Override
  public Connection borrow() {
    try {
      semaphore.acquire();  // 没有许可的线程在此等待
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    for (int i = 0; i < poolSize; i++) {
      if (status.get(i) == 0 && status.compareAndSet(i, 0, 1)) {
        return connections[i];
      }
    }

    throw new RuntimeException("不可能执行到这儿");
  }

  @Override
  public void free(Connection conn) {
    for (int i = 0; i < poolSize; i++) {
      if (connections[i] == conn) {
        status.set(i, 0);
        semaphore.release();
        break;
      }
    }
  }
}
