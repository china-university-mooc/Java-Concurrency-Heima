package com.itutry.demo2;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

  public static void main(String[] args) {
    AtomicInteger i = new AtomicInteger(0);

    //增减
    System.out.println(i.incrementAndGet());
    System.out.println(i.decrementAndGet());
    System.out.println(i.addAndGet(5));

    //更新
    System.out.println(i.updateAndGet(p -> p * 2));
    System.out.println(i.accumulateAndGet(5, (p, x) -> p / x));
    System.out.println(i.getAndSet(20));

    System.out.println(i.get());
  }
}
