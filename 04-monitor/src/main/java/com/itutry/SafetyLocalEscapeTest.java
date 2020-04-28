package com.itutry;

import java.util.ArrayList;

/**
 * @author itutry
 * @create 2020-04-27_23:27
 */
public class SafetyLocalEscapeTest {

  private static int THREAD_NUMBER = 2;
  private static int LOOP_NUMBER = 200;

  public static void main(String[] args) {
    final SafetyLocalEscape test = new SafetyLocalEscape();
    for (int i = 0; i < THREAD_NUMBER; i++) {
      new Thread(() -> {
        test.method1(LOOP_NUMBER);
      }, "Thread" + i).start();
    }
  }

  private static class SafetyLocalEscape extends SafetyLocal {

    @Override
    public void method2(ArrayList<String> list) {
      new Thread(() -> {
        list.add("1");
      }).start();
    }

    @Override
    public void method3(ArrayList<String> list) {
      new Thread(() -> {
        list.remove(0);
      }).start();
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

    public void method2(ArrayList<String> list) {
      list.add("1");
    }

    public void method3(ArrayList<String> list) {
      list.remove(0);
    }
  }
}
