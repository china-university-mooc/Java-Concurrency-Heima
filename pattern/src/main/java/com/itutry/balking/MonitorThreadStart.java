package com.itutry.balking;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * 监控线程只启动一次
 *
 * @author itutry
 * @create 2020-05-19_22:51
 */
@Slf4j(topic = "c.MonitorThreadStart")
public class MonitorThreadStart {

  public static void main(String[] args) {
    final Monitor monitor = new Monitor();

    new Thread(monitor::start, "t1").start();
    new Thread(monitor::start, "t2").start();

    sleep(3);
    monitor.stop();
    log.debug("停止监控线程");

    new Thread(monitor::start, "t3").start();
    sleep(3);
    monitor.stop();
    log.debug("停止监控线程");
  }

  private static class Monitor {

    private Thread task;
    private volatile boolean starting = false;

    public void start() {
      synchronized (this) {
        if (starting) {
          return;
        }
        starting = true;
      }

      task = new Thread(() -> {
        while (true) {
          final Thread current = Thread.currentThread();
          if (current.isInterrupted()) {
            log.debug("料理后事");
            break;
          }

          try {
            Thread.sleep(1000);
            log.debug("监控记录");
          } catch (InterruptedException e) {
            current.interrupt();
          }
        }
        starting = false;
      }, "monitor");
      task.start();
    }

    public void stop() {
      task.interrupt();
    }
  }
}
