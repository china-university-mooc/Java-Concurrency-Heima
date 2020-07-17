package com.itutry.demo1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义的不可重入锁
 */
public class MyLock implements Lock {

  class MySync extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
      if (compareAndSetState(0, 1)) {
        setExclusiveOwnerThread(Thread.currentThread());
        return true;
      }
      return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
//      if (isHeldExclusively()) {
//        setExclusiveOwnerThread(null);
//        setState(0);
//        return true;
//      }
//      return false;

      setExclusiveOwnerThread(null);
      setState(0);
      return true;
    }

    @Override
    protected boolean isHeldExclusively() {
//      return getExclusiveOwnerThread().equals(Thread.currentThread());
      return getState() == 1;
    }

    public Condition newCondition() {
      return new ConditionObject();
    }
  }

  private MySync sync = new MySync();

  public void lock() {
    sync.acquire(1);
  }

  public void lockInterruptibly() throws InterruptedException {
    sync.acquireInterruptibly(1);
  }

  public boolean tryLock() {
    return sync.tryAcquire(1);
  }

  public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
    return sync.tryAcquireNanos(1, unit.toNanos(time));
  }

  public void unlock() {
    sync.release(1);
  }

  public Condition newCondition() {
    return sync.newCondition();
  }
}
