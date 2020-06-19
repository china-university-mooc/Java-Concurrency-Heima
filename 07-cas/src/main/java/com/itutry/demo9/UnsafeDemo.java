package com.itutry.demo9;

import lombok.Data;
import sun.misc.Unsafe;

public class UnsafeDemo {

  public static void main(String[] args) throws NoSuchFieldException {
    Unsafe unsafe = UnsafeAccessor.getUnsafe();

    long idOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
    long nameOffset = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));

    Student student = new Student();
    unsafe.compareAndSwapInt(student, idOffset, 0, 18);
    unsafe.compareAndSwapObject(student, nameOffset, null, "张三");

    System.out.println(student);
  }
}

@Data
class Student {

  private volatile int id;
  private volatile String name;
}
