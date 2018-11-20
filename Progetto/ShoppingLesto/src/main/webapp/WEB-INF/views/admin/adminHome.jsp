<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 28/10/2018
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>ShoppingLesto | Admin Panel - Webprogramming18</title>

    <%@include file="../parts/_imports.jspf" %>

</head>
<body id="page-top">

<%@include file="../parts/_navigation.jspf" %>
<%@include file="../parts/_errors.jspf" %>

<div class="container-fluid">
    <div class="row justify-content-md-center">
        <div class="col-md-1 col-lg-1 hidden-sm-down">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <%@include file="../parts/_successMessage.jspf" %>
            <h2>Admin Panel</h2>
            <!-- Prodotti -->
            <ul class="list-inline">
                <li class="list-inline-item">
                    <h5>Manage the available products</h5>
                </li>
                <li class="list-inline-item">
                    <button type="button" class="btn btn-primary addProduct"
                            style="padding: 0 .375rem 0 .375rem;"
                            data-toggle="modal" data-target="#addProductModal">
                        <i class="fas fa-plus"></i>
                    </button>
                </li>
            </ul>
            <p><small>Click on the images in order to delete them.</small></p>
            <table class="table table-striped" id="productTable">
                <thead>
                <tr>
                    <th style="width: 10%" scope="col">Photos</th>
                    <th style="width: 25%" scope="col">Name</th>
                    <th style="width: 50%" scope="col">Description</th>
                    <th style="width: 5%" scope="col">Price</th>
                    <th style="width: 10%" scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${products}" var="prod">
                    <tr id="prod${prod.id}">
                        <td>
                            <c:forEach items="${prod.photos}" var="photo">
                                <img class="rounded shadow mb-3 bg-white rounded deletePhoto image" height="65" width="65"
                                     src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=products"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"
                                     onclick="deleteProductPhoto(${photo.id}, this)"
                                     onmouseover="mouseOverPhoto(this)" title="Click on the picture to delete it!"/>
                            </c:forEach>
                        </td>
                        <td>${prod.name}</td>
                        <td>${prod.description}</td>
                        <td>${prod.price} €</td>
                        <td>
                            <ul class="list-inline">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary modifyProd"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#modifyProdModal"
                                            data-id="${prod.id}"
                                            data-name="${prod.name}"
                                            data-desc="${prod.description}"
                                            data-cat="${prod.categoryId}"
                                            data-price="${prod.price}">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                </li>
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary deleteProd"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#deleteProdModal" data-id="${prod.id}">
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
        <div class="col-md-1 col-lg-1 hidden-sm-down">
        </div>

<!-- Product -->
<!-- Modal add product button -->
<div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProdModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addProdModalLabel">Add Product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/new" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameProd">Name</label>
                            <input type="text" class="form-control" id="nameProd" placeholder="Name" name="nameProd" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionProd">Description</label>
                            <textarea class="form-control" id="descriptionProd" name="descriptionProd" rows="3"
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="priceProd">Price</label>
                            <input type="number" class="form-control" id="priceProd" placeholder="Price" name="price" step="0.01" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="productCategory">Choose category</label>
                        <div class="form-group col-md-10 col-10">
                            <select id="productCategory" name="category" class="form-control" required>
                            </select>
                        </div>
                        <div class="form-group col-md-1 col-2">
                            <button type="button" class="btn btn-primary addProdCat"
                                    style="padding: 0 .375rem 0 .375rem;"
                                    data-toggle="modal" data-target="#addProdCatModal" data-dismiss="modal">
                                <i class="fas fa-plus"></i>
                            </button>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo2" name="photo">
                            <label class="custom-file-label" for="photo2">Add photo</label>
                        </div>
                    </div>
                    <hr/>
                    <button type="submit" class="btn btn-primary">Add product</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal edit product button -->
