package com.itutry.visibility;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * 可见性问题示例，退不出的循环
 *
 * @author itutry
 * @create 2020-05-19_22:15
 */
@Slf4j(topic = "c.VisibilityProblemDemo")
public class VisibilityProblem {

  private static boolean run = true;

  public static void main(String[] args) {
    new Thread(() -> {
      while (run) {

      }
      log.debug("循环退出");
    }, "t1").start();

    sleep(1);
    run = false;
    log.debug("停止线程");
  }
}
