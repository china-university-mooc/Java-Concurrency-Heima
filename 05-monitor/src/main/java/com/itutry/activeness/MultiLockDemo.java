package com.itutry.activeness;

import com.itutry.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 互补相干的事情，用多把锁，能增加并发度
 *
 * @author itutry
 * @create 2020-05-08_08:47
 */
public class MultiLockDemo {

  public static void main(String[] args) {
    final BigRoom bigRoom = new BigRoom();
    new Thread(() -> {
      bigRoom.study();
    }, "小南").start();

    new Thread(() -> {
      bigRoom.sleep();
    }, "小女").start();
  }

  @Slf4j(topic = "c.BigRoom")
  private static class BigRoom {
    private final Object sleepRoom = new Object();
    private final Object studyRoom = new Object();

    public void sleep() {
      synchronized (sleepRoom) {
        log.debug("睡觉两小时");
        Sleeper.sleep(2);
      }
    }

    public void study() {
      synchronized (studyRoom) {
        log.debug("学习一小时");
        Sleeper.sleep(1);
      }
    }
  }
}
