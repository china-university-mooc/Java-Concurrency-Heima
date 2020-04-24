package com.ituyty.chronous.syn;

import static com.ituyty.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_15:10
 */
@Slf4j(topic = "c.SynJoin")
public class SynJoin {

  private static int result = 0;

  public static void main(String[] args) throws InterruptedException {
    log.debug("开始");

    final Thread t1 = new Thread(() -> {
      log.debug("开始");
      sleep(1);
      log.debug("结束");
      result = 10;
    }, "t1");
    t1.start();

    t1.join();
    log.debug("结果为：{}", result);
  }
}
