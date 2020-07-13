package com.itutry.demo2;

import java.util.Queue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "App")
public class App {

  public static void main(String[] args) throws InterruptedException {
    test2();
  }

  private static void test2() throws InterruptedException {
    // 决绝策略


    RejectPolicy<Runnable> rejectPolicy = (queue, task) -> {
      // 1.死等
//      queue.put(task);

      // 2.带超时等
//      queue.offer(task, 1500, TimeUnit.MILLISECONDS);

      // 3.放弃任务
//      log.debug("放弃任务：{}", task);

      // 4.抛异常
//      throw new RuntimeException("任务执行失败：" + task);

      // 5.调用者自己执行
      task.run();
    };

    ThreadPool threadPool = new ThreadPool(1, 1000, TimeUnit.MILLISECONDS, 1, rejectPolicy);
    for (int i = 0; i < 4; i++) {
      int j = i;
      threadPool.execute(() -> {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.debug("{}", j);
      });
    }
  }
}
