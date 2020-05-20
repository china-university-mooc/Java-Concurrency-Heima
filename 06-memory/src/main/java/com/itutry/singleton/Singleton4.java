package com.itutry.singleton;

import java.io.Serializable;

/**
 * @author itutry
 * @create 2020-05-20_09:33
 */
public final class Singleton4 implements Serializable {

  private Singleton4() {

  }

  private static volatile Singleton4 INSTANCE = null;

  public static Singleton4 getInstance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }

    synchronized (Singleton4.class) {
      if (INSTANCE != null) {
        return INSTANCE;
      }

      INSTANCE = new Singleton4();
      return INSTANCE;
    }
  }

  public Object readResolve() {
    return INSTANCE;
  }
}
