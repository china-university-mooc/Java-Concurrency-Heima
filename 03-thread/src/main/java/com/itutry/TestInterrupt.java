package com.itutry;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_11:41
 */
@Slf4j(topic="c.TestInterrupt")
public class TestInterrupt {

  public static void main(String[] args) {
//    test1();
//    test2();
      test3();
  }

  private static void test3() {
    final Thread t1 = new Thread(() -> {
      log.debug("park...");
      LockSupport.park();
      log.debug("unpark...");
//      log.debug("打断标记:{}", Thread.currentThread().isInterrupted());
      log.debug(" 打断标记:{}", Thread.interrupted());

      log.debug("park...");
      LockSupport.park();
      log.debug("unpark...");
    }, "t1");

    t1.start();

    sleep(0.5);
    t1.interrupt();
  }

  /**
   * 打断正常运行的线程
   */
  private static void test2() {
    final Thread t1 = new Thread(() -> {
      while (true) {
        final boolean interrupted = Thread.currentThread().isInterrupted();
        if (interrupted) {
          log.debug("打断状态:{}", Thread.currentThread().isInterrupted());
          break;
        }
      }
    }, "t1");

    t1.start();

    sleep(1);
    t1.interrupt();
  }

  /**
   * 打断阻塞线程
   */
  private static void test1() {
    final Thread t1 = new Thread(() -> {
      sleep(1);
    }, "t1");

    t1.start();

    sleep(0.5);
    t1.interrupt();
    log.debug("t1的打断状态:{}", t1.isInterrupted());
  }
}
