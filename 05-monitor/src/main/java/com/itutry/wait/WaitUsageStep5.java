package com.itutry.wait;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * step2: 两个线程都在等待条件，用wait-notifyAll解决，用while
 *
 * @author itutry
 * @create 2020-05-06_15:09
 */
@Slf4j(topic = "c.WaitUsageStep1")
public class WaitUsageStep5 {

  public static final Object room = new Object();
  public static boolean hasCigarette = false;
  public static boolean hasTakeout = false;

  public static void main(String[] args) {
    new Thread(() -> {
      synchronized (room) {
        log.debug("有烟没？[{}]", hasCigarette);
        while (!hasCigarette) {
          log.debug("没烟，先歇会儿");
          try {
            room.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        log.debug("有烟，可以开始干活了");
      }
    }, "小南").start();

    new Thread(() -> {
      synchronized (room) {
        log.debug("有外卖没？[{}]", hasTakeout);
        while (!hasTakeout) {
          log.debug("没外卖，先歇会儿");
          try {
            room.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

        log.debug("有外卖，可以开始干活了");
      }
    }, "小女").start();

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
        hasTakeout = true;
        log.debug("外卖到了噢");
        room.notifyAll();
      }
    }, "送外卖的").start();
  }
}
