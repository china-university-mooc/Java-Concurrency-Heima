package com.ituyty.chronous.asyn;

import com.ituyty.util.Constants;
import com.ituyty.util.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-24_15:16
 */
@Slf4j(topic = "AsynThread")
public class AsynThread {

  public static void main(String[] args) {
    new Thread(() -> FileReader.read(Constants.MP4_FULL_PATH), "t1").start();

    log.debug("do other things...");
  }
}
