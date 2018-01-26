<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Requests</title>
	<%@ include file="../jspf/header.jspf"%>

</head>
<body>
	<%@ include file="../jspf/nav.jspf"%>

	<div class="tabs col-lg-12 col-md-12 col-sm-12 col-xs-12">
		<ul class="nav md-pills nav-justified pills-secondary">
			<li class="nav-item"><a href="requests?act=new"
				class="nav-link ${requestScope.action eq 'new' ? 'active' : '' }">Новые</a></li>
			<li class="nav-item"><a href="requests?act=approved"
				class="nav-link ${requestScope.action eq 'approved' ? 'active' : '' }">Выполненные</a></li>
			<li class="nav-item"><a href="requests?act=rejected"
				class="nav-link ${requestScope.action eq 'rejected' ? 'active' : '' }">Отклоненные</a></li>
		</ul>
		<div class="container tab-content col-lg-6 col-md-6 col-sm-6 col-xs-6">
			<c:choose>
				<c:when test="${empty requestScope.requests }">
					<div class="row">
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 text-center">
							<h1>There are no requests</h1>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h3>
						All
						<c:if test="${( requestScope.action != 'all') }"> ${requestScope.action } </c:if>
						requests
					</h3>

					<table class="table table-bordered table-striped">
						<thead class="indigo">
							<tr>
								<th>ID</th>
								<th>Operator</th>
								<th>Text</th>
								<c:if test="${(sessionScope.user.role == 'ADMINISTRATOR' && (requestScope.action == 'all' || requestScope.action == 'new')) }">
									<th>Actions</th>
								</c:if>
							</tr>
						</thead>
						<c:forEach items="${requestScope.requests }" var="req">
							<tr>
								<td>${req.id }</td>
								<td><a href="requests/get?id=${req.id }"
									data-toggle="modal" data-target="#utilModal">${req.operator.fullName}</a></td>
								<td class="table-truncate"><div class="table-truncate__body">${req.text }</div></td>
								<c:if test="${(req.status == 'ON_APPROVAL' && sessionScope.user.role == 'ADMINISTRATOR') }">
									<td>
										<form action="requests" method="post">
											<input type="hidden" name="id" value="${req.id }"> <input
												type="submit" name="act" value="reject"
												class="btn btn-primary btn-sm"> <input type="submit"
												name="act" value="approve" class="btn btn-primary btn-sm">
										</form>
									</td>
								</c:if>
							</tr>
						</c:forEach>

					</table>

				</c:otherwise>
			</c:choose>
		</div>
	</div>

<%@ include file="../jspf/utilmodal.jspf" %>


</body>
</html>