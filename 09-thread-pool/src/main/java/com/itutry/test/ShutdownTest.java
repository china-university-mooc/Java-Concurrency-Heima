package com.itutry.test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(2);

    Future<String> future1 = pool.submit(() -> {
      log.debug("begin...");
      Thread.sleep(1000);
      return "1";
    });

    Future<String> future2 = pool.submit(() -> {
      log.debug("begin...");
      Thread.sleep(1000);
      return "2";
    });

    Future<String> future3 = pool.submit(() -> {
      log.debug("begin...");
      Thread.sleep(1000);
      return "3";
    });

    log.debug("{}", pool.isShutdown());

    log.debug("showdown");
//    pool.shutdown();
//    pool.awaitTermination(3, TimeUnit.SECONDS);

    List<Runnable> runnables = pool.shutdownNow();
    log.debug(runnables.toString());
    log.debug("showdown did");
//    log.debug("{}", pool.isTerminated());

    log.debug(future1.get());
    log.debug(future2.get());
    log.debug(future3.get());
  }
}
