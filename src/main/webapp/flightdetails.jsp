<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="mui.jspf"%>
<title>${requestScope.flight.id}</title>
</head>
<body>
	<table>
		<tr>
			<td>id</td>
			<td>${requestScope.flight.id}</td>
		</tr>
		<tr>
			<td>Departure city</td>
			<td>${requestScope.flight.depart.name}</td>
		</tr>
		<tr>
			<td>Destination city</td>
			<td>${requestScope.flight.destination.name}</td>
		</tr>
		<tr>
			<td>Plane info</td>
			<td>${requestScope.flight.plane}</td>
		</tr>
		<tr>
			<td>Flight team details</td>
			<td>${requestScope.flight.flightTeam}</td>
		</tr>

	</table>
</body>
</html>