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
<c:choose>
    <c:when test="${not empty param.lang}">
        <c:set var="language" value="${param.lang}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    </c:otherwise>
</c:choose>
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
<style>
    .active{
        background-color: #ffc107 !important;
        border-color: #ffc107 !important;
    }
    .showMapButton:hover{
        background-color: #ccc !important;
        border-color: #bbb !important;
    }

    .active:hover{
        background-color: #e0a800 !important;
        border-color: #d39e00 !important;
    }
</style>
</head>
<body id="page-top">
<%@include file="parts/_navigation.jspf" %>
<%@include file="parts/_errors.jspf" %>
<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col-md-1 col-lg-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <div class="row">
                <div class="col-12 col-md-12 col-lg-3 mb-4">
                    <h2><fmt:message key="home.li.lists"/></h2>
                    <div class="list-group" id="pills-tab">
                        <c:forEach items="${userLists}" var="list">
                            <a class="list-group-item list-group-item-action showMapButton" data-id-category="${list.categoryId}">
                                <div class="media">
                                    <img id="listPic" class="align-self-center mr-3 rounded" height="64" width="64" src="${pageContext.request.contextPath}/images?id=${list.id}&resource=shoppingLists" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Lists/default.png';"/>
                                    <div class="media-body">
                                        <h5 class="mb-1">${list.name}</h5>
                                        <p>${list.user.fullName} • ${list.category.name}</p>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-12 col-md-12 col-lg-9 mb-4">
                    <div class="d-none d-lg-block" id="mapLabel"><h2><fmt:message key="home.h2.map"/></h2></div>
                    <div class="tab-content">
                        <iframe class="rounded" src="https://www.google.com/maps/embed?pb=!1m16!1m12!1m3!1d22154.981200143142!2d11.1333!3d46.0667!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!2m1!1sMarket!5e0!3m2!1sit!2sit!4v1542911270560" width="100%" height="700px" style="border: none;" allowfullscreen id="map"></iframe>
                    </div>
                </div>
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
        $('.showMapButton').removeClass('active');
        $(this).addClass('active');
        $.ajax({
            type: "POST",
            url: "geoip",
            data: {
                catId: catId
            },
            dataType : "html",
            success: function (html) {
                $("#map").attr("src", html);
                if(!$('#mapLabel').is(':visible')){
                    $('html, body').animate({
                        scrollTop: $("#map").offset().top
                    },1000);
                }
            }
        });
    })
</script>
</body>
</html>


