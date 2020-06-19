package com.itutry.demo1;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountCas implements Account {

  private AtomicInteger balance;

  public AccountCas(Integer balance) {
    this.balance = new AtomicInteger(balance);
  }

  @Override
  public Integer getBalance() {
    return balance.get();
  }

  @Override
  public synchronized void withdraw(Integer amount) {
//    while (true) {
//      int prev = balance.get();
//      int next = prev - amount;
//      if (balance.compareAndSet(prev, next)) {
//        break;
//      }
//    }
    balance.addAndGet(-amount);
  }
}
