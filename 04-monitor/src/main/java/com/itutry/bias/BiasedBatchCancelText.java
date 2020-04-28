package com.itutry.bias;

import static com.itutry.util.ClassLayoutUtil.toSimple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 撤销阈值达到40，批量锁撤销
 *
 * @author itutry
 * @create 2020-04-28_18:09
 */
@Slf4j(topic = "c.BiasedBatchCancelText")
public class BiasedBatchCancelText {

  private static Thread t1, t2, t3;

  // 添加虚拟机参数 -XX:BiasedLockingStartupDelay=0 禁用偏向延迟
  public static void main(String[] args) throws InterruptedException {
    List<Dog> dogs = new ArrayList<>();

    t1 = new Thread(() -> {
      for (int i = 0; i < 40; i++) {
        final Dog dog = new Dog();
        dogs.add(dog);
        synchronized (dog) {
          log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        }
      }

      LockSupport.unpark(t2);
    }, "t1");
    t1.start();

    t2 = new Thread(() -> {
      LockSupport.park();

      log.debug("==============================");
      for (int i = 0; i < dogs.size(); i++) {
        final Dog dog = dogs.get(i);
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        synchronized (dog) {
          log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        }
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
      }

      LockSupport.unpark(t3);
    }, "t2");
    t2.start();

    t3 = new Thread(() -> {
      LockSupport.park();

      log.debug("==============================");
      for (int i = 0; i < dogs.size(); i++) {
        final Dog dog = dogs.get(i);
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        synchronized (dog) {
          log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
        }
        log.debug(i + "\t" + toSimple(ClassLayout.parseInstance(dog).toPrintable()));
      }
    }, "t3");
    t3.start();

    t3.join();

    final Dog dog = new Dog();
    log.debug(toSimple(ClassLayout.parseInstance(dog).toPrintable()));
  }
}
