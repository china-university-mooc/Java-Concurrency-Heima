package com.itutry.demo4;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ABAProblem {

  private static AtomicReference<String> ref = new AtomicReference<>("A");

  public static void main(String[] args) {
    log.debug("main start...");

    String prev = ref.get();
    other();
    sleep(1);
    log.debug("change A->C {}", ref.compareAndSet(prev, "C"));
  }

  private static void other() {
    new Thread(() -> {
      log.debug("change A->B {}", ref.compareAndSet(ref.get(), "B"));
    }, "t1").start();
    sleep(0.5);
    new Thread(() -> {
      log.debug("change B->A {}", ref.compareAndSet(ref.get(), "A"));
    }, "t2").start();
  }
}
