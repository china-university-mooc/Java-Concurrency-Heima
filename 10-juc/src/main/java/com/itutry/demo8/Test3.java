package com.itutry.demo8;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * 模拟王者荣耀玩家加载过程
 */
@Slf4j
public class Test3 {

  public static void main(String[] args) {
    int num = 10;
    ExecutorService pool = Executors.newFixedThreadPool(num);
    CountDownLatch latch = new CountDownLatch(num);

    String[] arr = new String[num];
    Random random = new Random();
    for (int j = 0; j < num; j++) {
      int k = j;
      pool.submit(() -> {
        for (int i = 0; i <= 100; i++) {
          arr[k] = "t" + k + "(" + i + "%)";
          try {
            Thread.sleep(random.nextInt(100));
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.print("\r" + Arrays.toString(arr));
        }
        latch.countDown();
      });
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("\n游戏开始");
    pool.shutdown();
  }
}
