package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * 两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗?
 *
 * @author itutry
 * @create 2020-04-27_21:50
 */
@Slf4j(topic = "c.SynchronizedImproveTest")
public class SynchronizedImproveTest {

  public static void main(String[] args) throws InterruptedException {
    Room room = new Room();
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 5000; i++) {
        room.increase();
      }
    }, "t1");
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 5000; i++) {
        room.decrease();
      }
    }, "t2");

    t1.start();
    t2.start();

    t1.join();
    t2.join();
    log.debug("{}", room.getCounter());
  }

  private static class Room {

    private int counter = 0;

    public void increase() {
      synchronized (this) {
        counter++;
      }
    }

    public void decrease() {
      synchronized (this) {
        counter--;
      }
    }

    public int getCounter() {
      synchronized (this) {
        return counter;
      }
    }
  }
}
