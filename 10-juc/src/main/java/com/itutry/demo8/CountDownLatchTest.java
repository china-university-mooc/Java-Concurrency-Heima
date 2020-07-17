package com.itutry.demo8;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchTest {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(3);

    new Thread(() -> {
      log.debug("begin...");
      sleep(1);
      countDownLatch.countDown();
      log.debug("end...");
    }).start();

    new Thread(() -> {
      log.debug("begin...");
      sleep(2);
      countDownLatch.countDown();
      log.debug("end...");
    }).start();

    new Thread(() -> {
      log.debug("begin...");
      sleep(1.5);
      countDownLatch.countDown();
      log.debug("end...");
    }).start();

    try {
      log.debug("waiting...");
      countDownLatch.await();
      log.debug("wait end...");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
