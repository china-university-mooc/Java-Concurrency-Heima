package com.itutry.singleton;

import java.io.Serializable;

/**
 * @author itutry
 * @create 2020-05-20_09:33
 */
public final class Singleton3 implements Serializable {

  private Singleton3() {

  }

  private static Singleton3 INSTANCE = null;

  public static synchronized Singleton3 getInstance() {
    if (INSTANCE != null) {
      return INSTANCE;
    }

    INSTANCE = new Singleton3();
    return INSTANCE;
  }

  public Object readResolve() {
    return INSTANCE;
  }
}
