package com.itutry.demo1;

import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App1 {

  public static void main(String[] args) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    for (int i = 0; i < 10; i++) {
      new Thread(() -> {
        try {
          log.debug("{}", sdf.parse("1951-04-21"));
        } catch (Exception e) {
          log.error("出错啦", e);
        }
      }).start();
    }
  }
}
