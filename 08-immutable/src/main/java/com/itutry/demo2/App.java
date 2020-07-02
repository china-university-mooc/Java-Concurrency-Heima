package com.itutry.demo2;

import java.sql.Connection;
import java.util.Random;

public class App {

  public static void main(String[] args) {
    Pool pool = new Pool(2);
    for (int i = 0; i < 5; i++) {
      new Thread(() -> {
        Connection conn = pool.borrow();
        try {
          Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        pool.free(conn);
      }).start();
    }
  }
}
