<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>${sessionScope.user.username }'ssettings</title>
</head>
<body>
	<%@ include file="../jspf/nav.jspf"%>
	<h4 class="text-center">Here you can change your password</h4>
	<div class="container col-sm-4 col-md-4 col-lg-4">
		<form action="settings" method="post">
			<input type="hidden" name="action" value="update">
			<div class="md-form">
				<input type="password" id="currPass" name="currentPass"
					class="form-control"> <label class="" for="currPass">Enter
					current password</label>
			</div>
			<div class="md-form">
				<input type="password" name="newPass" id="newPass"
					class="form-control"> <label class="" for="newPass">Enter
					new password</label>
			</div>
			<div class="md-form">
				<input type="password" id="confPass" class="form-control"> <label
					class="" for="confPass">Confirm new password</label>
			</div>
			<input type="submit" value="Update password" class="btn btn-primary">

		</form>
	</div>

	<%@ include file="../jspf/header.jspf"%>
	<script>
		var password = document.getElementById("newPass"), confirm_password = document
				.getElementById("confPass");

		function validatePassword() {
			if (password.value != confirm_password.value) {
				confirm_password.setCustomValidity("Passwords Don't Match");
			} else {
				confirm_password.setCustomValidity('');
			}
		}

		password.onchange = validatePassword;
		confirm_password.onkeyup = validatePassword;
	</script>
</body>
</html>