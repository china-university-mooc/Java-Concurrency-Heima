package com.itutry.singleton;

import java.io.Serializable;

/**
 * @author itutry
 * @create 2020-05-20_09:33
 */
public final class Singleton1 implements Serializable {

  private Singleton1() {

  }

  private static final Singleton1 INSTANCE = new Singleton1();

  public static Singleton1 getInstance() {
    return INSTANCE;
  }

  public Object readResolve() {
    return INSTANCE;
  }
}
