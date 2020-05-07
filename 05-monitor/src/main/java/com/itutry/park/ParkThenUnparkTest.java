package com.itutry.park;

import static com.itutry.util.Sleeper.sleep;

import java.util.concurrent.locks.LockSupport;
import lombok.extern.slf4j.Slf4j;

/**
 * 先 park 再 unpark
 *
 * @author itutry
 * @create 2020-05-06_16:56
 */
@Slf4j(topic = "c.ParkThenUnparkTest")
public class ParkThenUnparkTest {

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      log.debug("start...");
      sleep(1);
      log.debug("park...");
      LockSupport.park();
      log.debug("resume...");
    }, "t1");

    t1.start();
    sleep(2);
    log.debug("unpark...");
    LockSupport.unpark(t1);
  }
}
