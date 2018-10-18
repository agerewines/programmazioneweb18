<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 18/10/2018
  Time: 16:00
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

    <title>ShoppingLesto | List ${list.name} - Webprogramming18</title>

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
            <h2>
                <ul class="list-inline">
                    <li class="list-inline-item">List: </li>
                    <li class="list-inline-item">${list.name}</li>
                </ul>
            </h2>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Prodotto</th>
                    <th scope="col">Descrizione</th>
                    <th scope="col">Logo</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productsList}" var="prod">
                    <tr>
                        <td>${prod.name}</td>
                        <td>${prod.description}</td>
                        <td>${prod.logo}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
</body>

</html>