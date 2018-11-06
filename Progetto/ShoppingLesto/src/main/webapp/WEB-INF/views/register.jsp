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

    <title>ShoppingLesto | Register - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">
<div class="container-fluid">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-6">
            <h2>Register</h2>
            <%@include file="parts/_navigation.jspf" %>
            <c:if test="${not empty errorMessage}">
                <c:out value="${errorMessage}"/>
            </c:if>
            <form action="${pageContext.request.contextPath}/register" method="POST">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="firstName"><fmt:message key="register.label.first_name" /></label>
                        <input type="text" class="form-control" id="firstName" placeholder="<fmt:message key="register.label.first_name" />" name="firstName"
                               value="${firstName}">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="lastName"><fmt:message key="register.label.last_name" /></label>
                        <input type="text" class="form-control" id="lastName" placeholder="<fmt:message key="register.label.last_name" />" name="lastName"
                               value="${lastName}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="mail"><fmt:message key="login.label.email" /></label>
                        <input type="email" class="form-control" id="mail" placeholder="<fmt:message key="login.label.email" />" name="mail"
                               value="${mail}">
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-5">
                        <label for="password"><fmt:message key="login.label.password" /></label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="login.label.password" />">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="confirmation"><fmt:message key="register.label.confirm" /></label>
                        <input type="password" class="form-control" id="confirmation" name="confirmation"
                               placeholder="<fmt:message key="register.label.confirm" />">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="checkTerms"><fmt:message key="register.label.check" /></label>
                        <input class="form-control" type="checkbox" id="checkTerms" name="checkTerms" value="A"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="register.button.register" /></button>
            </form>
            <div class="mt-4">
                <hr/>
                <fmt:message key="register.h.if" /> <a class="badge-pill btn-primary" href="${pageContext.request.contextPath}/login"><fmt:message key="register.h.here" /></a>!
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