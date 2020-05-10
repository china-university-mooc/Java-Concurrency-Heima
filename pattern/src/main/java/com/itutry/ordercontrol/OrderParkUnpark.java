package com.itutry.ordercontrol;

import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * 用 park&unpark 实现线程顺序执行
 *
 * @author itutry
 * @create 2020-05-10_10:51
 */
@Slf4j(topic = "c.OrderWaitNotify")
public class OrderParkUnpark {

  public static void main(String[] args) {

    final Thread t1 = new Thread(() -> {
      LockSupport.park();
      log.debug("1");
    }, "t1");
    final Thread t2 = new Thread(() -> {
      log.debug("2");
      LockSupport.unpark(t1);
    }, "t2");

    t1.start();
    t2.start();
  }
}
