package com.itutry.demo7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App2 {

  public static void main(String[] args) {
    ForkJoinPool forkJoinPool = new ForkJoinPool(2);
    Integer result = forkJoinPool.invoke(new MyTask2(1,5));
    System.out.println(result);
  }
}

@Slf4j
class MyTask2 extends RecursiveTask<Integer> {

  private int left;
  private int right;

  public MyTask2(int left, int right) {
    this.left = left;
    this.right = right;
  }

  @Override
  protected Integer compute() {
    if (left == right) {
      log.debug("join {}", left);
      return left;
    }
    if (left + 1 == right) {
      log.debug("join {} + {} = {}", left, right, left + right);
      return left + right;
    }

    int mid = (left + right) / 2;

    MyTask2 task1 = new MyTask2(left, mid);
    task1.fork();
    MyTask2 task2 = new MyTask2(mid + 1, right);
    task2.fork();
    log.debug("fork() {} + {}", task1, task2);

    int result = task1.join() + task2.join();
    log.debug("join() {} + {} = {}", task1, task2, result);
    return result;
  }

  @Override
  public String toString() {
    return "{" + left + ", " + right + "}";
  }
}
