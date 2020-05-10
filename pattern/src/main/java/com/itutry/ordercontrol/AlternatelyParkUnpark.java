package com.itutry.ordercontrol;

import java.util.concurrent.locks.LockSupport;

/**
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。现在要求输出 abcabcabcabcabc,
 *
 * @author itutry
 * @create 2020-05-10_11:07
 */
public class AlternatelyParkUnpark {

  private static Thread t1;
  private static Thread t2;
  private static Thread t3;

  public static void main(String[] args) {
    final OutputRoom room = new OutputRoom(5);

    t1 = new Thread(() -> {
      room.output("a",t2);
    }, "t1");
    t2 = new Thread(() -> {
      room.output("b", t3);
    }, "t2");
    t3 = new Thread(() -> {
      room.output("c", t1);
    }, "t3");

    t1.start();
    t2.start();
    t3.start();

    LockSupport.unpark(t1);
  }

  private static class OutputRoom {
    private int loopNumber;

    public OutputRoom(int loopNumber) {
      this.loopNumber = loopNumber;
    }

    public void output(String text, Thread nextThread) {
      for (int i = 0; i < loopNumber; i++) {
        LockSupport.park();
        System.out.print(text);
        LockSupport.unpark(nextThread);
      }
    }
  }
}
