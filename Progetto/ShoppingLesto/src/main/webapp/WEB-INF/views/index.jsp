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
<body id="page-top" style="margin-top: 0%">

<%@include file="parts/_errors.jspf" %>

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="${pageContext.request.contextPath}/home">ShoppingLesto</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse"
                data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false"
                aria-label="Toggle navigation">
            Menu
            <i class="fas fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav text-uppercase ml-auto">
            </ul>
        </div>
    </div>
</nav>

<!-- Header -->
<header class="masthead">
    <div class="container">
        <div class="intro-text">
            <div class="intro-heading text-uppercase">Benvenuto su ShoppingLesto</div>
            <div class="intro-lead-in">Compri male ma compri presto</div>
            <a class="btn btn-primary btn-xl text-uppercase js-scroll-trigger" style="margin: 1%"
               href="${pageContext.request.contextPath}/login">Login</a>
            <a class="btn btn-primary btn-xl text-uppercase js-scroll-trigger" style="margin: 1%"
               href="${pageContext.request.contextPath}/register">Register</a>
            <a class="btn btn-primary btn-xl text-uppercase js-scroll-trigger" style="margin: 1%"
               href="${pageContext.request.contextPath}/home">Anonimo</a>
        </div>
    </div>
</header>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

</body>

</html>
