package com.itutry.demo6;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import lombok.ToString;

public class AtomicReferenceFieldUpdaterDemo {

  public static void main(String[] args) {
    Student student = new Student();
    AtomicReferenceFieldUpdater<Student, String> updater = AtomicReferenceFieldUpdater
        .newUpdater(Student.class, String.class, "name");
    System.out.println(updater.compareAndSet(student, null, "张三"));
    System.out.println(student);
  }
}

@ToString
class Student {
  volatile String name;
}
