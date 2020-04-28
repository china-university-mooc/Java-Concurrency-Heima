package com.itutry.demo;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;

/**
 * @author itutry
 * @create 2020-04-28_09:20
 */
@Slf4j(topic = "c.TransferDemo")
public class TransferDemo {

  public static void main(String[] args) throws InterruptedException {
    final Account a = new Account(1000);
    final Account b = new Account(1000);

    final Thread t1 = new Thread(() -> {
      for (int i = 0; i < 200; i++) {
        a.transfer(b, randomAmount());
      }
    }, "t1");
    final Thread t2 = new Thread(() -> {
      for (int i = 0; i < 200; i++) {
        b.transfer(a, randomAmount());
      }
    }, "t2");

    t1.start();
    t2.start();

    t1.join();
    t2.join();
    log.debug("总额：{}", a.getBalance() + b.getBalance());
  }

  static Random random = new Random();

  public static int randomAmount() {
    return random.nextInt(100) + 1;
  }

  private static class Account {

    private int balance;

    public Account(int balance) {
      this.balance = balance;
    }

    public int getBalance() {
      return balance;
    }

    private void setBalance(int balance) {
      this.balance = balance;
    }

    public void transfer(Account target, int amount) {
      synchronized (Account.class) {
        if (balance > amount) {
          this.setBalance(this.getBalance() - amount);
          target.setBalance(target.getBalance() + amount);
        }
      }
    }
  }
}
