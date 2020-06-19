package com.itutry.demo3;

import java.math.BigDecimal;

public class DecimalAccountUnsafe implements DecimalAccount {

  private BigDecimal balance;

  public DecimalAccountUnsafe(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public void withdraw(BigDecimal amount) {
    balance = balance.subtract(amount);
  }
}
