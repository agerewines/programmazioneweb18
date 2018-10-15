<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 11/10/2018
  Time: 09:34
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty errorMessage}">
        <c:out value="${errorMessage}"/>
        <c:remove var="errorMessage"/>
    </c:when>
</c:choose>

Benvenuto su ShoppingLesto - Home
<a href="login.jsp">Login</a>
</body>
</html>