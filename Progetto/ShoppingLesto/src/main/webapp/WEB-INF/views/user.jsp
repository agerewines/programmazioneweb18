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

    <title>ShoppingLesto | User ${user.fullName} - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col-md-2">
        </div>
        <div class="col-lg-8 col-md-8 col-12">
            <%@include file="parts/_successMessage.jspf" %>
            <h2>
                ${user.fullName}
            </h2>
            <div class="media">
                <img  class="align-self-center mr-3 rounded" id="profilePic" height="150" width="150" src="${pageContext.request.contextPath}/images?id=${user.id}&resource=user" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Users/default.jpeg';"/>
                <div class="media-body">
                    <ul class="list-inline">
                        <li class="list-inline-item"><h5>Mail:</h5></li>
                        <li class="list-inline-item">${user.mail}</li>
                    </ul>
                </div>
            </div>
            <br/>
            <h2>Modifica</h2>
            <form action="${pageContext.request.contextPath}/user/profile" method="POST" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="firstName">First Name</label>
                        <input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName"
                               value="${user.firstName}">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="lastName">Last Name</label>
                        <input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName"
                               value="${user.lastName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="avatar">Add your avatar</label>
                        <input type="file" class="form-control-file" id="avatar" name="avatar">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Modify</button>
            </form>
        </div>
        <div class="col-md-2">
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
</body>

</html>