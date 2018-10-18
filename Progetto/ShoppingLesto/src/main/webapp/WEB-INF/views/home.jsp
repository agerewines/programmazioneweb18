<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 11/10/2018
  Time: 09:34
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ShoppingLesto - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">
<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>
<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col">
        </div>
        <div class="col-md-8">
            <h2>Lists</h2>
            <div class="list-group">
                <c:forEach items="${userLists}" var="list">
                    <a href="${pageContext.request.contextPath}/list?id=${list.id}" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${list.name}</h5>
                            <small>${list.user.firstName} ${list.user.lastName}</small>
                        </div>
                        <p class="mb-1">${list.description}</p>
                        <small></small>
                    </a>
                </c:forEach>
            </div>
        </div>
        <div class="col">
            <button type="button" class="btn btn-outline-primary" href="${pageContext.request.contextPath}/">Add new list</button>
        </div>
    </div>
</div>
<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

</body>

</html>
