package com.itutry.singleton;

/**
 * @author itutry
 * @create 2020-05-20_09:40
 */
public final class Singleton5 {

  private Singleton5() {
  }

  private static class LazyHolder {

    static final Singleton5 INSTANCE = new Singleton5();
  }

  public static Singleton5 getInstance() {
    return LazyHolder.INSTANCE;
  }
}
