package com.itutry.demo10;

import com.itutry.demo9.UnsafeAccessor;
import sun.misc.Unsafe;

public class MyAtomicInteger {

  private int value;

  private static Unsafe unsafe;

  private static long valueOffset;

  static {
    unsafe = UnsafeAccessor.getUnsafe();
    try {
      valueOffset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }

  public MyAtomicInteger(int value) {
    this.value = value;
  }

  public void decrease(int amount) {
    while (true) {
      int prev = value;
      int next = value - amount;
      if (unsafe.compareAndSwapInt(this, valueOffset, prev, next)) {
        break;
      }
    }
  }

  public int get() {
    return value;
  }
}
