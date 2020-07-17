package com.itutry.demo4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestGenericDao {

  public static void main(String[] args) {
    GenericDao dao = new CachedGenericDao();
    System.out.println("============> 查询");
    String sql = "select * from emp where empno = ?";
    int empno = 7369;
    Emp emp = dao.queryOne(Emp.class, sql, empno);
    System.out.println(emp);
    emp = dao.queryOne(Emp.class, sql, empno);
    System.out.println(emp);
    emp = dao.queryOne(Emp.class, sql, empno);
    System.out.println(emp);

    System.out.println("============> 更新");
    dao.update("update emp set sal = ? where empno = ?", 2000, empno);
    emp = dao.queryOne(Emp.class, sql, empno);
    System.out.println(emp);
  }
}

class CachedGenericDao extends GenericDao {

  private GenericDao dao = new GenericDao();
  private Map<SqlPair, Object> map = new HashMap<>();
  private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();

  @Override
  public <T> List<T> queryList(Class<T> beanClass, String sql, Object... args) {
    return dao.queryList(beanClass, sql, args);
  }

  @Override
  public <T> T queryOne(Class<T> beanClass, String sql, Object... args) {
    // 先从缓存中找，找到直接返回
    rw.readLock().lock();
    SqlPair key = new SqlPair(sql, args);
    try {
      @SuppressWarnings("unchecked")
      T value = (T) map.get(key);
      if (value != null) {
        return value;
      }
    } finally {
      rw.readLock().unlock();
    }

    // 缓存中没有，查询数据库
    rw.readLock().lock();
    try {
      @SuppressWarnings("unchecked")
      T value = (T) map.get(key);
      if (value == null) {
        value = dao.queryOne(beanClass, sql, args);
        map.put(key, value);
      }

      return value;
    } finally {
      rw.readLock().unlock();
    }
  }

  @Override
  public int update(String sql, Object... args) {
    rw.writeLock().lock();
    try {
      int update = dao.update(sql, args);
      map.clear();
      return update;
    } finally {
      rw.writeLock().unlock();
    }
  }

  private class SqlPair {

    private final String sql;
    private final Object[] args;

    public SqlPair(String sql, Object[] args) {
      this.sql = sql;
      this.args = args;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      SqlPair sqlPair = (SqlPair) o;

      if (!sql.equals(sqlPair.sql)) {
        return false;
      }
      // Probably incorrect - comparing Object[] arrays with Arrays.equals
      return Arrays.equals(args, sqlPair.args);
    }

    @Override
    public int hashCode() {
      int result = sql.hashCode();
      result = 31 * result + Arrays.hashCode(args);
      return result;
    }
  }
}
