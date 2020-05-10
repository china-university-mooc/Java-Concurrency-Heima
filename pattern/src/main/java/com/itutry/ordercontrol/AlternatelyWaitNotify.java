package com.itutry.ordercontrol;

/**
 * 线程 1 输出 a 5 次，线程 2 输出 b 5 次，线程 3 输出 c 5 次。现在要求输出 abcabcabcabcabc,
 *
 * @author itutry
 * @create 2020-05-10_11:07
 */
public class AlternatelyWaitNotify {

  public static void main(String[] args) {
    final OutputRoom room = new OutputRoom(1, 5);
    final Thread t1 = new Thread(() -> {
      room.output("a", 1, 2);
    }, "t1");
    final Thread t2 = new Thread(() -> {
      room.output("b", 2, 3);
    }, "t2");
    final Thread t3 = new Thread(() -> {
      room.output("c", 3, 1);
    }, "t3");

    t1.start();
    t2.start();
    t3.start();
  }

  private static class OutputRoom {
    private int order;
    private int loopNumber;

    public OutputRoom(int order, int loopNumber) {
      this.order = order;
      this.loopNumber = loopNumber;
    }

    public void output(String text, int waitOrder, int nextOrder) {
      for (int i = 0; i < loopNumber; i++) {
        synchronized (this) {
          while(order != waitOrder) {
            try {
              wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.print(text);

          order = nextOrder;
          notifyAll();
        }
      }
    }
  }
}
