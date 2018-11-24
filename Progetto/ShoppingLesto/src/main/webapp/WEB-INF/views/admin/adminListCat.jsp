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
                    <h5>Manage the category of lists</h5>
                </li>
                <li class="list-inline-item">
                    <button type="button" class="btn btn-primary addListCat"
                            style="padding: 0 .375rem 0 .375rem;"
                            data-toggle="modal" data-target="#addListCatModal">
                        <i class="fas fa-plus"></i>
                    </button>
                </li>
            </ul>
            <p><small>Click on the images in order to delete them.</small></p>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Photos</th>
                    <th scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listCategory}" var="listCat">
                    <tr id="listCat${listCat.id}">
                        <td></td>
                        <td>${listCat.name}</td>
                        <td>${listCat.description}</td>
                        <td>
                            <c:forEach items="${listCat.photos}" var="photo">
                                <img class="rounded shadow mb-3 bg-white rounded deletePhoto image" height="65" width="65"
                                     src="${pageContext.request.contextPath}/images?id=${photo.id}&resource=listCatPhoto"
                                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"
                                     onclick="deleteListCatPhoto(${photo.id}, this)"
                                     onmouseover="mouseOverPhoto(this)" title="Click on the picture to delete it!"/>
                            </c:forEach>
                        </td>
                        <td>
                            <ul class="list-inline">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary modifyListCat"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#modifyListCatModal"
                                            data-id="${listCat.id}"
                                            data-name="${listCat.name}"
                                            data-desc="${listCat.description}">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                </li>
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary deleteListCat"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#deleteListCatModal" data-id-list-cat="${listCat.id}">
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

<!-- List Categoy -->
<!-- Modal add list category button -->
<div class="modal fade" id="addListCatModal" tabindex="-1" role="dialog" aria-labelledby="addListCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addListCatModalLabel">Add List Category</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/category/new" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameListCat">Name</label>
                            <select id="nameListCatAdd" name="nameListCat" class="form-control">
                                <option value="Tempo Libero">Tempo libero</option>
                                <option value="Alimentari">Alimentari</option>
                                <option value="Videogiochi">Videogiochi</option>
                                <option value="Tecnologia">Tecnologia</option>
                                <option value="Accessori per la casa">Accessori per la casa</option>
                                <option value="Elettrodomestici">Elettrodomestici</option>
                                <option value="Fai da te">Fai da te</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionListCat">Description</label>
                            <textarea class="form-control" name="descriptionListCat" rows="3"
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo" name="photo">
                            <label class="custom-file-label" for="photo">Add photo</label>
                        </div>
                    </div>
                    <hr/>
                    <button type="submit" class="btn btn-primary">Add list category</button>
                </form>
            </div>
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
                <form action="${pageContext.request.contextPath}/list/category/edit" method="POST"
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
                                      placeholder="Description"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="photo2" name="photo">
                            <label class="custom-file-label" for="photo2">Add photo</label>
                        </div>
                    </div>
                    <hr/>
                    <input type="hidden" name="listCatId" id="hiddenListCatId">
                    <button type="submit" class="btn btn-primary">Edit list category</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal delete list category button -->
<div class="modal fade" id="deleteListCatModal" tabindex="-1" role="dialog" aria-labelledby="deleteListCatModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteListCatModalLabel">Delete ListCategory</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="deleteListCatForm" action="${pageContext.request.contextPath}/list/category/delete" method="POST">
                    <div class="form-row">
                        <label>Are you sure you want to delete this category<br>You will delete all list using this category</label>
                    </div>
                    <input type="hidden" id="hiddenListCatDeleteId" name="listCatId">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Delete list category</button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="../parts/_footer.jspf" %>
<%@include file="../parts/_importsjs.jspf" %>
<script type="text/javascript">

    // language=JQuery-CSS
    $('.modifyListCat').click(function () {
        $('#hiddenListCatId').val($(this).data('id'));
        $('#descriptionListCat').html($(this).data('desc'));
        $('#nameListCat').val($(this).data('name'));
    });

    $('.deleteListCat').click(function () {
        $('#hiddenListCatDeleteId').val($(this).data('id-list-cat'));
    });

    function deleteListCatPhoto(id, elem) {        // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.post("list/category/photo/delete",
            {
                listCategoryPhotoId : id
            }).done(function(){});
        $(elem).fadeOut(300, function() { $(this).remove(); });
        $(elem).tooltip( "option", "disabled" );
    }
    function mouseOverPhoto(elem){
        $(elem).css( 'cursor', 'pointer' );
    }

    $("#deleteListCatForm").submit(function(e) {
        var form = $(this);
        var url = form.attr('action');
        var myData = $("#hiddenListCatDeleteId").val();
        $.ajax({
            type: "POST",
            url: url,
            data: { listCatId : myData }
        }).done(function() {
            var elem = document.getElementById("listCat" + myData);
            $('#deleteListCatModal').modal('hide');
            $(elem).fadeOut(300, function() { $(this).remove(); });

        }).fail(function() {
            alert("error deleting list category");
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
