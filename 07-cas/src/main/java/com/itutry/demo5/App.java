package com.itutry.demo5;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.atomic.AtomicMarkableReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    GarbageBag bag = new GarbageBag("装满了垃圾");
    AtomicMarkableReference<GarbageBag> ref = new AtomicMarkableReference<>(bag, true);

    log.debug("主线程 start...");
    GarbageBag prev = ref.getReference();
    log.debug(prev.toString());

    new Thread(() -> {
      log.debug("打扫卫生的线程 start...");
      bag.setDesc("空垃圾袋");
      while(!ref.compareAndSet(bag, bag, true, false));
      log.debug(bag.toString());
    }).start();

    sleep(1);
    log.debug("主线程想换一只新垃圾袋?");
    boolean success = ref.compareAndSet(prev, new GarbageBag("空垃圾袋"), true, false);
    log.debug("换了吗？" + success);
    log.debug(ref.getReference().toString());
  }
}
