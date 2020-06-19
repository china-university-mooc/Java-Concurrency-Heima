package com.itutry.demo8;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CasLock {

  private AtomicInteger state = new AtomicInteger(0);

  public void lock() {
    while (true) {
      if (state.compareAndSet(0, 1)) {
        break;
      }
    }
  }

  public void unlock() {
    log.debug("unlock");
    state.compareAndSet(1, 0);
  }
}
