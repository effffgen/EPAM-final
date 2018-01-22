<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="jspf/header.jspf"%>
<title>Login page</title>
</head>
<body>

	<div class="container col-lg-4 col-md-4 col-sm-4">
		<form action="login" method="post">
			<p class="h5 text-center mb-4">Sign in</p>

			<div class="md-form">
				<input type="text" id="defaultForm-email" class="form-control" name="login">
					<label class="active" for="defaultForm-email">Your login</label>
			</div>

			<div class="md-form">
				<i class="fa fa-lock prefix grey-text"></i>
				<input type="password" id="defaultForm-pass" class="form-control" name="password">
				<label class="active" for="defaultForm-pass">Your password</label>
			</div>

			<div class="text-center">
				<input type="submit" class="btn btn-default" value="Login">
				<a href="register" class="btn btn-secondary">Register</a>
			</div>
		</form>
	</div>
	<p style="color: red">${sessionScope.error }</p>
</body>
</html>