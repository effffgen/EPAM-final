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
			<li class="nav-item"><a href="users?act=get&criteria=new"
				class="nav-link ${requestScope.criteria eq 'new' ? 'active' : '' }">Unregistered</a></li>
			<li class="nav-item"><a href="users?act=get&criteria=registered"
				class="nav-link ${requestScope.criteria eq 'registered' ? 'active' : '' }">Registered</a></li>
			<li class="nav-item"><a href="users?act=get&criteria=all"
				class="nav-link ${requestScope.criteria eq 'all' ? 'active' : '' }">All</a></li>
		</ul>
		<div class="container tab-content col-lg-8 col-md-8 col-sm-8 col-xs-8">
			
	<div style="margin:auto">
	<a href="users?act=create" class="btn btn-secondary" data-toggle="modal" data-target="#utilModal">Create new user</a>
	</div>
			
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
								<td class="text-nowrap"><button type="button" class="btn btn-secondary btn-sm"
										data-toggle="modal" data-target="#delete"
										onclick="setData('deleteid',${user.id})">Delete</button> 
									<a href="users?act=modify&id=${user.id }"
									class="btn btn-secondary btn-sm" data-toggle="modal"
									data-target="#utilModal">Modify</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

<%@ include file="../jspf/utilmodal.jspf" %>
<%@ include file="../jspf/users/delete.jspf" %>
</body>
</html>