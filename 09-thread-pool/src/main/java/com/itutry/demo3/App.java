package com.itutry.demo3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  private static final List<String> MENU = Arrays.asList("地三鲜", "宫保鸡丁", "辣子鸡丁", "烤鸡翅");
  private static final Random RANDOM = new Random();
  private static String cooking() {
    return MENU.get(RANDOM.nextInt(MENU.size()));
  }

  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(1);
    ExecutorService pool2 = Executors.newFixedThreadPool(1);

    pool.execute(() -> {
      log.debug("处理点餐");

      Future<String> future = pool2.submit(() -> {
        log.debug("做菜");
        Thread.sleep(1000);
        return cooking();
      });

      try {
        log.debug("上菜：{}", future.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });

    pool.execute(() -> {
      log.debug("处理点餐");

      Future<String> future = pool2.submit(() -> {
        log.debug("做菜");
        Thread.sleep(1000);
        return cooking();
      });

      try {
        log.debug("上菜：{}", future.get());
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
    });
  }
}
