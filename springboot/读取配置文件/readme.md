**配置lombok减少代码量**

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

**需要知道的是, 假如配置了spring.profiles.active, 那么springboot会合并两个配置文件**

```java
spring:
  profiles:
#    这里可以启用不同的配置文件 
#    active: dev
    active: prod
```

**配置文件命名规范:**
主配置文件为 application.yml, 那么active的配置文件的命名规范为 application-*.xml, *为active字段值

**引入 `spring-boot-configuration-processor` 以启用`@ConfigurationProperties`注解, 该注解标记在类上'**

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```