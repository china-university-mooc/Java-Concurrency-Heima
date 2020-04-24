package com.itutry;

import static com.itutry.util.Sleeper.sleep;

import com.itutry.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_11:56
 */
@Slf4j(topic = "c.TestDaemon")
public class TestDaemon {

  public static void main(String[] args) {
    final Thread t1 = new Thread(() -> {
      sleep(3);
      log.debug("结束");
    }, "t1");

    t1.setDaemon(true);
    t1.start();
    sleep(1);
    log.debug("结束");
  }
}
