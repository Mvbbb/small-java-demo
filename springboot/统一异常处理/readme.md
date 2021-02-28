此 demo 演示了如何在Spring Boot中进行统一的异常处理，包括了两种方式的处理：
第一种对常见API形式的接口进行异常处理，统一封装返回格式；
第二种是对模板页面请求的异常处理，统一处理错误页面。

包含统一API返回对象

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```