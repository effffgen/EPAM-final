<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="mui.jspf" %>
<title>Dashboard</title>
</head>
<body>

<c:forEach items="${requestScope.flights }" var="flight" >
<a href="flightdetails?id=${flight.id }">
${flight }
</a>
<br>

</c:forEach>
</body>
</html>