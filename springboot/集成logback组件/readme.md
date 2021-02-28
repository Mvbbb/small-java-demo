springboot会自动监测到日志组件logback的存在, 并启用.不需要到 application.yml 中额外配置

1 引入lombak pom 依赖
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

2 不需要特意为 logback 引入依赖, 因为spring-boot-starter-web依赖默认依赖了logging starter,

3 编写logback-spring.xml配置文件, 文件名不能更改. 如果实在是想要指定的话, 在application.properties中添加如下内容
```
logging.config=classpath:log/logback.xml
```