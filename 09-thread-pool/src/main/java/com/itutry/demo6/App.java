package com.itutry.demo6;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  // 每周四18:00:00
  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();

    LocalDateTime next = now.withHour(18).withMinute(0).withSecond(0).withNano(0)
        .with(DayOfWeek.THURSDAY);
    if (now.isAfter(next)) {
      next = next.plusWeeks(1);
    }

    System.out.println(next);

    long initialDelay = ChronoUnit.MILLIS.between(now, next);
    System.out.println(initialDelay);
    System.out.println(Duration.between(now, next).toMillis());
    long period = TimeUnit.DAYS.toMillis(7);

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(2);
    poolExecutor
        .scheduleAtFixedRate(() -> log.debug("task"), initialDelay, period, TimeUnit.MILLISECONDS);
  }
}
