<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 28/10/2018
  Time: 16:36
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

    <title>ShoppingLesto | Admin Panel - Webprogramming18</title>

    <%@include file="../parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="../parts/_navigation.jspf" %>
<%@include file="../parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col">
        </div>
        <div class="col-md-8">
            <%@include file="../parts/_successMessage.jspf" %>
            <h2>Admin Panel</h2>
            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link" id="pills-listCat-tab" data-toggle="pill" href="#pills-listCat" role="tab" aria-controls="pills-listCat" aria-selected="true">Category of lists</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="pills-prod-tab" data-toggle="pill" href="#pills-prod" role="tab" aria-controls="pills-prod" aria-selected="false">Products</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="pills-prodCat-tab" data-toggle="pill" href="#pills-prodCat" role="tab" aria-controls="pills-prodCat" aria-selected="false">Category of products</a>
                </li>
            </ul>
            <!-- Categoria di liste -->
            <div class="tab-content" id="pills-tabContent">
                <div class="tab-pane fade" id="pills-listCat" role="tabpanel" aria-labelledby="pills-listCat-tab">
                    <h5>Manage the category of lists</h5>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Image</th>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Edit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listCategory}" var="listCat">

                            <tr>
                                <td>
                                    <c:forEach items="${listCat.photos}" var="photo">
                                        <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                             src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=listCatPhoto"
                                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"/>
                                    </c:forEach>
                                </td>
                                <td>${listCat.name}</td>
                                <td>${listCat.description}</td>
                                <td>
                                    <ul class="list-inline">
                                            <li class="list-inline-item">
                                                <button type="button" class="btn btn-primary"
                                                        style="padding: 0 .375rem 0 .375rem;"
                                                        data-toggle="modal" data-target="#modifyListCatModal" data-id-listCat="${listCat.id}">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                            </li>
                                        <li class="list-inline-item">
                                            <button type="button" class="btn btn-primary"
                                                    style="padding: 0 .375rem 0 .375rem;"
                                                    data-toggle="modal" data-target="#deleteListCatModal" data-id-listCat="${listCat.id}">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- Prodotti -->
                <div class="tab-pane fade" id="pills-prod" role="tabpanel" aria-labelledby="pills-prod-tab">
                    <h5>Manage the available products</h5>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Edit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${products}" var="prod">
                            <tr>
                                <td>${prod.name}</td>
                                <td>${prod.description}</td>
                                <td>@mdo</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <!-- Categoria di prodotti -->
                <div class="tab-pane fade" id="pills-prodCat" role="tabpanel" aria-labelledby="pills-prodCat-tab">
                    <h5>Manage the category of products</h5>
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Description</th>
                            <th scope="col">Edit</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${prodCategory}" var="prodCat">
                            <tr>
                                <td>
                                    <c:forEach items="${prodCat.photos}" var="photo">
                                    <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                         src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=listCatPhoto"
                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"/>
                                </c:forEach>
                                </td>
                                <td>${prodCat.name}</td>
                                <td>${prodCat.description}</td>
                                <td>@mdo</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>


<!-- Modal edit list category button -->
<div class="modal fade" id="modifyListCatModal" tabindex="-1" role="dialog" aria-labelledby="modifyListCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyListCatModalLabel">Edit List Category</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/editCategory" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameListCat">Name</label>
                            <input type="text" class="form-control" id="nameListCat" placeholder="Name" name="nameListCat">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionListCat">Description</label>
                            <textarea class="form-control" id="descriptionListCat" name="descriptionListCat" rows="3"
                                      placeholder="Description">${list.description}</textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group  col-md-6">
                            <label for="photo">Add photo</label>
                            <input type="file" class="form-control-file" id="photo" name="photo"
                                   value="${list.image}">
                        </div>
                    </div>
                    <input type="text" name="listCatIdHidden">
                    <button type="submit" class="btn btn-primary">Edit list category</button>
                </form>
            </div>
        </div>
    </div>
</div>


<%@include file="../parts/_footer.jspf" %>
<%@include file="../parts/_importsjs.jspf" %>
<script type="text/javascript">
/*
    $('#modifyListCatModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var idListCat = button.data('id-listCat');// Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this);
        // language=JQuery-CSS
        $("#listCatIdHidden").val("valore");
    })
*/

</script>
</body>

</html>