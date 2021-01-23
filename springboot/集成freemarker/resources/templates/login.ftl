<!doctype html>
<html lang="en">
<#include "head.ftl">
<body>
<div id="app" style="margin: 20px 20%">
    请登录
    <form action="/user/login" method="post">
        用户名<input type="text" name="name" placeholder="用户名"/>
        密码<input type="password" name="password" placeholder="密码"/>
        <input type="submit" value="登录">
    </form>
</div>
</body>
</html>