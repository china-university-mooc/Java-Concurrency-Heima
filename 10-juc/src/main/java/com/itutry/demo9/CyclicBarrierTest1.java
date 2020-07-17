package com.itutry.demo9;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CyclicBarrierTest1 {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(2);
    CyclicBarrier barrier = new CyclicBarrier(2, () -> {
      log.debug("task1 task2 finish...");
    });

    for (int i = 0; i < 3; i++) {
      service.submit(() -> {
        log.debug("task1 begin...");
        sleep(1);
        try {
          barrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
//      log.debug("task1 end...");
      });

      service.submit(() -> {
        log.debug("task2 begin...");
        sleep(2);
        try {
          barrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
//      log.debug("task2 end...");
      });
    }

    service.shutdown();
  }
}
