package com.itutry.visibility;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * 用 synchronized 解决可见性问题
 *
 * @author itutry
 * @create 2020-05-19_22:22
 */
@Slf4j(topic = "c.VisibilitySolutionSynchronized")
public class VisibilitySolutionSynchronized {

  private static boolean run = true;
  private static Object lock = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      while (run) {
        synchronized (lock) {
          if (!run) {
            break;
          }
        }
      }
      log.debug("循环退出");
    }, "t1").start();

    sleep(1);
    run = false;
    log.debug("停止线程");
  }
}
