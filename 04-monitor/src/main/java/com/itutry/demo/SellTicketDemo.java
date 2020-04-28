package com.itutry.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-28_08:26
 */
@Slf4j(topic = "c.SellTicketDemo")
public class SellTicketDemo {

  public static void main(String[] args) throws InterruptedException {
    TicketWindow window = new TicketWindow(2000);

    List<Integer> counts = new Vector<>();
//    List<Integer> counts = new ArrayList<>();
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      final Thread t = new Thread(() -> {
        for (int j = 0; j < 50; j++) {
          final int count = window.sell(randomAmount());
          counts.add(count);
        }

      }, "t" + i);
      threads.add(t);
    }

    for (Thread t: threads) {
      t.start();
    }

    for (Thread t : threads) {
      t.join();
    }

    final int remain = window.getCount();
    final int sells = counts.stream().mapToInt(i -> i).sum();
    log.debug("总票数: {}", remain + sells);
  }


  static Random random = new Random();

  public static int randomAmount() {
    return random.nextInt(5) + 1;
  }


  private static class TicketWindow {

    private int count;

    private TicketWindow(int count) {
      this.count = count;
    }

    public int getCount() {
      return count;
    }

    public synchronized int sell(int amount) {
      if (count > amount) {
        count -= amount;
        return amount;
      } else {
        return 0;
      }
    }
  }
}
