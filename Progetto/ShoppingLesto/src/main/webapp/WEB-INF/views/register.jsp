<%-- 
    Document   : register
    Created on : Oct 3, 2018, 4:37:26 PM
    Author     : alessandrogerevini
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

    <title>ShoppingLesto | Register - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">
<div class="container-fluid">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-6" style="margin:7%;">
            <h2>Register</h2>
            <%@include file="parts/_navigation.jspf" %>
            <c:if test="${not empty errorMessage}">
                <c:out value="${errorMessage}"/>
            </c:if>
            <form action="${pageContext.request.contextPath}/register" method="POST">
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
                    <div class="form-group col-md-12">
                        <label for="mail">Email</label>
                        <input type="email" class="form-control" id="mail" placeholder="Email" name="mail"
                               value="${mail}">
                    </div>

                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Password">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="confirmation">Confirm password</label>
                        <input type="password" class="form-control" id="confirmation" name="confirmation"
                               placeholder="Confirm Password">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="checkTerms">Check terms</label>
                        <input class="form-control" type="checkbox" id="checkTerms" name="checkTerms" value="A"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Register</button>
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