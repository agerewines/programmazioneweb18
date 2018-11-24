<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 16/11/2018
  Time: 09:24
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty param.lang}">
        <c:set var="language" value="${param.lang}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    </c:otherwise>
</c:choose>
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
        <div class="col-md-1 col-lg-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <%@include file="../parts/_successMessage.jspf" %>
            <h2>Admin Panel</h2>
            <ul class="list-inline">
                <li class="list-inline-item">
                    <h5><fmt:message key="admin.h.manage_product"/></h5>
                </li>
                <li class="list-inline-item">
                    <button type="button" class="btn btn-primary addProdCat"
                            style="padding: 0 .375rem 0 .375rem;"
                            data-toggle="modal" data-target="#addProdCatModal">
                        <i class="fas fa-plus"></i>
                    </button>
                </li>
            </ul>
            <p><small><fmt:message key="admin.h.image"/></small></p>
            <table class="table table-striped display nowrap">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col"><fmt:message key="admin.h.name"/></th>
                    <th scope="col"><fmt:message key="admin.h.description"/></th>
                    <th scope="col"><fmt:message key="admin.h.photos"/></th>
                    <th scope="col"><fmt:message key="admin.h.edit"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${prodCategory}" var="prodCat">
                    <tr id="prodCat${prodCat.id}">
                        <td></td>
                        <td>${prodCat.name}</td>
                        <td>${prodCat.description}</td>
                        <td>
                            <c:forEach items="${prodCat.photos}" var="photo">
                                <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                     src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=prodCatPhoto"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"
                                     onmouseover="mouseOverPhoto(this)"
                                     onclick="deleteProdCatPhoto(${photo.id}, this)"  title="Click on the picture to delete it!"/>
                            </c:forEach>
                        </td>
                        <td>
                            <ul class="list-inline">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary modifyProdCat"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#modifyProdCatModal"
                                            data-id="${prodCat.id}"
                                            data-name="${prodCat.name}"
                                            data-desc="${prodCat.description}">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                </li>
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary deleteProdCat"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#deleteProdCatModal" data-id="${prodCat.id}">
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
        <div class="col-md-1 col-lg-1">
        </div>
    </div>
</div>
<!-- Product Category -->
<!-- Modal add list category button -->
<div class="modal fade" id="addProdCatModal" tabindex="-1" role="dialog" aria-labelledby="addProdCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addProdCatModalLabel"><fmt:message key="admin.h.add_product_cat"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/category/new" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameProdCat"><fmt:message key="admin.h.name"/></label>
                            <input type="text" class="form-control" placeholder="Name" name="nameProdCat">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionProdCat"><fmt:message key="admin.h.description"/></label>
                            <textarea class="form-control" name="descriptionProdCat" rows="3"
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo" name="photo">
                            <label class="custom-file-label" for="photo"><fmt:message key="admin.h.add_photo"/></label>
                        </div>
                    </div>
                    <hr/>
                    <button type="submit" class="btn btn-primary"><fmt:message key="admin.h.add_product_cat"/></button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal edit prod category button -->
<div class="modal fade" id="modifyProdCatModal" tabindex="-1" role="dialog" aria-labelledby="modifyProdCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyProdCatModalLabel"><fmt:message key="admin.h.edit_list"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/category/edit" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameProdCat"><fmt:message key="admin.h.name"/></label>
                            <input type="text" class="form-control" id="nameProdCat" placeholder="Name" name="nameProdCat">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionProdCat"><fmt:message key="admin.h.description"/></label>
                            <textarea class="form-control" id="descriptionProdCat" name="descriptionProdCat" rows="3"
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo2" name="photo">
                            <label class="custom-file-label" for="photo2"><fmt:message key="admin.h.add_photo"/></label>
                        </div>
                    </div>
                    <hr/>
                    <input type="hidden" name="prodCatId" id="hiddenProdCatId">
                    <button type="submit" class="btn btn-primary"><fmt:message key="admin.h.edit_product_cat"/></button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal delete prod category button -->
<div class="modal fade" id="deleteProdCatModal" tabindex="-1" role="dialog" aria-labelledby="deleteProdCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteProdCatModalLabel"><fmt:message key="admin.h.delete_product_cat"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="deleteProdCatForm" action="${pageContext.request.contextPath}/product/category/delete" method="POST">
                    <div class="form-row">
                        <label><fmt:message key="admin.h.sure"/><br><fmt:message key="admin.h.sure2"/></label>
                    </div>
                    <input type="hidden" id="hiddenProdCatDeleteId" name="prodCatId">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="admin.h.cancel"/></button>
                    <button type="submit" id="deleteProductCategoryButton" class="btn btn-primary"><fmt:message key="admin.h.delete_product_cat"/></button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../parts/_footer.jspf" %>
<%@include file="../parts/_importsjs.jspf" %>

<script type="text/javascript">


    $('.deleteProdCat').click(function () {
        $('#hiddenProdCatDeleteId').val($(this).data('id'));
    });

    $('.modifyProdCat').click(function () {
        $('#hiddenProdCatId').val($(this).data('id'));
        $('#descriptionProdCat').html($(this).data('desc'));
        $('#nameProdCat').val($(this).data('name'));
    });


    function deleteProdCatPhoto(id, elem) {        // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.post("product/category/photo/delete",
            {
                prodCategoryPhotoId : id
            }).done(function(){});
        $(elem).fadeOut(300, function() { $(this).remove(); });
        $(elem).tooltip( "option", "disabled" );
    }


    $("#deleteProdCatForm").submit(function(e) {
        var form = $(this);
        var url = form.attr('action');
        var myData = $("#hiddenProdCatDeleteId").val();
        $.ajax({
            type: "POST",
            url: url,
            data: { prodCatId : myData }
        }).done(function() {
            var elem = document.getElementById("prodCat" + myData);
            $('#deleteProdCatModal').modal('hide');
            $(elem).fadeOut(300, function() { $(this).remove(); });

        }).fail(function() {
            alert("error deleting product category");
        });
        e.preventDefault(); // avoid to execute the actual submit of the form.
    });
    $('table').DataTable({
        "order" : [[1, "asc"]],
        "language" : {
            "url" : getLang()
        },
        responsive: {
            details: {
                type: 'column',
                target: 'tr'
            }
        },
        columnDefs: [ {
            className: 'control',
            orderable: false,
            targets:   0
        } ]
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
