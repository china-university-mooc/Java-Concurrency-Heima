package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 调用 hashcode 时撤销偏向锁
 *
 * @author itutry
 * @create 2020-04-28_12:13
 */
@Slf4j(topic = "c.BiasedCancelHashcodeTest")
public class BiasedCancelHashcodeTest {

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) {
    final Dog d = new Dog();
    ClassLayout layout = ClassLayout.parseInstance(d);

    new Thread(() -> {
      log.debug(toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug(toSimple(layout.toPrintable()));
      }
      log.debug(toSimple(layout.toPrintable()));

      log.debug("调用hashcode: {}", d.hashCode());

      log.debug(toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug(toSimple(layout.toPrintable()));
      }
      log.debug(toSimple(layout.toPrintable()));
    }, "t1").start();
  }
}
