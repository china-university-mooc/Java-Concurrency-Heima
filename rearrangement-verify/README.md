# 验证指令重排序

## 项目骨架

```bash
mvn archetype:generate -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jcstress -DarchetypeArtifactId=jcstress-java-test-archetype -DarchetypeVersion=0.5 -DgroupId=com.itutry -DartifactId=rearrangement-verify -Dversion=1.0
```

## 执行

```bash
mvn clean install
jar -jar target/jcstress.jar
```
