package com.itutry.demo2;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

  public static void main(String[] args) {
    AtomicReference<BigDecimal> atomicReference = new AtomicReference<>(BigDecimal.ZERO);

    System.out.println(atomicReference.updateAndGet(v -> v.add(BigDecimal.TEN)));
    System.out.println(atomicReference.accumulateAndGet(BigDecimal.ONE, BigDecimal::subtract));
    atomicReference.getAndSet(BigDecimal.TEN);
    System.out.println(atomicReference.get());
  }
}
