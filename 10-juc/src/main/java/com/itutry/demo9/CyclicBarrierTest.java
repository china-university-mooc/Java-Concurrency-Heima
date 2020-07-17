package com.itutry.demo9;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CyclicBarrierTest {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(5);

    for (int i = 0; i < 3; i++) {
      CountDownLatch latch = new CountDownLatch(2);
      service.submit(() -> {
        log.debug("task1 start...");
        sleep(1);
        latch.countDown();
      });

      service.submit(() -> {
        log.debug("task2 start...");
        sleep(2);
        latch.countDown();
      });

      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      log.debug("task1 task2 finish...");
    }
    service.shutdown();
  }
}
