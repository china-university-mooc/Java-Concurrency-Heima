package com.itutry.activeness;

import com.itutry.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 活锁：互相改变对方的结束条件，导致谁也无法结束
 *
 * @author itutry
 * @create 2020-05-08_09:17
 */
@Slf4j(topic = "c.LiveLockDemo")
public class LiveLockDemo {
  private static int count = 0;
  private static Object lock = new Object();

  public static void main(String[] args) {

    new Thread(() -> {
      while(count < 10) {
        synchronized (lock) {
          count++;
          log.debug("add：{}", count);
        }
        Sleeper.sleep(0.5);
      }
    }, "t1").start();

    new Thread(() -> {
      while(count > -10) {
        synchronized (lock) {
          count--;
          log.debug("sub：{}", count);
        }
        Sleeper.sleep(0.5);
      }
    }, "t2").start();
  }
}
