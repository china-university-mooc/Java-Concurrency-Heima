package com.itutry.reentrantlock;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

/**
 * 用多个条件变量解决虚假唤醒问题
 *
 * @author itutry
 * @create 2020-05-06_15:09
 */
@Slf4j(topic = "c.WaitUsageStep1")
public class ConditionDemo {

  public static final Object room = new Object();
  public static boolean hasCigarette = false;
  public static boolean hasTakeout = false;

  public static ReentrantLock lock = new ReentrantLock();
  public static Condition cigaretteCondition = lock.newCondition();
  public static Condition takeoutCondition = lock.newCondition();

  public static void main(String[] args) {
    new Thread(() -> {
      lock.lock();
      try {
        log.debug("有烟没？[{}]", hasCigarette);
        if (!hasCigarette) {
          log.debug("没烟，先歇会儿");
          cigaretteCondition.await();
        }

        log.debug("有烟，可以开始干活了");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "小南").start();

    new Thread(() -> {
      lock.lock();
      try {
        log.debug("有外卖没？[{}]", hasTakeout);
        if (!hasTakeout) {
          log.debug("没外卖，先歇会儿");
          takeoutCondition.await();
        }

        log.debug("有外卖，可以开始干活了");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }, "小女").start();

    for (int i = 0; i < 5; i++) {
      new Thread(() -> {
        lock.lock();
        try {
          log.debug("开始干活了");
        } finally {
          lock.unlock();
        }
      }, "其他人").start();
    }

    sleep(1);
    new Thread(() -> {
      lock.lock();
      try {
        hasTakeout = true;
        log.debug("外卖到了噢");
        takeoutCondition.signalAll();
      } finally {
        lock.unlock();
      }
    }, "送外卖的").start();
    new Thread(() -> {
      lock.lock();
      try {
        hasCigarette = true;
        log.debug("烟到了噢");
        cigaretteCondition.signalAll();
      } finally {
        lock.unlock();
      }
    }, "送烟的").start();
  }
}
