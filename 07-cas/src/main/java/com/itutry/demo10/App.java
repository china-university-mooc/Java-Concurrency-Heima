package com.itutry.demo10;

import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) {
    demo(new MyAccount(10000));
  }

  public static void demo(Account account) {
    List<Thread> ts = new ArrayList<Thread>();
    long start = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
      ts.add(new Thread(() -> {
        account.withdraw(10);
      }));
    }
    ts.forEach(Thread::start);
    ts.forEach(t -> {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    long end = System.nanoTime();
    System.out.println(account.getBalance() + " cost: " + (end - start) / 1000000 + "ms");
  }
}


