<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">Details about the
		request #${requestScope.req.id }</h5>
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<table>
		<tr>
			<td>Operator's name</td>
			<td>${requestScope.req.operator.fullName }</td>
		</tr>
		<tr>
			<td>Full text of request</td>
			<td>${requestScope.req.text}</td>
		</tr>
		<tr>
			<td>Date of creation</td>
			<td><fmt:formatDate value="${requestScope.req.creationDate }"
					pattern="dd/MM/yyyy" /></td>
		</tr>

		<tr>
			<td>Current request status:</td>
			<td>${requestScope.req.status }</td>
		</tr>
	</table>



</div>

<div class="modal-footer">
	<c:if test="${(requestScope.req.status == 'ON_APPROVAL' && sessionScope.user.role=='ADMINISTRATOR') }">
	<form action="requests" method="post">
		<input type="hidden" id="deleteid" name="id" value="${requestScope.req.id }" />
		<button type="submit" class="btn btn-primary" name="act" value="approve">Approve</button>
		<button type="submit" class="btn btn-primary" name="act" value="reject">Reject</button>
	</form>
	</c:if>


</div>


