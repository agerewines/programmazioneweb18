<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty param.lang}">
        <c:set var="language" value="${param.lang}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    </c:otherwise>
</c:choose>
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

<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-1 col-lg-2">
        </div>
        <div class="col-lg-8 col-md-10 col-12">
            <h2>Register</h2>
            <form action="${pageContext.request.contextPath}/register" method="POST" id="register">
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
                        <div class="col-sm-6" id="result" style="font-weight:bold;padding:6px 12px; display: inline">
                        </div>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="login.label.password" />">
                    </div>
                    <div class="form-group col-md-5">
                        <label for="confirmation"><fmt:message key="register.label.confirm" /></label>
                        <input type="password" class="form-control" id="confirmation" name="confirmation"
                               placeholder="<fmt:message key="register.label.confirm" />">
                    </div>
                    <div class="form-group col-md-2">
                        <label for="checkTerms"><a href="PrivacyPolicy"><fmt:message key="register.label.check" /></a></label>
                        <input class="form-control" type="checkbox" id="checkTerms" name="checkTerms" value="A"/>
                    </div>
                </div>
                <button type="submit" id="submitRegisterButton" class="btn btn-primary" disabled><fmt:message key="register.button.register" /></button>
            </form>
            <div class="mt-4">
                <hr/>
                <fmt:message key="register.h.if" /> <a class="btn btn-outline-warning" href="${pageContext.request.contextPath}/login"><fmt:message key="register.h.here" /></a>!
            </div>
        </div>

        <div class="col-md-1 col-lg-2">
        </div>
    </div>
</div>
<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
<script type="text/javascript">
    $(document).ready(function() {
        $('#password').on('keyup', function () {
            var strength = 0;
            var password = $('#password').val();
            var confirmation = $("#confirmation").val();

            if (password.length > 7) strength += 1
            if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1
            if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  strength += 1
            if (password.match(/([!,%,&,@,#,$,^,*,?,_,~,.])/))  strength += 1
            if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~,.].*[!,%,&,@,#,$,^,*,?,_,~,.])/)) strength += 1
            if (strength <= 3) {
                $('#submitRegisterButton').prop('disabled', true);
                $('#result').html('Password debole').css('color', 'red');
            } else {
                $('#submitRegisterButton').prop('disabled', false);
                $('#result').html('')
            }
        });

    });
</script>
</body>

</html>