<div class="modal fade" id="modifyProdModal" tabindex="-1" role="dialog" aria-labelledby="modifyProdModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyProdModalLabel">Edit Product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/edit" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameProdEdit">Name</label>
                            <input type="text" class="form-control" id="nameProdEdit" placeholder="Name" name="nameProd" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionProdEdit">Description</label>
                            <textarea class="form-control" id="descriptionProdEdit" name="descriptionProd" rows="3"
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="priceProdEdit">Price</label>
                            <input type="number" class="form-control" id="priceProdEdit" placeholder="Price" name="priceEdit" step="0.01" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="productCategoryEdit">Choose category</label>
                            <select id="productCategoryEdit" name="category" class="form-control">
                            </select>
                    </div>
                    <div class="form-row mt-4">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo" name="photo">
                            <label class="custom-file-label" for="photo">Add photo</label>
                        </div>
                    </div>
                    <hr/>
                    <input type="hidden" name="prodId" id="hiddenProductEditId">
                    <button type="submit" class="btn btn-primary">Edit product</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal delete product button -->
<div class="modal fade" id="deleteProdModal" tabindex="-1" role="dialog" aria-labelledby="deleteProdModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteProdModalLabel">Delete Product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="deleteProductForm" action="${pageContext.request.contextPath}/product/delete" method="POST">
                    <div class="form-row">
                        <label>Are you sure you want to delete this product<br>You will delete this product from every list</label>
                    </div>
                    <input type="hidden" id="hiddenProdDeleteId" name="prodId">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" id="deleteProductButton" class="btn btn-primary">Delete product</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../parts/_footer.jspf" %>
<%@include file="../parts/_importsjs.jspf" %>

<script type="text/javascript">
    $('.addProduct').click(function(){       // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.get("product/category/all")
            .done(function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                var opts = "";
                $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
                    opts += "<option value='" + key + "'>" + value + "</option>";
                });
                $("#productCategory").append(opts);
            });
    });


    $('.modifyProd').click(function(){       // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $('#nameProdEdit').val($(this).data('name'));
        $('#priceProdEdit').val($(this).data('price'));
        $('#descriptionProdEdit').val($(this).data('desc'));
        $('#hiddenProductEditId').val($(this).data('id'));
        $('#productCategoryEdit').val($(this).data('cat'));
        var catId = $(this).data('cat');
        $.get("product/category/all")
            .done(function(responseJson) {
                var opts = "";
                $.each(responseJson, function(key, value) {
                    if(key != catId){
                        opts += "<option value='" + key + "'>" + value + "</option>";
                    }
                    else{
                        opts += "<option value='" + key + "' selected>" + value + "</option>";
                    }
                });
                $("#productCategoryEdit").append(opts);
            });
    });

    $('.deleteProd').click(function(){
        $('#hiddenProdDeleteId').val($(this).data('id'));
    });

    function mouseOverPhoto(elem){
        $(elem).css( 'cursor', 'pointer' );
    }

    function deleteProductPhoto(id, elem) {        // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.post("product/photo/delete",
            {
                prodPhotoId : id
            }).done(function(){});
        $(elem).fadeOut(300, function() { $(this).remove(); });
        $(elem).tooltip( "option", "disabled" );
    }


    $("#deleteProductForm").submit(function(e) {
        var form = $(this);
        var url = form.attr('action');
        var myData = $("#hiddenProdDeleteId").val();
        $.ajax({
            type: "POST",
            url: url,
            data: myData
        }).done(function() {
            var elem = document.getElementById("prod" + myData);
            $(elem).fadeOut(300, function() { $(this).remove(); });
        }).fail(function() {
            alert("error deleting product");
        });

        e.preventDefault(); // avoid to execute the actual submit of the form.
    });

    $('table').DataTable({
        "order" : [[1, "asc"]],
        "language" : {
            "url" : getLang()
        }
    });

    var lang = document.documentElement.lang;
    function getLang(){
        switch (lang){
            case "it_IT" : return "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Italian.json";
                break;
            case "en_US" : return "https://cdn.datatables.net/plug-ins/1.10.19/i18n/English.json";
                break;
            default :  return "https://cdn.datatables.net/plug-ins/1.10.19/i18n/English.json";
                break;
        }
    };


</script>
</body>

</html>