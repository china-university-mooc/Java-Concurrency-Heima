# lock eliminate

测试锁消除效果

## 描述

MyBenchmark.normal(): 正常无锁方法

MyBenchmark.lock(): 在局部锁对象上加锁

## 执行

正常执行：
```bash
java -jar target/benchmarks.jar
```

禁用锁消除执行：
```bash
java -XX:-EliminateLocks -jar target/benchmarks.jar
```

## 结果

正常：
```
MyBenchmark.lock    avgt    5  2.177 ± 0.110  ns/op
MyBenchmark.normal  avgt    5  2.210 ± 0.176  ns/op
```

禁用锁消除:
```
MyBenchmark.lock    avgt    5  20.942 ± 1.660  ns/op
MyBenchmark.normal  avgt    5   2.126 ± 0.108  ns/op
```

## 结论

根据测试结果，禁用锁消除时，正常方法的性能是加锁方法的十多倍，但锁消除开启时，加锁方法和正常方法的性能基本一样，说明JIT确实进行了优化，消除了不必要的锁。
