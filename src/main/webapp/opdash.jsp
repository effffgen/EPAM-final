<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="jspf/header.jspf"%>
<title>Dashboard</title>
<script>

	$(document).ready(function(){
	    $('#info td span.badge').each(function(){
	        if ($(this).text() == 'Ready') {
	            $(this).addClass('badge-success');
	        }
	        else{
	        	$(this).addClass('badge-warning');
	        }
	    });
	});
</script>
</head>
<body>
	<%@ include file="jspf/nav.jspf"%>
	<c:choose>
		<c:when test="${empty requestScope.flights }">
			<h2 class="text-center">There are no flights on the list</h2>
		</c:when>
		<c:otherwise>
			<div class="container col-lg-6 col-md-6 col-sm-6">
				<h3 class="text-center">Flight list</h3>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#createFlight" onclick="">Create new flight</button>

				<table class="info table table-bordered table-striped" id="info">
					<thead class="blue darken-1">
						<tr>
							<th>Id</th>
							<th>From - to</th>
							<th>Team status</th>
							<c:if test="${(user.role == 'ADMINISTRATOR')}">
								<th>Actions</th>
							</c:if>
						</tr>
					</thead>
					<c:forEach items="${requestScope.flights }" var="flight">
						<tr>
							<td>${flight.id}</td>
							<td><a href="flightdetails?id=${flight.id}" data-toggle="modal" data-target="#utilModal">${flight.depart}
									-> ${flight.destination}</a></td>
							<td><h5>
									<span class="badge">${flight.flightTeam.readiness ? 'Ready' : 'Not ready' }</span>
								</h5></td>
							<c:if test="${(user.role == 'ADMINISTRATOR')}">
								<td><button type="button" class="btn btn-secondary btn-sm"
										data-toggle="modal" data-target="#deleteFlight"
										onclick="setData('deleteid',${flight.id})">Delete</button> <a
									href="getFlight?id=${flight.id}"
									class="btn btn-secondary btn-sm" data-toggle="modal"
									data-target="#utilModal">Modify</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:otherwise>
	</c:choose>

	<c:if
		test="${(user.role == 'ADMINISTRATOR' || user.role == 'OPERATOR')}">
		<%@ include file="jspf/flight/deletemodal.jspf"%>
		<%@ include file="jspf/flight/modifymodal.jspf"%>
	</c:if>
	<%@ include file="jspf/utilmodal.jspf"%>
</body>
</html>