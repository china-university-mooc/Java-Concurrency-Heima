package com.itutry.demo6;

import com.itutry.util.Sleeper;
import java.util.concurrent.Semaphore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SemaphoreTest {

  public static void main(String[] args) {
    Semaphore semaphore = new Semaphore(3);

    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          semaphore.acquire();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        try {
          log.debug("running...");
          Sleeper.sleep(1);
          log.debug("end...");
        } finally {
          semaphore.release();
        }
      }).start();
    }
  }
}
