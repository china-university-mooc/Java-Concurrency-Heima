package com.itutry.demo2;

@FunctionalInterface
public interface RejectPolicy<T> {

  void reject(BlockingQueue<T> queue, T task);
}
