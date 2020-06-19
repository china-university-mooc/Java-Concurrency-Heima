package com.itutry.demo5;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GarbageBag {

  private String desc;

  @Override
  public String toString() {
    return super.toString() + " " + desc;
  }
}
