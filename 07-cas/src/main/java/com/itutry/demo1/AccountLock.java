package com.itutry.demo1;

public class AccountLock implements Account {

  private Integer balance;

  public AccountLock(Integer balance) {
    this.balance = balance;
  }

  @Override
  public synchronized Integer getBalance() {
    return balance;
  }

  @Override
  public synchronized void withdraw(Integer amount) {
    balance -= amount;
  }
}
