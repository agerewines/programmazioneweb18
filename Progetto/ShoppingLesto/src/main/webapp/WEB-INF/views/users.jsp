<%-- 
    Document   : users
    Created on : Sep 27, 2018, 3:42:24 PM
    Author     : alessandrogerevini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Mail</th>
            </tr>
            <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.mail}</td>
            </tr>
            </c:forEach>
        </table>
    </body>
</html>
