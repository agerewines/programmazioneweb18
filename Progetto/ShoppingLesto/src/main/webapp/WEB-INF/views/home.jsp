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
            <%@include file="parts/_successMessage.jspf" %>

            <h2>
                <ul class="list-inline">
                    <li class="list-inline-item">Lists</li>
                    <li class="list-inline-item"><a class="btn btn-primary"
                                                    href="${pageContext.request.contextPath}/list/new">Add new list</a>
                    </li>
                </ul>
            </h2>

            <div class="list-group">
                <c:forEach items="${userLists}" var="list">
                    <a href="${pageContext.request.contextPath}/list?id=${list.id}"
                       class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="d-flex w-100 justify-content-between">
                            <div class="media">
                                <img id="listPic" class="align-self-center mr-3 rounded" height="64" width="64" src="${pageContext.request.contextPath}/images?id=${list.id}&resource=shoppingLists" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Lists/default.png';"/>
                                <div class="media-body">
                                    <h5 class="mb-1">${list.name}</h5>
                                    <p class="mb-1">${list.description}</p>
                                </div>
                            </div>
                            <small>${list.user.fullName}</small>
                        </div>
                        <small></small>
                    </a>
                </c:forEach>
            </div>
        </div>
        <div class="col">

        </div>
    </div>
</div>
<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

</body>

</html>
