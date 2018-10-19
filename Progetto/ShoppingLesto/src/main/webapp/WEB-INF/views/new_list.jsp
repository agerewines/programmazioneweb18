<%-- 
    Document   : new_list
    Created on : Oct 19, 2018, 11:41:15 AM
    Author     : Dietre
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

    <title>ShoppingLesto - Webprogramming18</title>

    <%@include file="parts/_imports.jspf" %>

</head>
<body id="page-top">
    <%@include file="parts/_navigation.jspf" %>
    <%@include file="parts/_errors.jspf" %>

    <div class="container-fluid">
        <form>
            <div class="form-group">
                <label for="name">Nome</label>
                <input type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="description">Descrizione</label>
                <input type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="image">Immagine</label>
                <input type="text" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">Crea lista</button>
        </form>
    </div>
    <%@include file="parts/_footer.jspf" %>
    <%@include file="parts/_importsjs.jspf" %>

</body>

</html>
