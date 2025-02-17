# 并发编程模式

## 两阶段终止模式

- 利用isInterrupted
- 利用volatile的停止标记

## 同步模式之保护性暂停

以GuardedObject作为桥梁，将一个线程产生的结果传递给另一个线程

## 同步模式之Balking模式

一件事情如果已经做了，那么本线程无需再做，直接返回

## 同步模式之顺序控制

### 线程顺序执行

- wait&notify实现
- ReentrantLock的await&signal实现
- park&unpark实现

### 线程交替执行：
- wait&notify实现
- ReentrantLock条件变量实现
- park&unpark实现

## 异步模式之生产者/消费者

用消息队列来平衡生产和消费的线程资源
