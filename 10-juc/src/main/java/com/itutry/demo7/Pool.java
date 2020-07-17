package com.itutry.demo7;

import java.sql.Connection;

public interface Pool {

  Connection borrow();
  void free(Connection conn);
}
