package com.itutry.demo3;

import java.math.BigDecimal;

public interface DecimalAccount {

  BigDecimal getBalance();

  void withdraw(BigDecimal amount);
}
