package com.ituyty.plan;

import static com.ituyty.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_15:26
 */
@Slf4j(topic = "c.BoilTeaJoin")
public class BoilTeaJoin {

  public static void main(String[] args) {
    final Thread t1 = new Thread(() -> {
      log.debug("洗水壶");
      sleep(1);

      log.debug("烧开水");
      sleep(5);
    }, "老王");

    final Thread t2 = new Thread(() -> {
      log.debug("洗茶壶");
      sleep(1);

      log.debug("洗茶杯");
      sleep(2);

      log.debug("拿茶叶");
      sleep(1);

      try {
        t1.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.debug("泡茶");
    }, "小王");

    t1.start();
    t2.start();
  }
}
