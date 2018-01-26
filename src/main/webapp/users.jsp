<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>Users</title>
	<%@ include file="../jspf/header.jspf"%>
</head>
<body>
	<%@ include file="../jspf/nav.jspf"%>
	<div class="tabs col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<ul class="nav md-pills nav-justified pills-secondary">
			<li class="nav-item"><a href="users?act=new"
				class="nav-link ${requestScope.action eq 'unreg' ? 'active' : '' }">Unregistered</a></li>
			<li class="nav-item"><a href="users?act=registered"
				class="nav-link ${requestScope.action eq 'reg' ? 'active' : '' }">Registered</a></li>
			<li class="nav-item"><a href="users?act=all"
				class="nav-link ${requestScope.action eq 'all' ? 'active' : '' }">All</a></li>
		</ul>
		<div class="container tab-content col-lg-6 col-md-6 col-sm-6 col-xs-6">
			<c:choose>
				<c:when test="${empty requestScope.users }">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
							<h1>No users</h1>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h3>
						All TODOTODO users
					</h3>

					<table class="table table-bordered table-striped">
						<thead class="indigo">
							<tr>
								<th>ID</th>
								<th>First name</th>
								<th>Last name</th>
								<th>Login</th>
								<th>Creation date</th>
								<th>Role</th>
								<th>Actions</th>
							</tr>
						</thead>
						<c:forEach items="${requestScope.users}" var="user">
							<tr>
								<td>${user.id }</td>
								<td>${user.firstName}</td>
								<td>${user.lastName }</td>
								<td>${user.username }</td>
								<td><fmt:formatDate value="${user.creationTime }" pattern="dd/MM/yyyy"/></td>
								<td>${user.role }</td>
								<td></td>
							</tr>
						</c:forEach>

					</table>

				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="modal fade" id="utilModal" tabindex="-1"
		role="dialog" aria-labelledby="reqDetailsModal" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"></div>
		</div>
	</div>


</body>
</html>