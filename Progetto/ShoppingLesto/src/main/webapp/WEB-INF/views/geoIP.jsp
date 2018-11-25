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
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
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
<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col-md-1 col-lg-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <h2><fmt:message key="home.li.lists"/></h2>
            <ul class="nav nav-pills mb-3 nav-fill" id="pills-tab" role="tablist">
                <c:forEach items="${userLists}" var="list">
                    <li class="nav-item">
                        <a class="btn btn-warning m-2 showMapButton" target="map" data-toggle="pill" href="" data-id-category="${list.categoryId}" role="tab">
                            ${list.name}
                            <br/>
                            ${list.user.firstName} • ${list.category.name}
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <div class="tab-content">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d22154.981200143142!2d11.1333!3d46.0667!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1sMarket!5e0!3m2!1sit!2sit!4v1542911270560" width="100%" height="600px" frameborder="0" style="border:0" allowfullscreen id="map"></iframe>
            </div>
        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
<script type="text/javascript">
    $(".showMapButton").click(function(){
        let catId = $(this).data("id-category");
        $.ajax({
            type: "POST",
            url: "geoip",
            data: {
                catId: catId
            },
            dataType : "html",
            success: function (html) {
                $("#map").attr("src", html);
                $(this).removeClass("active");
                $(this).addClass("active");
            }
        });
    })
</script>
</body>
</html>


