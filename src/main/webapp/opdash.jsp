<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="jspf/mui.jspf"%>
<title>Dashboard</title>
</head>
<body>
	<table>
		<c:forEach items="${requestScope.flights }" var="flight">
			<tr>
				<td><a href="flightdetails?id=${flight.id }"> ${flight } </a></td>
				<c:if
					test="${(user.role == 'ADMINISTRATOR' || user.role == 'OPERATOR')}">
					<td>DELETE</td>
					<td>MODIFY ${sessionScope.user.role }</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
</body>
</html>