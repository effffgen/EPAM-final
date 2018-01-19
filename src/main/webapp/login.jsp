<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="jspf/mui.jspf" %>
<title>Login page</title>
</head>
<body>

<form action="login" method="post">
Username:<br>
<input name="login"><br>
Password:<br>
<input type="password" name="password"><br>
<input type="submit" value="Login">
<input type="button" value="Register" onclick="window.location.href='register'">
</form>

<p style="color:red">
${sessionScope.error }
</p>
</body>
</html>