package com.itutry;

import java.util.Arrays;
import java.util.concurrent.FutureTask;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

/**
 * @author itutry
 * @create 2020-04-22_23:22
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class MyBenchmark {

  static int[] ARRAY = new int[100000000];

  static {
    Arrays.fill(ARRAY, 1);
  }

  @Benchmark
  public int multi() throws Exception {
    int[] array = ARRAY;

    FutureTask<Integer> t1 = new FutureTask<>(() -> {
      int sum = 0;
      for (int i = 0; i < 25000000; i++) {
        sum += array[i];
      }
      return sum;
    });
    FutureTask<Integer> t2 = new FutureTask<>(() -> {
      int sum = 0;
      for (int i = 0; i < 25000000; i++) {
        sum += array[25000000 + i];
      }
      return sum;
    });
    FutureTask<Integer> t3 = new FutureTask<>(() -> {
      int sum = 0;
      for (int i = 0; i < 25000000; i++) {
        sum += array[50000000 + i];
      }
      return sum;
    });
    FutureTask<Integer> t4 = new FutureTask<>(() -> {
      int sum = 0;
      for (int i = 0; i < 25000000; i++) {
        sum += array[75000000 + i];
      }
      return sum;
    });
    new Thread(t1).start();
    new Thread(t2).start();
    new Thread(t3).start();
    new Thread(t4).start();
    return t1.get() + t2.get() + t3.get() + t4.get();
  }

  @Benchmark
  public int single() throws Exception {
    int[] array = ARRAY;
    FutureTask<Integer> t1 = new FutureTask<>(() -> {
      int sum = 0;
      for (int i = 0; i < 100000000; i++) {
        sum += array[i];
      }
      return sum;
    });
    new Thread(t1).start();
    return t1.get();
  }
}
