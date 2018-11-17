<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 15/11/2018
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<!DOCTYPE html>
<html lang="${language}">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ShoppingLesto | Error 404 - Webprogramming18</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/notfound.css" />
    <%@include file="../parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="../parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <div id="notfound">
                <div class="notfound">
                    <div class="notfound-404">
                        <h3>Oops! Page not found</h3>
                        <h1><span>4</span><span>0</span><span>4</span></h1>
                    </div>
                    <h2>Your ship got lost in time and space</h2>
                    <small>we are sorry, but the page you requested was not found</small>
                </div>
            </div>

        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>
<%@include file="../parts/_importsjs.jspf" %>
</body>

</html>