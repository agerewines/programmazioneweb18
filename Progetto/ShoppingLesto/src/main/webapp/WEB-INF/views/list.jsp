<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:choose>
    <c:when test="${not empty param.lang}">
        <c:set var="language" value="${param.lang}" scope="session"/>
    </c:when>
    <c:otherwise>
        <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    </c:otherwise>
</c:choose>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="i18n.text"/>
<!DOCTYPE html>
<html lang="${language}">

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
        <div class="col-md-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <%@include file="parts/_successMessage.jspf" %>
            <div class="d-flex flex-wrap">
                <div class="p-2 align-self-center justify-content-center">
                    <img id="listPic" class="rounded shadow mb-5 bg-white rounded" height="150" width="150"
                         src="${pageContext.request.contextPath}/images?id=${list.id}&resource=shoppingLists"
                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Lists/default.png';"/>
                </div>
                <div class="p-2 flex-fill">
                    <h2>
                        <ul class="list-inline">
                            <li class="list-inline-item"><fmt:message key="list.li.list"/></li>
                            <li class="list-inline-item">${list.name}</li>
                            <c:if test="${list.edit}">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#modifyListModal">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                </li>
                            </c:if>
                            <c:if test="${list.share && !anon}">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#shareListModal">
                                        <i class="fas fa-share"></i>
                                    </button>
                                </li>
                            </c:if>
                            <c:if test="${list.user.id eq user.id}">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#deleteListModal">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </li>
                            </c:if>
                        </ul>
                    </h2>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item"><h5><fmt:message key="list.li.description"/></h5></li>
                                <li class="list-inline-item">${list.description}</li>
                            </ul>
                        </li>
                        <c:if test="${!anon}">
                            <li class="list-group-item">
                                <ul class="list-inline">
                                    <li class="list-inline-item"><h5><fmt:message key="list.li.owner"/></h5></li>
                                    <li class="list-inline-item">${list.user.fullName}</li>
                                </ul>
                            </li>
                        </c:if>
                        <c:if test="${not empty sharedWith && !anon}">
                            <li class="list-group-item">
                                <button type="button" class="btn btn-primary"
                                        style="padding: 0 .375rem 0 .375rem;"
                                        data-toggle="modal" data-target="#sharedUserModal">
                                    <div class="d-lg-none">Shared With</div>
                                    <div class="d-none d-lg-block">
                                        <fmt:message key="list.button.shared"/>
                                        <span class="badge badge-dark badge-pill">${fn:length(sharedWith)}</span>
                                    </div>
                                </button>
                            </li>
                        </c:if>
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item"><h5><fmt:message key="list.li.category"/></h5></li>
                                <li class="list-inline-item">${list.category.name}</li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-1">
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <table class="table" id="tableProductInList">
                <thead class="thead-light">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"><fmt:message key="list.th.name"/></th>
                    <th scope="col"><fmt:message key="list.th.description"/></th>
                    <th scope="col" width="70px">Images</th>
                    <th scope="col">Price</th>
                    <th scope="col">Delete</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productsList}" var="prod">
                    <tr>
                        <td></td>
                        <td>${prod.name}</td>
                        <td>${prod.description}</td>
                        <td>
                            <img class="rounded shadow mb-3 bg-white rounded" height="65" width="65"
                                 src="${pageContext.request.contextPath}/images?id=${prod.id}&resource=product"
                                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';"/>
                        </td>
                        <td>${prod.price} €</td>x
                        <td>
                            <button type="button" class="btn btn-primary removeProd"
                                    style="padding: 0 .375rem 0 .375rem;"
                                    data-toggle="modal" data-target="#deleteProductModal" data-id="${prod.id}">
                                <i class="fas fa-trash"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${anon}">
                    <a class="btn btn-primary mb-4"
                       href="${pageContext.request.contextPath}/product/add?listId=${list.id}&anonymous=true"><fmt:message
                            key="list.a.add_product"/></a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary mb-4"
                       href="${pageContext.request.contextPath}/product/add?listId=${list.id}"><fmt:message
                            key="list.a.add_product"/></a>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-1">
        </div>
    </div>
    <div class="row justify-content-center">
        <div class="col-md-1">
        </div>
        <div class="col-lg-10 col-md-10 col-12">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Chat</h5>
                    </div>
                    <div class="modal-body">
                        <div id="divMessages" style="max-height: 250px; overflow-y: scroll">
                            <ul class="list-unstyled" id="messages">
                            </ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <div class="input-group mb-3">
                            <div class="d-none d-lg-block">
                                <div class="input-group-prepend">
                                    <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Choose...</button>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item chooseMessage">Sto andando a fare la spesa, manca qualcosa?</a>
                                        <a class="dropdown-item chooseMessage">Lista modificata. Guarda cosa ho aggiunto</a>
                                        <a class="dropdown-item chooseMessage">Spesa fatta. Ti puoi rilassare</a>
                                    </div>
                                </div>
                            </div>
                            <input type="text" class="form-control" placeholder="Send message..." aria-label="Send message..." aria-describedby="submitMessage" id="message">
                            <input type="hidden" id="listIdMessage" value="${list.id}">
                            <input type="hidden" id="userIdMessage" value="${user.id}">
                            <input type="hidden" id="sendMessageUrl" value="${pageContext.request.contextPath}/message/create">
                            <div class="input-group-append">
                                <button type="button" class="btn btn-primary" id="submitMessage">Submit</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-1">
        </div>
