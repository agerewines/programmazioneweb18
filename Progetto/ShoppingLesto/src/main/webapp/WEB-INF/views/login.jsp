<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 12/10/2018
  Time: 09:38
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

    <title>ShoppingLesto | Login - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-6">
            <h2>Login</h2>
            <form  method="POST" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter email"  value="${user.mail}">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password" value="${user.password}" placeholder="Password">
                </div>
                <div class="form-group form-check">
                    <input type="checkbox" class="form-check-input" id="rememberMe"  name="rememberMe" value="Y">
                    <label class="form-check-label" for="rememberMe" >Remember me</label>
                </div>
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            <div class="mt-4">
                <hr/>
                If you are not registered yet, you can do it <a class="badge-pill btn-primary" href="${pageContext.request.contextPath}/register">here</a>!
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