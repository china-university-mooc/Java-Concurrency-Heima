package com.itutry.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubmitTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(2);

    log.debug("start...");
    Future<String> future = pool.submit(() -> {
      Thread.sleep(1000);
      return "ok";
    });

    log.debug(future.get());
  }
}
