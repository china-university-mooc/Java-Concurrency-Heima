package com.itutry.demo7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    Integer result = forkJoinPool.invoke(new MyTask(5));
    System.out.println(result);
  }
}

@Slf4j
class MyTask extends RecursiveTask<Integer> {

  private int n;

  public MyTask(int n) {
    this.n = n;
  }

  @Override
  protected Integer compute() {
    if (n == 1) {
      log.debug("join() 1");
      return 1;
    }

    MyTask task = new MyTask(n - 1);
    task.fork();
    log.debug("fork() {} + {}", n, task);

    int result = n + task.join();
    log.debug("join() {} + {} = {}", n, task, result);
    return result;
  }

  @Override
  public String toString() {
    return "{" + n + "}";
  }
}
