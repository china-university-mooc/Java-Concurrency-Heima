package com.itutry.demo4;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    method3();
  }

  private static void method2() {
    ScheduledExecutorService poolExecutor = new ScheduledThreadPoolExecutor(2);

    Runnable task1 = () -> {
      log.debug("task 1");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      int i = 1 / 0;
    };

    Runnable task2 = () -> {
      log.debug("task 2");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    log.debug("begin...");
    poolExecutor.schedule(task1, 1, TimeUnit.SECONDS);
    poolExecutor.schedule(task2, 1, TimeUnit.SECONDS);

    poolExecutor.shutdown();
  }

  private static void method3() {
    ScheduledExecutorService poolExecutor = new ScheduledThreadPoolExecutor(2);

    Runnable task2 = () -> {
      log.debug("task 2");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

//    poolExecutor.scheduleAtFixedRate(task2, 1, 1, TimeUnit.SECONDS);
    poolExecutor.scheduleWithFixedDelay(task2, 1, 1, TimeUnit.SECONDS);
  }

  private static void method1() {
    Timer timer = new Timer();

    TimerTask timerTask1 = new TimerTask() {

      @SneakyThrows
      @Override
      public void run() {
        log.debug("task 1");
//        Thread.sleep(1000);
        int i = 1 / 0;
      }
    };

    TimerTask timerTask2 = new TimerTask() {

      @SneakyThrows
      @Override
      public void run() {
        log.debug("task 2");
      }
    };

    log.debug("begin...");
    timer.schedule(timerTask1, 1000);
    timer.schedule(timerTask2, 1000);
  }
}
