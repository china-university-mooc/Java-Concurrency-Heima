package com.itutry.happensbefore;

/**
 * @author itutry
 * @create 2020-05-20_09:25
 */
public class HbStop {

  static int x;

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      x = 10;
    }, "t1");
    t1.start();

    t1.join();
    System.out.println(x);
  }
}
