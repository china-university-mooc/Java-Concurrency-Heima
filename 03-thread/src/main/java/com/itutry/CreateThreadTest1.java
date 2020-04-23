package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-23_11:30
 */
@Slf4j(topic = "c.CreateThreadTest1")
public class CreateThreadTest1 {

  public static void main(String[] args) {
    final Thread t = new Thread() {
      @Override
      public void run() {
        log.debug("running");
      }
    };
    t.setName("t1");
    t.start();

    log.debug("running");
  }
}
