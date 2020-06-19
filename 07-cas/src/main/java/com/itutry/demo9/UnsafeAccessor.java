package com.itutry.demo9;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class UnsafeAccessor {

  private static Unsafe unsafe;

  public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
    theUnsafe.setAccessible(true);
    Unsafe unsafe = (Unsafe) theUnsafe.get(null);
    System.out.println(unsafe);
  }

  static {
    try {
      Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
      theUnsafe.setAccessible(true);
      unsafe = (Unsafe) theUnsafe.get(null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Unsafe getUnsafe() {
    return unsafe;
  }
}
