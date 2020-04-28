package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 撤销阈值超过20，批量重定向
 *
 * @author itutry
 * @create 2020-04-28_17:50
 */
@Slf4j(topic = "c.BiasedBachRebiasTest")
public class BiasedBachRebiasTest {

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) {
    List<Dog> dogs = new ArrayList<>();

    new Thread(() -> {
      for (int i = 0; i < 30; i++) {
        final Dog dog = new Dog();
        dogs.add(dog);
        synchronized (dog) {
          log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        }
      }

      synchronized (dogs) {
        dogs.notify();
      }
    }, "t1").start();

    new Thread(() -> {
      synchronized (dogs) {
        try {
          dogs.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      log.debug("==============================");
      for (int i = 0; i < dogs.size(); i++) {
        final Dog dog = dogs.get(i);
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        synchronized (dog) {
          log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        }
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
      }
    }, "t2").start();
  }
}
