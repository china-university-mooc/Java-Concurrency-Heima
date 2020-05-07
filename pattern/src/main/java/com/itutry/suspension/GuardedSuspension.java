package com.itutry.suspension;

import static com.itutry.suspension.Downloader.download;

import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 同步模式之保护性暂停, 以GuardedObject作为桥梁，将一个线程产生的结果传递给另一个线程。
 *
 * @author itutry
 * @create 2020-05-06_15:50
 */
@Slf4j(topic = "c.GuardedSuspension")
public class GuardedSuspension {

  public static void main(String[] args) {
    final GuardedObject guardedObject = new GuardedObject();

    new Thread(() -> {
      try {
        final List<String> list = download();
        log.debug("download complete");
        guardedObject.complete(list);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }, "下载线程").start();

    log.debug("waiting...");
    final List<String> list = (List<String>) guardedObject.get();
    log.debug("get result: {} lines", list.size());
  }


  public static class GuardedObject {
    private Object result;
    private final Object lock = new Object();

    public Object get() {
      synchronized (lock) {
        while(result == null) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
      return result;
    }

    public void complete(Object result) {
      synchronized (lock) {
        this.result = result;
        lock.notifyAll();
      }
    }
  }
}
