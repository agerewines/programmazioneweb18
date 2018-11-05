<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 27/10/2018
  Time: 21:25
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

    <title>ShoppingLesto | Add Product - Webprogramming18</title>

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
            <%@include file="parts/_successMessage.jspf" %>
            <!-- Search form -->
            <form class="form-inline" href="${pageContext.request.contextPath}/product/search" method="POST">
                <div class="input-group mb-2 mr-sm-2 flex-fill">
                    <div class="input-group-prepend">
                        <div class="input-group-text"><i class="fas fa-search"></i></div>
                    </div>
                    <input type="text" class="form-control" id="search" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Search</button>
            </form>
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th scope="col">Photo</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Add</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="prod">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${ empty prod.photos}">
                                    <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                         src="${pageContext.request.contextPath}/images/avatars/Products/default.png" alt="default product photo"/>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${prod.photos}" var="photo">
                                        <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                             src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=listCatPhoto"
                                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"/>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${prod.name}</td>
                        <td>${prod.description}</td>
                        <td>
                            <button type="button" class="btn btn-primary addButton"
                                    style="padding: 0 .375rem 0 .375rem;"
                                    data-toggle="modal" data-target="#addProductModal" data-id="${prod.id}">
                                <i class="fas fa-plus"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
        </div>
    </div>
</div>

<!-- Modal product button -->
<div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addProductModalLabel">Add product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/add" method="POST">
                    <label>Are you sure you want to add this product?</label> <br/>
                    <input id="hiddenProdId" type="hidden" name="prodId">
                    <input id="hiddenListId" type="hidden" name="listId" value="${param.listId}">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Add</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

<script type="text/javascript">
        // language=JQuery-CSS
        $('.addButton').click(function () {
            var prodId = $(this).data('id');// Extract info from data-* attributes
            // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
            // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
            $('#hiddenProdId').val(prodId);
        })

</script>

</body>

</html>