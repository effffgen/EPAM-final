<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="jspf/header.jspf" %>
<title>Register new user</title>
</head>
<body>
	<p class="h4 text-center mb-4">Please, remember that even if you
		have registered, your account has to be approved by the administrator
		of the system.</p>
	Please, enter your information below

	<!-- Form register -->
	<form action="register" method="post">
		<p class="h4 text-center mb-4">Sign up</p>

		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="firstname"> <label for="orangeForm-name">Your
				first name</label>
		</div>

		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="lastname"> <label for="orangeForm-name">Your
				last name</label>
		</div>

		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="username"> <label for="orangeForm-name">Your
				desired login</label>
		</div>

		<div class="md-form">
			<i class="fa fa-envelope prefix grey-text"></i> <input type="email"
				id="orangeForm-email" class="form-control" name="email"> <label
				for="orangeForm-email">Your email</label>
		</div>

		<div class="md-form">
			<i class="fa fa-lock prefix grey-text"></i> <input type="password"
				id="orangeForm-pass" class="form-control" name="password"> <label
				for="orangeForm-pass">Your password</label>
		</div>
                
		<div class="text-center">
			<button class="btn btn-deep-orange">Sign up</button>
		</div>

	</form>
<%@ include file="jspf/showmessage.jspf" %>

</body>
</html>