package com.itutry.demo8;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchTest1 {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(3);
    ExecutorService pool = Executors.newFixedThreadPool(4);

    pool.submit(() -> {
      log.debug("begin...");
      sleep(1);
      countDownLatch.countDown();
      log.debug("end...");
    });

    pool.submit(() -> {
      log.debug("begin...");
      sleep(2);
      countDownLatch.countDown();
      log.debug("end...");
    });

    pool.submit(() -> {
      log.debug("begin...");
      sleep(1.5);
      countDownLatch.countDown();
      log.debug("end...");
    });

    pool.submit(() -> {
      try {
        log.debug("waiting...");
        countDownLatch.await();
        log.debug("wait end...");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }
}
