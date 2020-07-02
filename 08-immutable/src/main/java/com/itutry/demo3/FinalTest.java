package com.itutry.demo3;

public class FinalTest {

  final static int A = 10;
  final static int B = Short.MAX_VALUE + 1;

  final int a = 20;
  final int b = Integer.MAX_VALUE;
}

class UseFinal {
  public void test() {
    System.out.println(FinalTest.A);
    System.out.println(FinalTest.B);
    System.out.println(new FinalTest().a);
    System.out.println(new FinalTest().b);
  }
}
