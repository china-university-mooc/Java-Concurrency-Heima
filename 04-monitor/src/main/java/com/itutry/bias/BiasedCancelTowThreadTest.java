package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 其它线程使用对象时撤销偏向锁
 *
 * @author itutry
 * @create 2020-04-28_12:17
 */
@Slf4j(topic = "c.BiasedCancelTowThread")
public class BiasedCancelTowThreadTest {

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) {
    final Dog d = new Dog();
    ClassLayout layout = ClassLayout.parseInstance(d);

    final Thread t1 = new Thread(() -> {
      log.debug(toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug(toSimple(layout.toPrintable()));
      }
      log.debug(toSimple(layout.toPrintable()));

      synchronized (BiasedCancelTowThreadTest.class) {
        BiasedCancelTowThreadTest.class.notify();
      }
      log.debug("------------------");
    }, "t1");
    t1.start();

    new Thread(() -> {
      synchronized (BiasedCancelTowThreadTest.class) {
        try {
          BiasedCancelTowThreadTest.class.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }


      log.debug(toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug(toSimple(layout.toPrintable()));
      }
      log.debug(toSimple(layout.toPrintable()));
    }, "t2").start();
  }
}
