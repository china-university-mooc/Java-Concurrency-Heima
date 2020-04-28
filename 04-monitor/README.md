
## 命令行执行
```bash
java -cp ".:/Users/zhaozhang/.m2/repository/ch/qos/logback/logback-classic/1.2.3/logback-classic-1.2.3.jar:/Users/zhaozhang/.m2/repository/ch/qos/logback/logback-core/1.2.3/logback-core-1.2.3.jar:/Users/zhaozhang/.m2/repository/org/slf4j/slf4j-api/1.7.25/slf4j-api-1.7.25.jar" com.itutry.demo.SellTicketDemo
```

class path用:分隔，且必须为绝对路径

## 重复执行命令
```bash
for i in {1..10}; do <command>; done
```
