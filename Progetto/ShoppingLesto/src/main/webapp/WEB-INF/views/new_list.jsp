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
                        <!-- Button trigger modal
                        <button type="button" class="btn btn-primary float-right" style="padding: 0 .375rem 0 .375rem;" data-toggle="modal" data-target="#categoryModal">
                            <i class="fas fa-edit"></i>
                        </button> -->
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
                        <input type="text" class="form-control" id="description" placeholder="Description"
                               name="description">
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


    <!-- Modal
    <div class="modal fade" id="categoryModal" tabindex="-1" role="dialog" aria-labelledby="categoryModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="categoryModalLabel">Category maganer</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Photo</th>
                            <th scope="col">Modify</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listCategories}" var="category">
                            <tr>
                                <td>${category.name}</td>
                                <td>${category.description}</td>
                                <td>null</td>
                                <td><i class="fas fa-pen"></i></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <form action="${pageContext.request.contextPath}/list/category/new" method="POST">
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="nameCat">Name</label>
                                <input type="text" class="form-control" id="nameCat" placeholder="Name" name="nameCat">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="descriptionCat">Description</label>
                                <input type="text" class="form-control" id="descriptionCat" placeholder="Description"
                                       name="descriptionCat">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">New list category</button>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>-->
    </div>
</div>
<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

</body>

</html>