</div>
<!-- Modal edit button -->
<div class="modal fade" id="modifyListModal" tabindex="-1" role="dialog" aria-labelledby="modifyListModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyListModalLabel"><fmt:message key="list.h.edit_list"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/modify" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameList"><fmt:message key="list.label.name"/></label>
                            <input type="text" class="form-control" id="nameList" placeholder="Name" name="nameList"
                                   value="${list.name}">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionList"><fmt:message key="list.label.description"/></label>
                            <textarea class="form-control" id="descriptionList" name="descriptionList" rows="3"
                                      placeholder="Description">${list.description}</textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="avatar"><fmt:message key="list.label.choose_category"/></label>
                        <select id="category" name="category" class="form-control">
                            <option selected value="-1"><fmt:message key="list.option.choose"/></option>
                            <c:forEach items="${listCategories}" var="category">
                                <option value="${category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <hr/>
                    <div class="form-row mb-4">
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="avatar" name="avatar"
                                   value="${list.image}">
                            <label class="custom-file-label" for="avatar">Add photo</label>
                        </div>
                    </div>
                    <input type="hidden" id="listId" name="listId" value="${list.id}">

                    <button type="submit" class="btn btn-primary"><fmt:message key="list.h.edit_list"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal share button -->
<div class="modal fade" id="shareListModal" tabindex="-1" role="dialog" aria-labelledby="shareListModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="shareListModalLabel"><fmt:message key="list.h.share_list"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/share" method="POST">
                    <div class="form-row">
                        <label for="user"><fmt:message key="list.label.chose_user"/></label>
                        <select id="user" name="user" class="form-control">
                            <option selected value="-1"><fmt:message key="list.option.choose"/></option>
                            <c:forEach items="${listUsers}" var="user">
                                <option value="${user.id}">${user.fullName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <br/>
                    <div class="form-row">
                        <label><fmt:message key="list.label.chose_permission"/></label>
                    </div>
                    <div class="form-row">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="checkAdd" name="add" value="add">
                            <label class="form-check-label" for="checkAdd"><fmt:message key="list.label.add"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="checkEdit" name="edit" value="edit">
                            <label class="form-check-label" for="checkEdit"><fmt:message key="list.label.edit"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="checkShare" name="share"
                                   value="share">
                            <label class="form-check-label" for="checkShare"><fmt:message
                                    key="list.label.share"/></label>
                        </div>
                    </div>
                    <br/>
                    <input type="hidden" id="listIdShare" name="listId" value="${list.id}">
                    <button type="submit" class="btn btn-primary"><fmt:message key="list.button.share_list"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal delete button -->
<div class="modal fade" id="deleteListModal" tabindex="-1" role="dialog" aria-labelledby="deleteListModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteListModalLabel"><fmt:message key="list.h.delete_list"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/delete" method="POST" id="deleteItemForm">
                    <label><fmt:message key="list.label.sure"/> ${list.name}?</label> <br/>
                    <c:if test="${not empty sharedWith}">
                        <label>
                            <fmt:message key="list.label.even"/>
                            <ul>
                                <c:forEach items="${sharedWith}" var="sharedUser">
                                    <li>${sharedUser.fullName}</li>
                                </c:forEach>
                            </ul>
                        </label> <br/>
                    </c:if>
                    <c:if test="${anon}">
                        <input type="hidden" id="anonymous" name="anonymous" value="true">
                    </c:if>
                    <input type="hidden" id="listIdDelete" name="listId" value="${list.id}">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="list.h.delete_list"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal shared user -->
<div class="modal fade" id="sharedUserModal" tabindex="-1" role="dialog" aria-labelledby="sharedUserModalTitle"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="sharedUserModalTitle"><fmt:message key="list.h.shared_users"/></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <ul class="list-group">
                    <c:forEach items="${sharedWith}" var="sharedUser">
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item">
                                        ${sharedUser.fullName}
                                </li>
                                <c:if test="${list.share and sharedUser.id ne user.id}">
                                    <li class="list-inline-item">
                                        <button type="button" class="btn btn-primary editPermit"
                                                style="padding: 0 .375rem 0 .375rem;"
                                                data-toggle="modal" data-target="#editPermitModal"
                                                data-id="${sharedUser.id}"
                                                data-list-id="${list.id}">
                                            <i class="fas fa-edit"></i>
                                        </button>
                                    </li>
                                    <li class="list-inline-item">
                                        <button type="button" class="btn btn-primary deletePermit"
                                                style="padding: 0 .375rem 0 .375rem;"
                                                data-toggle="modal" data-target="#deleteSharedUserModal"
                                                data-id="${sharedUser.id}"
                                                data-shared-name="${sharedUser.fullName}">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </li>
                                </c:if>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal"><fmt:message
                        key="list.button.close"/></button>
            </div>
        </div>
    </div>
</div>
<!-- Modal edit share permit -->
<div class="modal fade" id="editPermitModal" tabindex="-1" role="dialog" aria-labelledby="editPermitModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editPermitModalLabel">Edit Permit</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/share/edit" method="POST">
                    <div class="form-row">
                        <label><fmt:message key="list.label.chose_permission"/></label>
                    </div>
                    <div class="form-row">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" id="editPermitAdd" type="checkbox" name="add" value="add">
                            <label class="form-check-label" for="editPermitAdd"><fmt:message
                                    key="list.label.add"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" id="editPermitEdit" type="checkbox" name="edit"
                                   value="edit">
                            <label class="form-check-label" for="editPermitEdit"><fmt:message
                                    key="list.label.edit"/></label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" id="editPermitShare" type="checkbox" name="share"
                                   value="share">
                            <label class="form-check-label" for="editPermitShare"><fmt:message
                                    key="list.label.share"/></label>
                        </div>
                    </div>
                    <br/>
                    <input type="hidden" name="listId" value="${list.id}">
                    <input type="hidden" id="userId" name="user">
                    <button type="submit" class="btn btn-primary"><fmt:message key="list.button.share_list"/></button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal edit shared user permit -->
<div class="modal fade" id="deleteSharedUserModal" tabindex="-1" role="dialog"
     aria-labelledby="deleteSharedUserModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteSharedUserModalLabel">Delete shared user</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/share/delete" method="POST">
                    <label id="confirmLabelDeleteSharedUser"></label> <br/>
                    <input type="hidden" id="listIdDeleteSharedUser" name="listId" value="${list.id}">
                    <input type="hidden" id="userIdDeleteSharedUser" name="user">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Delete shared user</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Modal delete product from list -->
<div class="modal fade" id="deleteProductModal" tabindex="-1" role="dialog" aria-labelledby="deleteProductModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteProductModalLabel">Remove product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/list/product/delete" method="POST">
                    <label id="confirmLabelDeleteProduct">Are you sure you want to delete this product from this
                        list?</label> <br/>
                    <c:if test="${anon}">
                        <input type="hidden" id="anonymous" name="anonymous" value="true">
                    </c:if>
                    <input type="hidden" id="listIdDeleteProduct" name="listId" value="${list.id}">
                    <input type="hidden" id="prodIdDelete" name="prodId">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Remove product</button>
                </form>
            </div>
        </div>
    </div>
</div>


<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
<script type="text/javascript">
    // language=JQuery-CSS
    $('.editPermit').click(function () {
        $('#userId').val($(this).data('id'));
    });
    $(document).on("click", ".editPermit", function () {        // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
        $.get("list/share/edit",
            {
                listId: $(this).data('list-id'),
                userId: $(this).data('id')
            }).done(function (responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
            $.each(responseJson, function (key, value) {               // Iterate over the JSON object.
                switch (key) {
                    case "add" :
                        $("#editPermitAdd").prop('checked', value);
                        break;
                    case "edit" :
                        $("#editPermitEdit").prop('checked', value);
                        break;
                    case "share" :
                        $("#editPermitShare").prop('checked', value);
                        break;
                }
            });
        });
    });
    // DELETE SHARED USER MODAL
    $('.deletePermit').click(function () {
        $('#confirmLabelDeleteSharedUser').html("Are you sure you want to unshare this list with " + $(this).data('shared-name') + "?");
        $('#userIdDeleteSharedUser').val($(this).data('id'));
    });

    $(document).ready(function () {
        $('#tableProductInList').DataTable({
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

        let listId = $("#listIdMessage").val();
        let userId = $("#userIdMessage").val();
        $.ajax({
            type: "POST",
            url: "message/show",
            dataType: "html",
            data:  {
                listId : listId,
                userId : userId
            },
            success: function(html){
                $("#messages").html(html);
                $("#divMessages").animate({scrollTop: $('#divMessages').prop("scrollHeight")}, 1000);
            }
        });
    });
    var refreshMessage = setInterval(function(){
        $.ajax({
            type: "POST",
            url: "message/show",
            dataType: "html",
            data:  {
                listId : $("#listIdMessage").val(),
                userId : $("#userIdMessage").val(),
                lastMessageTime : $("#messages li").last().find("div h6 small").html()
            },
            success: function(html){
                if(html !== ""){
                    $("#messages").append(html);
                    $("#divMessages").animate({scrollTop: $('#divMessages').prop("scrollHeight")}, 1000);
                }
            }
        });
    }, 2000);

    $(".chooseMessage").click(function(){
        let elem = $(this).html();
        $("#message").val(elem);
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
    }

    $('.removeProd').click(function () {
        $('#prodIdDelete').val($(this).data('id'));
    });


    // chat
    $('#submitMessage').click(function(){
        let listId = $("#listIdMessage").val();
        let message = $("#message").val();
        let url = $("#sendMessageUrl").val();
        $.ajax({
            type: "POST",
            url: url,
            data: {
                listId : listId,
                message : message
            }
        }).done(function(){
            $("#message").val('');
            $.ajax({
                type: "POST",
                url: "message/show",
                dataType: "html",
                data:  {
                    listId : $("#listIdMessage").val(),
                    userId : $("#userIdMessage").val(),
                    lastMessageTime : $("#messages li").last().find("div h6 small").html()
                },
                success: function(html){
                    if(html !== ""){
                        $("#messages").append(html);
                        $("#divMessages").animate({scrollTop: $('#divMessages').prop("scrollHeight")}, 1000);
                    }
                }
            });
        });
    });


</script>
</body>

</html>