package com.itutry.wait;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * step2: 条件不满足需要等待，用wait-notify解决
 *
 * @author itutry
 * @create 2020-05-06_15:09
 */
@Slf4j(topic = "c.WaitUsageStep1")
public class WaitUsageStep2 {

  public static final Object room = new Object();
  public static boolean hasCigarette = false;

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (room) {
        log.debug("有烟没？[{}]", hasCigarette);
        if (!hasCigarette) {
          log.debug("没烟，先歇会儿");
          try {
            room.wait(2000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
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
      synchronized (room) {
        hasCigarette = true;
        log.debug("烟到了");
        room.notify();
      }
    }, "送烟的").start();
  }
}
