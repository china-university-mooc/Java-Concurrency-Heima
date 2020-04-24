package com.itutry;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_09:33
 */
@Slf4j(topic = "c.SleepTest")
public class SleepTest {

  public static void main(String[] args) throws InterruptedException {
    final Thread t1 = new Thread(() -> {
      log.debug("进入睡眠");
      try {
//        Thread.sleep(2000);
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.debug("睡醒了");
    }, "t1");

    t1.start();
    Thread.sleep(500);
    log.debug("t1 state: {}", t1.getState());
  }
}
