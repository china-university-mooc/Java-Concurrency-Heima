package com.itutry.demo2;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "BlockingQueue")
public class BlockingQueue<T> {

  private Deque<T> queue = new ArrayDeque<T>();

  private ReentrantLock lock = new ReentrantLock();

  private Condition fullWaitSet = lock.newCondition();

  private Condition emptyWaitSet = lock.newCondition();

  private int capacity;

  public BlockingQueue(int capacity) {
    this.capacity = capacity;
  }

  public T poll(long timeout, TimeUnit unit) {
    lock.lock();
    try {
      long nanos = unit.toNanos(timeout);
      while (queue.isEmpty()) {
        try {
          if (nanos <= 0) {
            return null;
          }
          nanos = emptyWaitSet.awaitNanos(nanos);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      T e = queue.removeFirst();
      fullWaitSet.signalAll();
      return e;
    } finally {
      lock.unlock();
    }
  }

  public T take() {
    lock.lock();
    try {
      while (queue.isEmpty()) {
        try {
          emptyWaitSet.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      T e = queue.removeFirst();
      fullWaitSet.signalAll();
      return e;
    } finally {
      lock.unlock();
    }
  }

  public void put(T element) {
    lock.lock();
    try {
      while (queue.size() >= capacity) {
        try {
          log.debug("等待加入任务队列...{}", element);
          fullWaitSet.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      queue.addLast(element);
      log.debug("加入任务队列：{}", element);
      emptyWaitSet.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public boolean offer(T element, long timeout, TimeUnit unit) {
    lock.lock();
    try {
      long nanos = unit.toNanos(timeout);
      while (queue.size() >= capacity) {
        try {
          if (nanos <= 0) {
            return false;
          }
          log.debug("等待加入任务队列...{}", element);
          nanos = fullWaitSet.awaitNanos(nanos);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      queue.addLast(element);
      log.debug("加入任务队列：{}", element);
      emptyWaitSet.signalAll();
      return true;
    } finally {
      lock.unlock();
    }
  }

  public int size() {
    lock.lock();
    try {
      return queue.size();
    } finally {
      lock.unlock();
    }
  }

  public void tryPut(RejectPolicy<T> rejectPolicy, T element) {
    lock.lock();
    try {
      if (queue.size() == capacity) {
        rejectPolicy.reject(this, element);
      } else {
        queue.addLast(element);
        log.debug("加入任务队列：{}", element);
        emptyWaitSet.signalAll();
      }
    } finally {
      lock.unlock();
    }
  }
}
