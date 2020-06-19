package com.itutry.demo3;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class DecimalAccountCas implements DecimalAccount {

  private AtomicReference<BigDecimal> balance;

  public DecimalAccountCas(BigDecimal balance) {
    this.balance = new AtomicReference<>(balance);
  }

  @Override
  public BigDecimal getBalance() {
    return balance.get();
  }

  @Override
  public void withdraw(BigDecimal amount) {
//    while(true) {
//      BigDecimal prev = balance.get();
//      BigDecimal next = prev.subtract(amount);
//      if (balance.compareAndSet(prev, next)) {
//        break;
//      }
//    }
    balance.updateAndGet(v -> v.subtract(amount));
  }
}
