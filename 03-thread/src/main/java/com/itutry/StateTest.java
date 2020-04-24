package com.itutry;

import static com.itutry.util.Sleeper.sleep;

import com.itutry.util.Constants;
import com.itutry.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_11:59
 */
@Slf4j(topic = "c.StateTest")
public class StateTest {

  public static void main(String[] args) {
    final Thread t1 = new Thread(() -> {
      sleep(1);
    }, "t1");

    final Thread t2 = new Thread(() -> {
      FileReader.read(Constants.MP4_FULL_PATH);
      FileReader.read(Constants.MP4_FULL_PATH);
      FileReader.read(Constants.MP4_FULL_PATH);
    }, "t2");
    t2.start();

    final Thread t3 = new Thread(() -> {}, "t3");
    t3.start();

    final Thread t4 = new Thread(() -> {
      synchronized (StateTest.class) {
        sleep(2);
      }
    }, "t4");
    t4.start();

    final Thread t5 = new Thread(() -> {
      try {
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t5");
    t5.start();

    final Thread t6 = new Thread(() -> {
      synchronized (StateTest.class) {
        sleep(2);
      }
    }, "t6");
    t6.start();

    log.debug("t1 state: {}", t1.getState());
    log.debug("t2 state: {}", t2.getState());
    log.debug("t3 state: {}", t3.getState());
    log.debug("t4 state: {}", t4.getState());
    log.debug("t5 state: {}", t5.getState());
    log.debug("t6 state: {}", t6.getState());
  }
}
