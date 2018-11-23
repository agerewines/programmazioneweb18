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

    <title>ShoppingLesto - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">
    <%@include file="parts/_navigation.jspf" %>
    <%@include file="parts/_errors.jspf" %>

    <div style="width: 100%; margin-top: -0.5%;">
        <iframe src="${map}" width="100%" height="700px" frameborder="0" style="border:0" allowfullscreen></iframe>
    </div>

    <%@include file="parts/_footer.jspf" %>
    <%@include file="parts/_importsjs.jspf" %>
</body>
</html>


