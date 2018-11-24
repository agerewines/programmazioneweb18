<%--
  Created by IntelliJ IDEA.
  User: samuele
  Date: 05/11/18
  Time: 20.08
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<!DOCTYPE html>
<html lang="${language}">

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
        <div class="col-md-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <table style="width: 100%;">
                <td style="width: 33%">
                    <h2>
                        <ul class="list-inline">
                            <li class="list-inline-item"><fmt:message key="home.li.lists"/></li>
                        </ul>
                    </h2>

                    <div class="list-group">
                        <c:forEach items="${userLists}" var="list">
                        <c:choose>
                        <c:when test="${anon}">
                        <a href="${pageContext.request.contextPath}/list?id=${list.id}&anonymous=true"
                           class="list-group-item list-group-item-action flex-column align-items-start">
                            </c:when>
                            <c:otherwise>
                            <a href="${pageContext.request.contextPath}/geoip?id=${list.id}"
                               class="list-group-item list-group-item-action flex-column align-items-start">
                                </c:otherwise>
                                </c:choose>
                                <div class="d-flex w-100 justify-content-between">
                                    <div class="media">
                                        <img id="listPic" class="align-self-center mr-3 rounded" height="64" width="64"
                                             src="${pageContext.request.contextPath}/images?id=${list.id}&resource=shoppingLists"
                                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Lists/default.png';"/>
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
                </td>
                <td style="width: 66%">
                    <div style="width: 100%">
                        <iframe src="${map}" width="100%" height="700px" frameborder="0" style="border:0"
                                allowfullscreen></iframe>
                    </div>
                </td>
            </table>
        </div>
    </div>
</div>


<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
</body>
</html>


