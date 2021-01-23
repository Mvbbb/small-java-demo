此 demo 主要是演示如何使用 aop 切面对请求进行 logback 日志记录，并且记录 UserAgent 信息。

1 引入 aop pom依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

2 引入解析 userAgent 信息的依赖

```xml
<!-- 解析 UserAgent 信息 -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.0-jre</version>
</dependency>
<dependency>
    <groupId>eu.bitwalker</groupId>
    <artifactId>UserAgentUtils</artifactId>
</dependency>
```

3 引入hutool, 简化编码
```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
</dependency>
```