package com.ituyty.mutex;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itutry
 * @create 2020-04-28_21:36
 */
public interface Account {

  Integer getBalance();

  void withdraw(Integer amount);

  /**
   * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
   * 如果初始余额为 10000 那么正确的结果应当是 0
   */
  static void demo(Account account) {
    List<Thread> ts = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      ts.add(new Thread(() -> {
        account.withdraw(10);
      }));
    }

    long start = System.nanoTime();
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
