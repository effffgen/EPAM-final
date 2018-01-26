<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<c:set var="plane" value="${requestScope.flight.plane}" />
	<c:set var="team" value="${requestScope.flight.flightTeam }" />
	<jsp:useBean id="team" class="ua.nure.baranov.entity.Team" />
	<jsp:useBean id="plane" class="ua.nure.baranov.entity.Plane" />
<div class="modal-header"><h5>Flight # ${requestScope.flight.id}</h5></div>
<div class="modal-body">
	<table>
		<tr>
			<td>id</td>
			<td>${requestScope.flight.id}</td>
		</tr>
		<tr>
			<td>From:</td>
			<td>${requestScope.flight.depart.name}</td>
		</tr>
		<tr>
			<td>To:</td>
			<td>${requestScope.flight.destination.name}</td>
		</tr>
		<tr>
			<td>Plane info</td>
			<td>
				<ul>
					<li>Id number: ${plane.id }</li>
					<li>Name: ${plane.name }</li>
				</ul>
			</td>
		</tr>
		<tr>
			<td>Flight team details</td>
			<td><ul>
					<li>First pilot: <c:out value="${team.firstPilot.fullName}">Not assigned</c:out>
					</li>
					<li>Second pilot: <c:out value="${team.secondPilot.fullName}">Not assigned</c:out></li>
					<li>Navigator: <c:out value="${team.aeronavigator.fullName}">Not assigned</c:out></li>
					<li>Attendants (${fn:length(team.attendants)}):<br> <c:choose>
							<c:when
								test="${empty team.attendants or (fn:length(team.attendants)==0)}">
						None
						
						</c:when>
							<c:otherwise>
								<ul>
									<c:forEach items="${team.attendants}" var="attendant">
										<li>${attendant.fullName}</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</li>
				</ul></td>
		</tr>

	</table>
</div>
<div class="modal-footer">
	<c:if test="${(user.role == 'ADMINISTRATOR' || user.role == 'OPERATOR')}">
		<button type="button" class="btn btn-secondary" data-toggle="modal"	data-target="#deleteFlight" onclick="setData('deleteFlight', ${requestScope.flight.id})">Delete this flight</button>
		<a href="getFlight?id=${requestScope.flight.id}" class="btn btn-secondary" data-toggle="modal" data-target="#modify">Modify this flight</a>
	<%@ include file="jspf/flight/deletemodal.jspf" %>
	<%@ include file="jspf/flight/modifymodal.jspf" %>
	</c:if>
</div>