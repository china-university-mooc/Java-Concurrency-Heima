package com.itutry;

import com.itutry.util.Constants;
import com.itutry.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_09:11
 */
@Slf4j(topic = "c.TestStart")
public class TestStart {

  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      log.debug(Thread.currentThread().getName());
      FileReader.read(Constants.MP4_FULL_PATH);
    }, "t1");

//    t1.run();
    t1.start();
    log.debug("t1 state: {}", t1.getState());
    log.debug("do other things...");
  }
}
