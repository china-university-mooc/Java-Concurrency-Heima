package com.itutry.demo1;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ThreadPool")
public class ThreadPool {

  private final BlockingQueue<Runnable> taskQueue;

  private final HashSet<Worker> works = new HashSet<>();

  private int coreSize;

  private long timeout;

  private TimeUnit timeUnit;

  public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity) {
    this.coreSize = coreSize;
    this.timeout = timeout;
    this.timeUnit = timeUnit;
    taskQueue = new BlockingQueue<>(queueCapacity);
  }

  public void execute(Runnable task) {
    synchronized (works) {
      if (works.size() < coreSize) {
        Worker worker = new Worker(task);
        log.debug("新增 worker：{}, {}", worker, task);
        works.add(worker);
        worker.start();
      } else {
        taskQueue.put(task);
      }
    }
  }

  public class Worker extends Thread {

    private Runnable task;

    public Worker(Runnable task) {
      this.task = task;
    }

    @Override
    public void run() {
//      while (task != null || (task = taskQueue.take()) != null) {
      while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
        try {
          log.debug("正在执行...{}", task);
          task.run();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          task = null;
        }
      }

      synchronized (works) {
        log.debug("worker被移除：{}", this);
        works.remove(this);
      }
    }
  }
}
