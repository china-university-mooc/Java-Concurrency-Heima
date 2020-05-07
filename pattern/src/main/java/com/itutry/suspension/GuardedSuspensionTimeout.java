package com.itutry.suspension;

import static com.itutry.util.Sleeper.sleep;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步模式之保护性暂停, 在GuardedObject上等待结果时可以超时。
 *
 * @author itutry
 * @create 2020-05-06_15:50
 */
@Slf4j(topic = "c.GuardedSuspension")
public class GuardedSuspensionTimeout {

  public static void main(String[] args) {
    final GuardedObject guardedObject = new GuardedObject();

    new Thread(() -> {
      sleep(1);
      guardedObject.complete(null);
      sleep(1);
      guardedObject.complete(Arrays.asList("a", "b", "c"));
    }).start();

    Object result = guardedObject.get(1500);
    if (result != null) {
      log.debug("get result: {}", result);
    }
  }

  public static class GuardedObject {

    private Object result;
    private final Object lock = new Object();

    public Object get(long timeout) {
      synchronized (lock) {
        final long begin = System.currentTimeMillis();
        long total = 0;

        while (result == null && total < timeout) {
          try {
            lock.wait(timeout - total);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          total = System.currentTimeMillis() - begin;
          log.debug("timePassed: {}, object is null? {}", total, result == null);
        }
      }
      return result;
    }

    public void complete(Object result) {
      synchronized (lock) {
        this.result = result;
        log.debug("notify...");
        lock.notifyAll();
      }
    }
  }
}
