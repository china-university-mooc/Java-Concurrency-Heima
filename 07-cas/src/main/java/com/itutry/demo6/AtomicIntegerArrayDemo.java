package com.itutry.demo6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicIntegerArrayDemo {

  public static void main(String[] args) {
    demo(() -> new int[10],
        array -> array.length,
        (array, index) -> array[index]++,
        array -> System.out.println(Arrays.toString(array)));

    demo(() -> new AtomicIntegerArray(10),
        AtomicIntegerArray::length,
        AtomicIntegerArray::incrementAndGet,
        System.out::println);
  }

  private static <T> void demo(Supplier<T> arraySupplier,
      Function<T, Integer> lengthFun,
      BiConsumer<T, Integer> putConsumer,
      Consumer<T> printConsumer) {
    ArrayList<Thread> ts = new ArrayList<>();
    T array = arraySupplier.get();
    int length = lengthFun.apply(array);
    for (int i = 0; i < length; i++) {
      ts.add(new Thread(() -> {
        for (int j = 0; j < 10000; j++) {
          putConsumer.accept(array, j % length);
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
    printConsumer.accept(array);
  }
}
