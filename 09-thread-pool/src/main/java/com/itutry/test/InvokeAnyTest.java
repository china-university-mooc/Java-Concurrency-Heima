package com.itutry.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvokeAnyTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(1);

     String result = pool.invokeAny(Arrays.asList(
        () -> {
          log.debug("begin...");
          Thread.sleep(1000);
          return "1";
        },
        () -> {
          log.debug("begin...");
          Thread.sleep(500);
          return "2";
        },
        () -> {
          log.debug("begin...");
          Thread.sleep(2000);
          return "3";
        }
    ));

    log.debug(result);
  }
}
