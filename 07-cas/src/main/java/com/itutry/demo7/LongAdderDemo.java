package com.itutry.demo7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LongAdderDemo {

  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
      demo(AtomicLong::new, AtomicLong::incrementAndGet);
    }
    for (int i = 0; i < 5; i++) {
      demo(LongAdder::new, LongAdder::increment);
    }
  }

  private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
    T adder = adderSupplier.get();

    long start = System.nanoTime();
    List<Thread> ts = new ArrayList<>(); // 4 个线程，每人累加 50 万
    for (int i = 0; i < 4; i++) {
      ts.add(new Thread(() -> {
        for (int j = 0; j < 500000; j++) {
          action.accept(adder);
        }
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
    System.out.println(adder + " cost:" + (end - start) / 1000000);
  }
}
