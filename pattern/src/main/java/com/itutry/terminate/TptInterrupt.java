package com.itutry.terminate;

import static com.itutry.util.Sleeper.sleep;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_15:46
 */
@Slf4j(topic = "c.TptInterrupt")
public class TptInterrupt {

  public static void main(String[] args) {
    final Monitor monitor = new Monitor();
    monitor.start();

    sleep(6);
    monitor.stop();
  }

  private static class Monitor {

    private final Thread task;

    public Monitor() {
      task = new Thread(() -> {
        while (true) {
          final Thread current = Thread.currentThread();
          if (current.isInterrupted()) {
            log.debug("料理后事");
            break;
          }

          try {
            Thread.sleep(2000);
            log.debug("监控记录");
          } catch (InterruptedException e) {
            current.interrupt();
          }
        }
      }, "monitor");
    }

    public void start() {
      task.start();
    }

    public void stop() {
      task.interrupt();
    }
  }
}
