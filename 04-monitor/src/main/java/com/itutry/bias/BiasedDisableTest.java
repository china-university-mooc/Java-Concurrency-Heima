package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;
import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author itutry
 * @create 2020-04-28_12:10
 */
@Slf4j(topic = "c.BiasedDisableTest")
public class BiasedDisableTest {

  // 添加虚拟机参数 -XX:-UseBiasedLocking 禁用偏向锁
  public static void main(String[] args) {
    sleep(4);

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
