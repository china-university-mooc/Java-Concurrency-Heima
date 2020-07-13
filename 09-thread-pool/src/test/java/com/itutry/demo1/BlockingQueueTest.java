package com.itutry.demo1;

import org.junit.Test;

public class BlockingQueueTest {

  @Test
  public void test() {
    BlockingQueue<Object> queue = new BlockingQueue<>(2);

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        // FIXME 为啥加上sleep后没有输出
//        try {
//          Thread.sleep(1);
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
        System.out.println(queue.take());
      }
    }).start();

    for (int i = 0; i < 10; i++) {
      new Thread(() -> queue.put(new Object())).start();
    }
  }
}
