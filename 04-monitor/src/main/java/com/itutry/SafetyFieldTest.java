package com.itutry;

import java.util.ArrayList;

/**
 * @author itutry
 * @create 2020-04-27_23:14
 */
public class SafetyFieldTest {

  private static int THREAD_NUMBER = 2;
  private static int LOOP_NUMBER = 200;

  public static void main(String[] args) {
    final SafetyField test = new SafetyField();
    for (int i = 0; i < THREAD_NUMBER; i++) {
      new Thread(() -> {
          test.method1(LOOP_NUMBER);
      }, "Thread" + i).start();
    }
  }

  private static class SafetyField {
    ArrayList<String> list = new ArrayList<>();

    public void method1(int loopNumber) {
      for (int i = 0; i < loopNumber; i++) {
        method2();
        method3();
      }
    }

    private void method2() {
      list.add("1");
    }

    private void method3() {
      list.remove(0);
    }
  }
}
