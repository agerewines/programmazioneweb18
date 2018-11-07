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
            <form class="form-inline md-form form-sm mt-0">
                <i class="fa fa-search" aria-hidden="true"></i>
                <input class="form-control form-control-sm ml-3 w-75" type="text" placeholder="Search" aria-label="Search">
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
        $('#hiddenProdId').val($(this).data('id'));
    })

</script>
</body>

</html>