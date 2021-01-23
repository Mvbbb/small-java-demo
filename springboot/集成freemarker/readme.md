1 引入 pom 依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
```

2 分析

request session放用户信息, 如果用户信息为null,证明没登录. 就重定向到登录界面 flt. 用户登录了的话, 就将用户信息放到modelandview里面, forward 到ftl


mv.setViewName("redirect:/user/login"); 重定向

mv.setViewName("/index"); forward跳转

https://gitee.com/mvbbb/java-learn-notes/blob/master/%E9%98%B6%E6%AE%B5%E4%BA%8C/freemarker.md