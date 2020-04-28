package com.itutry;

import java.util.ArrayList;

/**
 * @author itutry
 * @create 2020-04-27_23:23
 */
public class SafetyLocalTest {

  private static int THREAD_NUMBER = 2;
  private static int LOOP_NUMBER = 200;

  public static void main(String[] args) {
    final SafetyLocal test = new SafetyLocal();
    for (int i = 0; i < THREAD_NUMBER; i++) {
      new Thread(() -> {
        test.method1(LOOP_NUMBER);
      }, "Thread" + i).start();
    }
  }

  private static class SafetyLocal {

    public void method1(int loopNumber) {
      ArrayList<String> list = new ArrayList<>();
      for (int i = 0; i < loopNumber; i++) {
        method2(list);
        method3(list);
      }
    }

    private void method2(ArrayList<String> list) {
      list.add("1");
    }

    private void method3(ArrayList<String> list) {
      list.remove(0);
    }
  }
}
