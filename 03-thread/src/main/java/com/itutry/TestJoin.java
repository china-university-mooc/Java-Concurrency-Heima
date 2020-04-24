package com.itutry;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_09:56
 */
@Slf4j(topic = "c.TestJoin")
public class TestJoin {

  private static int r = 0;
  private static int s = 0;

  public static void main(String[] args) throws InterruptedException {
//    test1();
//    test2();
    test3();
  }

  private static void test1() {
    log.debug("开始");
    Thread t1 = new Thread(() -> {
      log.debug("开始");
      sleep(1);
      log.debug("结束");
      r = 10;
    }, "t1");
    t1.start();
    log.debug("结果为:{}", r);
    log.debug("结束");
  }

  private static void test2() throws InterruptedException {
    Thread t1 = new Thread(() -> {
      sleep(1);
      r = 10;
    });
    Thread t2 = new Thread(() -> {
      sleep(2);
      s = 20;
    });
    t1.start();
    t2.start();
    final long start = System.currentTimeMillis();
    log.debug("join begin");
    t2.join();
    log.debug("t2 join end");
    t1.join();
    log.debug("t1 join end");
    final long end = System.currentTimeMillis();
    log.debug("r:{}, s:{}, cost: {}", r, s, end - start);
  }

  private static void test3() throws InterruptedException {
    Thread t1 = new Thread(() -> {
      sleep(2);
      r = 10;
    }, "t1");
    t1.start();

    final long start = System.currentTimeMillis();
//    t1.join(1500);
    t1.join(3000);
    final long end = System.currentTimeMillis();

    log.debug("r:{}, s:{}, cost: {}", r, s, end - start);
  }
}
