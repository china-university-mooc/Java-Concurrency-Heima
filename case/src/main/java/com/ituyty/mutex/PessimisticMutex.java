package com.ituyty.mutex;

/**
 * @author itutry
 * @create 2020-04-28_21:39
 */
public class PessimisticMutex {

  public static void main(String[] args) {
    final PessimisticAccount account = new PessimisticAccount();
    Account.demo(account);
  }

  private static class PessimisticAccount implements Account {

    private int balance = 10000;

    @Override
    public Integer getBalance() {
      synchronized (this) {
        return balance;
      }
    }

    @Override
    public void withdraw(Integer amount) {
      synchronized (this) {
        if (balance >= amount) {
          balance -= amount;
        }
      }
    }
  }
}
