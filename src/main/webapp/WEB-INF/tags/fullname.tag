<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="user" required="true" rtexprvalue="true" %>
<jsp:useBean id="user" class="ua.nure.baranov.entity.User"/>
${user.firstName} ${user.lastName}