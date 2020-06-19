package com.itutry.demo8;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    CasLock lock = new CasLock();

    new Thread(() -> {
      log.debug("begin..."); lock.lock();
      try {
        log.debug("lock...");
        sleep(1); } finally {
        lock.unlock(); }
    }).start();

    new Thread(() -> { log.debug("begin..."); lock.lock();
      try {
        log.debug("lock..."); } finally {
        lock.unlock(); }
    }).start();
  }
}
