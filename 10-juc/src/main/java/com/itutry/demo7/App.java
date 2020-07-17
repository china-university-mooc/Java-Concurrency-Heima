package com.itutry.demo7;

import com.itutry.util.Sleeper;
import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) {
    Pool pool = new SemaphorePool(3);
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        Connection conn = pool.borrow();
        log.debug("borrow... {}", conn);
        Sleeper.sleep(1);
        log.debug("free... {}", conn);
        pool.free(conn);
      }).start();
    }
  }
}
