package com.itutry.test;

import java.util.concurrent.SynchronousQueue;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "SynchronousQueueTest")
public class SynchronousQueueTest {

  public static void main(String[] args) throws InterruptedException {
    SynchronousQueue<Integer> queue = new SynchronousQueue<>();

    new Thread(() -> {
      try {
        log.debug("putting... 1");
        queue.put(1);
        log.debug("putted 1");

        log.debug("putting... 2");
        queue.put(2);
        log.debug("putted 2");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t1").start();

    Thread.sleep(1000);
    new Thread(() ->{
      try{
        log.debug("take 1");
        queue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t2").start();

    Thread.sleep(1000);
    new Thread(() ->{
      try{
        log.debug("take 2");
        queue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, "t3").start();
  }
}
