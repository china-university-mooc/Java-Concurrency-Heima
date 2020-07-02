package com.itutry.demo1;

import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App3 {

  public static void main(String[] args) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          log.debug("{}", formatter.parse("1951-04-21"));
        } catch (Exception e) {
          log.error("出错啦", e);
        }
      }).start();
    }
  }
}
