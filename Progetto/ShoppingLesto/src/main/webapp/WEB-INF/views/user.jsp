<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 18/10/2018
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ShoppingLesto | User ${user.firstName} ${user.lastName}- Webprogramming18</title>

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
                Your account <a href="#" class="badge badge-primary">Modify</a>
            </h2>
            <div class="row">
                <div class="col-4">
                    Avatar: ${user.avatar}
                </div>
                <div class="col-4">
                    Nome:
                </div>
                <div class="col-4">
                    ${user.firstName}
                </div>
            </div>
            <div class="row">
                <div class="col-4">
                </div>
                <div class="col-4">
                    Cognome:
                </div>
                <div class="col-4">
                    ${user.lastName}
                </div>
            </div>
            <div class="row">
                <div class="col-4">
                </div>
                <div class="col-4">
                    mail:
                </div>
                <div class="col-4">
                    ${user.mail}
                </div>
            </div>

            <h2>Modifica</h2>
            <form action="${pageContext.request.contextPath}/user/profile" method="POST">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="firstName">First Name</label>
                        <input type="text" class="form-control" id="firstName" placeholder="First Name" name="firstName"
                               value="${firstName}">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="lastName">Last Name</label>
                        <input type="text" class="form-control" id="lastName" placeholder="Last Name" name="lastName"
                               value="${lastName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group  col-md-6">
                        <label for="avatar">Add your avatar</label>
                        <input type="file" class="form-control-file" id="avatar">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Modify</button>
            </form>
        </div>
        <div class="col">
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
</body>

</html>