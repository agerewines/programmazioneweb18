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

    <%@include file="../parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="../parts/_navigation.jspf" %>
<%@include file="../parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
        </div>
        <div class="col-lg-8 col-md-8 col-12">
            <h2>Error 404</h2>
            <h5>Exception is: <%= exception %></h5>
        </div>
        <div class="col-md-2">
        </div>
    </div>
</div>
<%@include file="../parts/_footer.jspf" %>
<%@include file="../parts/_importsjs.jspf" %>
</body>

</html>