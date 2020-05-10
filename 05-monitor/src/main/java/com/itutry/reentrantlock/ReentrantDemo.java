package com.itutry.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 可重入
 *
 * @author itutry
 * @create 2020-05-09_23:20
 */
@Slf4j(topic = "c.ReentrantDemo")
public class ReentrantDemo {

  private static ReentrantLock lock = new ReentrantLock();

  public static void main(String[] args) {
    method1();
  }

  public static void method1() {
    lock.lock();
    try {
      log.debug("execute method1");
      method2();
    } finally {
      lock.unlock();
    }
  }

  public static void method2() {
    lock.lock();
    try {
      log.debug("execute method2");
      method3();
    } finally {
      lock.unlock();
    }
  }

  public static void method3() {
    lock.lock();
    try {
      log.debug("execute method3");
    } finally {
      lock.unlock();
    }
  }
}
