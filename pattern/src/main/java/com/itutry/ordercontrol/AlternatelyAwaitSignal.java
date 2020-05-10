package com.itutry.ordercontrol;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。现在要求输出 abcabcabcabcabc,
 *
 * @author itutry
 * @create 2020-05-10_11:07
 */
public class AlternatelyAwaitSignal {

  public static void main(String[] args) {
    final OutputRoom room = new OutputRoom(5);
    final Condition aWaitSet = room.newCondition();
    final Condition bWaitSet = room.newCondition();
    final Condition cWaitSet = room.newCondition();

    final Thread t1 = new Thread(() -> {
      room.output("a", aWaitSet, bWaitSet);
    }, "t1");
    final Thread t2 = new Thread(() -> {
      room.output("b", bWaitSet, cWaitSet);
    }, "t2");
    final Thread t3 = new Thread(() -> {
      room.output("c", cWaitSet, aWaitSet);
    }, "t3");

    t1.start();
    t2.start();
    t3.start();
    room.notify(aWaitSet);
  }

  private static class OutputRoom extends ReentrantLock {
    private int loopNumber;

    public OutputRoom(int loopNumber) {
      this.loopNumber = loopNumber;
    }

    public void output(String text, Condition waitSet, Condition nextWaitSet) {
      for (int i = 0; i < loopNumber; i++) {
        this.lock();
        try {
          waitSet.await();
          System.out.print(text);
          nextWaitSet.signalAll();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          this.unlock();
        }
      }
    }

    public void notify(Condition waitSet) {
      this.lock();
      try {
        waitSet.signalAll();
      } finally {
        this.unlock();
      }
    }
  }
}
