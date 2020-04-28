package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 查看偏向锁的对象头信息
 *
 * @author itutry
 * @create 2020-04-28_11:35
 */
@Slf4j(topic = "c.BiasedLockTest")
public class BiasedLockTest {

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) {
    final Dog d = new Dog();
    ClassLayout layout = ClassLayout.parseInstance(d);

    new Thread(() -> {
      log.debug("synchronized前：{}", toSimple(layout.toPrintable()));
      synchronized (d) {
        log.debug("synchronized中：{}", toSimple(layout.toPrintable()));
      }
      log.debug("synchronized后：{}", toSimple(layout.toPrintable()));
    }, "t1").start();
  }
}
