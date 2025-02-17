package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * 两个线程对初始值为 0 的静态变量一个做自增，一个做自减，各做 5000 次，结果是 0 吗?
 *
 * @author itutry
 * @create 2020-04-27_21:27
 */
@Slf4j(topic = "c.SafetyTest")
public class SafetyTest {

  private static int counter = 0;

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 5000; i++) {
        counter++;
      }
    }, "t1");
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 5000; i++) {
        counter--;
      }
    }, "t2");

    t1.start();
    t2.start();

    t1.join();
    t2.join();
    log.debug("{}", counter);
  }
}
