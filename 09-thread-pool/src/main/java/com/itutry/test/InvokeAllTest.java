package com.itutry.test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvokeAllTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService pool = Executors.newFixedThreadPool(2);

    List<Future<String>> futures = pool.invokeAll(Arrays.asList(
        () -> {
          log.debug("begin...");
          Thread.sleep(1000);
          return "1";
        },
        () -> {
          log.debug("begin...");
          Thread.sleep(1000);
          return "2";
        },
        () -> {
          log.debug("begin...");
          Thread.sleep(1000);
          return "3";
        }
    ));

    futures.forEach(f -> {
      try {
        log.debug(f.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });
  }
}
