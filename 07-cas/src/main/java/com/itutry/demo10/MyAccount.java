package com.itutry.demo10;

public class MyAccount implements Account {

  private MyAtomicInteger balance;

  public MyAccount(int value) {
    this.balance = new MyAtomicInteger(value);
  }

  @Override
  public Integer getBalance() {
    return balance.get();
  }

  @Override
  public void withdraw(Integer amount) {
    balance.decrease(amount);
  }
}
