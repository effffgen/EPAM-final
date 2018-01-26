<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">
	${requestScope.action == 'create' ? 'Create new flight' : 'Modify flight'}</h5>

	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>



<div class="modal-body">
<input type="text" name="from"> 
</div>

<div class="modal-footer">


</div>


