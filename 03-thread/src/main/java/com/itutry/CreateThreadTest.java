package com.itutry;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_12:14
 */
@Slf4j(topic = "c.CreateThreadTest")
public class CreateThreadTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
//    test1();
//    test2();
    test3();
  }

  /**
   * 实现Callable
   */
  private static void test3() throws ExecutionException, InterruptedException {
    final FutureTask<String> task = new FutureTask<>(() -> {
      log.debug("running");
      Thread.sleep(1000);
      return "success";
    });

    final Thread t = new Thread(task, "t1");
    t.start();

    log.debug("结果是：{}", task.get());
  }

  /**
   * 实现Runnable
   */
  private static void test2() {
//    final Runnable r = new Runnable() {
//      @Override
//      public void run() {
//        log.debug("running");
//      }
//    };

    final Runnable r = () -> log.debug("running");

    final Thread t = new Thread(r, "t1");
    t.start();
    log.debug("running");
  }

  /**
   * 继承Thread
   */
  private static void test1() {
    final Thread t = new Thread() {
      @Override
      public void run() {
        log.debug("running");
      }
    };
    t.setName("t1");
    t.start();

    log.debug("running");
  }
}
