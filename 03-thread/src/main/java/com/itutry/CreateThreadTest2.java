package com.itutry;

import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-23_11:55
 */
@Slf4j(topic = "c.CreateThreadTest2")
public class CreateThreadTest2 {

  public static void main(String[] args) {
//    final Runnable r = new Runnable() {
//      @Override
//      public void run() {
//        log.debug("running");
//      }
//    };

    final Runnable r = () -> log.debug("running");

    final Thread t = new Thread(r, "t1");
    t.start();
    log.debug("running");
  }
}
