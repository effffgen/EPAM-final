<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-header">
	<div class="text-center">${(requestScope.action == 'modify' ? 'Modify user' : 'Create new user')}</div>
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<form action="users" method="post">
		<input type="hidden" name="act" value="${requestScope.action }">
		<input type="hidden" name="id" value="${requestScope.user.id }">
		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="firstname" value="${requestScope.user.firstName }"> <label
				class="active" for="orangeForm-name">First name</label>
		</div>

		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="lastname" value="${requestScope.user.lastName }"> <label
				class="active" for="orangeForm-name">Last name</label>
		</div>

		<div class="md-form">
			<input type="text" id="orangeForm-name" class="form-control"
				name="username" value="${requestScope.user.username }"> <label
				class="active" for="orangeForm-name">Login</label>
		</div>
		<c:if test="${requestScope.action eq 'create'}">
			<div class="md-form">
				<i class="fa fa-lock prefix grey-text"></i> <input type="password"
					id="newPass" class="form-control" name="password"> <label
					class="active" for="newPass">Enter password</label>
			</div>

			<div class="md-form">
				<i class="fa fa-lock prefix grey-text"></i> <input type="password"
					id="confPass" class="form-control"> <label class="active" for="confPass">Confirm
					password</label>
			</div>

			<script>
				var password = document.getElementById("newPass"), confirm_password = document
						.getElementById("confPass");

				function validatePassword() {
					if (password.value != confirm_password.value) {
						confirm_password
								.setCustomValidity("Passwords Don't Match");
					} else {
						confirm_password.setCustomValidity('');
					}
				}

				password.onchange = validatePassword;
				confirm_password.onkeyup = validatePassword;
			</script>
		</c:if>
		<div class="md-form">
			<i class="fa fa-envelope prefix grey-text"></i> <input type="email"
				id="orangeForm-email" class="form-control" name="email"
				value="${requestScope.user.email }"> <label
				class="active" for="orangeForm-email">Email</label>
		</div>

		<div class="form-group">
			<label for="sel1">Set role:</label> <select class="form-control"
				id="sel1" name="role" required>
				<option value="NONE" disabled>NONE</option>
				<option value="ADMINISTRATOR">ADMINISTRATOR</option>
				<option value="PILOT">PILOT</option>
				<option value="OPERATOR">OPERATOR</option>
				<option value="NAVIGATOR">NAVIGATOR</option>
				<option value="ATTENDANT">ATTENDANT</option>
			</select>
		</div>

		<div class="text-center">
			<input type="submit" class="btn btn-deep-orange"
				value="${(requestScope.action == 'modify' ? 'Modify' : 'Create')}">
		</div>

	</form>
</div>
	<script>
	$(document).ready(function(){
		$("option[value='${requestScope.user.role}']").attr('selected', 'selected');
	});
	
	</script>