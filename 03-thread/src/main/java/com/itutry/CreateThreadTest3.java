package com.itutry;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-23_12:03
 */
@Slf4j(topic = "c.CreateThreadTest3")
public class CreateThreadTest3 {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    final FutureTask<String> task = new FutureTask<>(() -> {
      log.debug("running");
      Thread.sleep(1000);
      return "success";
    });

    final Thread t = new Thread(task, "t1");
    t.start();

    log.debug("结果是：{}", task.get());
  }
}
