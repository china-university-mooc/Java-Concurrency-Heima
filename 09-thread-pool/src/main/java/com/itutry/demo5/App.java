package com.itutry.demo5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(2);

    Future<Boolean> f = pool.submit(() -> {
      log.debug("task");
//      int i = 1 / 0;
      return true;
    });

    log.debug("result: {}", f.get());
  }
}
