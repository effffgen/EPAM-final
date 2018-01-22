<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="jspf/header.jspf"%>
<script>
function showdetails(id){
	alert('Kek');
	
}

</script>
<title>Requests</title>
</head>
<body>
	<ul id="reqs">
		<c:forEach items="${requestScope.requests }" var="req">
			<li onclick="showdetails(${req.id})">${req }</li>
		</c:forEach>
	</ul>
	
</body>
</html>