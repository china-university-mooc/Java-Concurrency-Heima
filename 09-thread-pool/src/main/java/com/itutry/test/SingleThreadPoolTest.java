package com.itutry.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleThreadPoolTest {

  public static void main(String[] args) {
    ThreadFactory threadFactory = new ThreadFactory() {
      private AtomicInteger threadNumber = new AtomicInteger(1);

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "mypool-t" + threadNumber.getAndIncrement());
      }
    };
//    ExecutorService pool = Executors.newFixedThreadPool(1, threadFactory);


    ExecutorService pool = Executors.newSingleThreadExecutor(threadFactory);

    pool.execute(() -> {
      log.debug("1");
//      int i = 1 / 0;
    });
    pool.execute(() -> log.debug("2"));
    pool.execute(() -> log.debug("3"));

    ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) pool;
    poolExecutor.setCorePoolSize(2);

    pool.execute(() -> {
      log.debug("1");
//      int i = 1 / 0;
    });
    pool.execute(() -> log.debug("2"));
    pool.execute(() -> log.debug("3"));
  }
}
