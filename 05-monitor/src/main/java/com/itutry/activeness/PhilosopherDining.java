package com.itutry.activeness;

import com.itutry.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * 死锁经典问题：哲学家就餐
 *
 * @author itutry
 * @create 2020-05-08_09:06
 */
public class PhilosopherDining {

  public static void main(String[] args) {
    final Chopstick c1 = new Chopstick("1");
    final Chopstick c2 = new Chopstick("2");
    final Chopstick c3 = new Chopstick("3");
    final Chopstick c4 = new Chopstick("4");
    final Chopstick c5 = new Chopstick("5");

    new Philosopher("苏格拉底", c1, c2).start();
    new Philosopher("柏拉图", c2, c3).start();
    new Philosopher("亚里士多德", c3, c4).start();
    new Philosopher("赫拉克利特", c4, c5).start();
    new Philosopher("阿基米德", c5, c1).start();
  }

  private static class Chopstick {
    private String name;

    public Chopstick(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "筷子{" + name + "}";
    }
  }

  @Slf4j(topic = "c.Philosopher")
  private static class Philosopher extends Thread {
    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name, Chopstick left,
        Chopstick right) {
      super(name);
      this.left = left;
      this.right = right;
    }

    @Override
    public void run() {
      while(true) {
        synchronized (left) {
          synchronized (right) {
            eat();
          }
        }
      }
    }

    private void eat() {
      log.debug("eating...");
      Sleeper.sleep(1);
    }
  }
}
