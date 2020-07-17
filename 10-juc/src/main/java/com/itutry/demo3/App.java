package com.itutry.demo3;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    DataContainer container = new DataContainer();

    new Thread(() -> {
      container.read();
//      container.write(new Object());
    }, "t1").start();


    new Thread((
    ) -> {
//      container.read();
      container.write(new Object());
    }, "t2").start();
  }
}

@Slf4j
class DataContainer {

  private Object data;

  private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
  private ReadLock r = rw.readLock();
  private WriteLock w = rw.writeLock();

  public Object read() {
    log.debug("获取读锁...");
    r.lock();
    try {
      log.debug("读取");

//      w.lock();
//      try {
//
//      } finally {
//        w.unlock();
//      }

      return data;
    } finally {
      r.unlock();
      log.debug("释放读锁");
    }
  }

  public void write(Object data) {
    log.debug("获取写锁...");
    w.lock();
    try {
      log.debug("写入");

//      r.lock();
//      try {
//
//      } finally {
//        r.unlock();
//      }

      this.data = data;
    } finally {
      w.unlock();
      log.debug("释放写锁");
    }
  }
}
