package com.itutry.wait;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * step1: 条件不满足需要等待，用sleep来解决，等待时，其它线程被阻塞
 *
 * @author itutry
 * @create 2020-05-06_15:09
 */
@Slf4j(topic = "c.WaitUsageStep1")
public class WaitUsageStep1 {

  public static final Object room = new Object();
  public static boolean hasCigarette = false;

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (room) {
        log.debug("有烟没？[{}]", hasCigarette);
        if (!hasCigarette) {
          log.debug("没烟，先歇会儿");
          sleep(2);
        }

        log.debug("有烟没？[{}]", hasCigarette);
        if (hasCigarette) {
          log.debug("有烟，可以开始干活了");
        }
      }
    }, "小南").start();

    for (int i = 0; i < 5; i++) {
      new Thread(() -> {
        synchronized (room) {
          log.debug("开始干活了");
        }
      }, "其他人").start();
    }

    sleep(1);
    new Thread(() -> {
      // 不能加 synchronized (room);
      hasCigarette = true;
      log.debug("烟到了");
    }, "送烟的").start();
  }
}
