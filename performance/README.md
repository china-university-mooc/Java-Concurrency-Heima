# performance

使用JMH分别在单核CPU和多核CPU下比较单线程和多线程的执行效率。


## 1.代码描述

MyBenchmark.single 方法：用单线程来执行1亿个数的求和

MyBenchmark.multi 方法：用四个线程分别执行2500万个数的求和，最后在主线程汇总结果


## 2.打包

执行：
```bash
mvn clean package
```

然后使用target下的benchmarks.jar,因为这个jar包中才有主类

## 3.多核运行

直接在本机运行，本机有6个核

## 4.单核运行

利用docker模拟单核环境

搜索并获取jdk镜像
```bash
docker search jdk
docker pull primetoninc/jdk:1.8
```

交互式启动jdk镜像，分配一个固定的CPU，然后上传jar包
```bash
docker run -it --rm --cpuset-cpus="1" --name=benchmark primetoninc/jdk /bin/bash
docker cp /path/to/target/benchmarks.jar benchmark:/home
```

在交互式命令行中执行jar包
```bash
java -jar -Xmx1G benchmarks.jar
```

终止并删除jdk容器
```bash
docker stop benchmark
docker rm benchmark
```

## 5.结果

多核：
```
Benchmark           Mode  Cnt  Score   Error  Units
MyBenchmark.multi   avgt    5  0.020 ± 0.001   s/op
MyBenchmark.single  avgt    5  0.042 ± 0.001   s/op
```

单核：
```
Benchmark           Mode  Cnt  Score   Error  Units
MyBenchmark.multi   avgt    5  0.066 ± 0.014   s/op
MyBenchmark.single  avgt    5  0.061 ± 0.009   s/op
```
