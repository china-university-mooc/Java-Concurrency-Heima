package com.itutry;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * “线程八锁”,考察 synchronized 锁住的是哪个对象
 *
 * @author itutry
 * @create 2020-04-27_21:56
 */
@Slf4j(topic = "c.SynchronizedMethodTest")
public class SynchronizedMethodTest {

  public static void main(String[] args) {
//    test1();
//    test2();
//    test3();
//    test4();
//    test5();
//    test6();
//    test7();
    test8();
  }

  /**
   * 结果：2(1s后)1, (1s后)12
   */
  private static void test8() {
    final Number6 n1 = new Number6();
    final Number6 n2 = new Number6();
    log.debug("begin");
    new Thread(() -> n1.a()).start();
    new Thread(() -> n2.b()).start();
  }

  /**
   * 结果：2(1s后)1
   */
  private static void test7() {
    final Number5 n1 = new Number5();
    final Number5 n2 = new Number5();
    log.debug("begin");
    new Thread(() -> n1.a()).start();
    new Thread(() -> n2.b()).start();
  }

  /**
   * 结果：2(1s后)1, (1s后)12
   */
  private static void test6() {
    final Number6 n = new Number6();
    log.debug("begin");
    new Thread(() -> n.a()).start();
    new Thread(() -> n.b()).start();
  }

  /**
   * 结果：2(1s后)1
   */
  private static void test5() {
    final Number5 n = new Number5();
    log.debug("begin");
    new Thread(() -> n.a()).start();
    new Thread(() -> n.b()).start();
  }

  /**
   * 结果：2(1s后)1
   */
  private static void test4() {
    final Number2 n1 = new Number2();
    final Number2 n2 = new Number2();
    log.debug("begin");
    new Thread(n1::a).start();
    new Thread(n2::b).start();
  }

  /**
   * 结果：23(1s后)1, 32(1s后)1, 3(1s后)12
   */
  private static void test3() {
    final Number3 n = new Number3();
    log.debug("begin");
    new Thread(n::a).start();
    new Thread(n::b).start();
    new Thread(n::c).start();
  }

  /**
   * 结果：2(1s后)1 或 (1s后)12
   */
  private static void test2() {
    final Number2 n = new Number2();
    log.debug("begin");
    new Thread(n::a).start();
    new Thread(n::b).start();
  }

  /**
   * 结果：12 或 21
   */
  private static void test1() {
    final Number1 n = new Number1();
    log.debug("begin");
    new Thread(n::a).start();
    new Thread(n::b).start();
  }

  @Slf4j(topic = "c.Number1")
  private static class Number1 {

    public synchronized void a() {
      log.debug("1");
    }

    public synchronized void b() {
      log.debug("2");
    }
  }

  @Slf4j(topic = "c.Number2")
  private static class Number2 {

    public synchronized void a() {
      sleep(1);
      log.debug("1");
    }

    public synchronized void b() {
      log.debug("2");

    }
  }

  @Slf4j(topic = "c.Number3")
  private static class Number3 {

    public synchronized void a() {
      sleep(1);
      log.debug("1");
    }

    public synchronized void b() {
      log.debug("2");

    }

    public void c() {
      log.debug("3"); }
  }

  @Slf4j(topic = "c.Number5")
  private static class Number5 {

    public static synchronized void a() {
      sleep(1);
      log.debug("1");
    }

    public synchronized void b() {
      log.debug("2");
    }
  }

  @Slf4j(topic = "c.Number6")
  private static class Number6 {

    public static synchronized void a() {
      sleep(1);
      log.debug("1");
    }

    public static synchronized void b() {
      log.debug("2");
    }
  }

}
