package com.itutry.demo4;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.atomic.AtomicStampedReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ABAProblem2 {

  private static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 1);

  public static void main(String[] args) {
    log.debug("main start...");

    String prev = ref.getReference();
    int stamp = ref.getStamp();
    other();
    sleep(1);
    log.debug("change A->C {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));
  }

  private static void other() {
    new Thread(() -> {
      int stamp = ref.getStamp();
      log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B", stamp, stamp + 1));
    }, "t1").start();
    sleep(0.5);
    new Thread(() -> {
      int stamp = ref.getStamp();
      log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A", stamp, stamp + 1));
    }, "t2").start();
  }
}
