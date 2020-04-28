package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;
import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 调用 wait/notify 时撤销偏向锁
 *
 * author itutry
 *
 * @create 2020-04-28_12:25
 */
@Slf4j(topic = "c.BiasedCancelWaitTest")
public class BiasedCancelWaitTest {

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) {
    Dog d = new Dog();
    ClassLayout layout = ClassLayout.parseInstance(d);

    Thread t1 = new Thread(() -> {
      log.debug(toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug(toSimple(layout.toPrintable()));
        try {
          d.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      log.debug(toSimple(layout.toPrintable()));
    }, "t1");
    t1.start();

    new Thread(() -> {
      sleep(2);
      synchronized (d) {
        log.debug("notify");
        d.notify();
      }
    }, "t2").start();
  }
}
