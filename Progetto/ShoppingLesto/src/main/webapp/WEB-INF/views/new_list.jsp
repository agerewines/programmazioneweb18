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
    <div class="row justify-content-md-center">
        <div class="col">
        </div>
        <div class="col-md-8">
            <h2>New List</h2>
            <%@include file="parts/_successMessage.jspf" %>
            <form action="${pageContext.request.contextPath}/list/new" method="POST" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" id="name" placeholder="Name" name="name">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="category">Category</label>
                        <select id="category" name="category" class="form-control">
                            <option selected>Choose...</option>
                            <c:forEach items="${listCategories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" name="description " rows="3" placeholder="Description">${list.description}</textarea>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group  col-md-12">
                        <label for="avatar">Add your avatar</label>
                        <input type="file" class="form-control-file" id="avatar" name="avatar">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">New List</button>
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
