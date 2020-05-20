package com.itutry.terminate;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * 利用volatile的停止标记终止线程
 *
 * @author itutry
 * @create 2020-04-24_15:46
 */
@Slf4j(topic = "c.TptVolatile")
public class TptVolatile {

  public static void main(String[] args) {
    final Monitor monitor = new Monitor();
    monitor.start();

    sleep(3);
    monitor.stop();
    log.debug("停止监控线程");
  }

  private static class Monitor {

    private final Thread task;
    private volatile boolean stop = false;

    public Monitor() {
      task = new Thread(() -> {
        while (true) {
          if (stop) {
            log.debug("料理后事");
            break;
          }

          try {
            Thread.sleep(1000);
            log.debug("监控记录");
          } catch (InterruptedException e) {
          }
        }
      }, "monitor");
    }

    public void start() {
      task.start();
    }

    public void stop() {
      stop = true;
      task.interrupt();
    }
  }
}
