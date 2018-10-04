<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>

        <c:choose>
            <c:when test="${not empty errorMessage}">
                <c:out value="${errorMessage}"/>
                <c:remove var="errorMessage"/>
            </c:when>
        </c:choose>
    </body>
</html>