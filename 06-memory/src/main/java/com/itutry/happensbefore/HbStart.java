package com.itutry.happensbefore;

/**
 * @author itutry
 * @create 2020-05-20_09:24
 */
public class HbStart {

  static int x;

  public static void main(String[] args) {
    x = 10;
    new Thread(() -> {
      System.out.println(x);
    }, "t2").start();
  }
}
