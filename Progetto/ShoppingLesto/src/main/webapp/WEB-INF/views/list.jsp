<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 18/10/2018
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

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
        <div class="col">
        </div>
        <div class="col-md-8">
            <%@include file="parts/_successMessage.jspf" %>

            <div class="media">
                <img id="listPic" class="align-self-center mr-3 rounded" height="150" width="150"
                     src="${pageContext.request.contextPath}/images?id=${list.id}&resource=shoppingLists"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Lists/default.png';"/>
                <div class="media-body">
                    <h2>
                        <ul class="list-inline">
                            <li class="list-inline-item">List:</li>
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
                            <c:if test="${list.share}">
                                <li class="list-inline-item">
                                    <button type="button" class="btn btn-primary"
                                            style="padding: 0 .375rem 0 .375rem;"
                                            data-toggle="modal" data-target="#shareListModal">
                                        <i class="fas fa-share"></i>
                                    </button>
                                </li>
                            </c:if>
                            <li class="list-inline-item" style="float: right;">
                                <button type="button" class="btn btn-primary"
                                        style="padding: 0 .375rem 0 .375rem;"
                                        data-toggle="modal" data-target="#deleteListModal">
                                    <i class="fas fa-trash"></i>
                                </button>
                            </li>
                        </ul>
                    </h2>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item"><h5>Description:</h5></li>
                                <li class="list-inline-item">${list.description}</li>
                            </ul>
                        </li>
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item"><h5>Owner:</h5></li>
                                <li class="list-inline-item">${list.user.fullName}</li>
                            </ul>
                        </li>
                        <c:if test="${not empty sharedWith}">
                            <li class="list-group-item">
                                <button type="button" class="btn btn-primary"
                                        style="padding: 0 .375rem 0 .375rem;"
                                        data-toggle="modal" data-target="#sharedUserModal">
                                    See who this list is shared with
                                    <span class="badge badge-dark badge-pill">${fn:length(sharedWith)}</span>
                                </button>
                            </li>
                        </c:if>
                        <li class="list-group-item">
                            <ul class="list-inline">
                                <li class="list-inline-item"><h5>Category:</h5></li>
                                <li class="list-inline-item">${list.category.name}</li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <br/>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Prodotto</th>
                    <th scope="col">Descrizione</th>
                    <th scope="col">Logo</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productsList}" var="prod">
                    <tr>
                        <td>${prod.name}</td>
                        <td>${prod.description}</td>
                        <td>${prod.logo}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col">
        </div>
    </div>
    <!-- Modal edit button -->
    <div class="modal fade" id="modifyListModal" tabindex="-1" role="dialog" aria-labelledby="modifyListModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modifyListModalLabel">Edit List</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/list/modify" method="POST"
                          enctype='multipart/form-data'>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="nameList">Name</label>
                                <input type="text" class="form-control" id="nameList" placeholder="Name" name="nameList"
                                       value="${list.name}">
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <label for="descriptionList">Description</label>
                                <textarea class="form-control" id="descriptionList" name="descriptionList" rows="3"
                                          placeholder="Description">${list.description}</textarea>
                            </div>
                        </div>
                        <div class="form-row">
                            <label for="avatar">Choose Category</label>
                            <select id="category" name="category" class="form-control">
                                <option selected value="-1">Choose...</option>
                                <c:forEach items="${listCategories}" var="category">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <div class="form-group  col-md-6">
                                <label for="avatar">Add your avatar</label>
                                <input type="file" class="form-control-file" id="avatar" name="avatar"
                                       value="${list.image}">
                            </div>
                        </div>
                        <input type="hidden" id="listId" name="listId" value="${list.id}">

                        <button type="submit" class="btn btn-primary">Edit list</button>
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
                    <h5 class="modal-title" id="shareListModalLabel">Share list</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/list/share" method="POST">
                        <div class="form-row">
                            <label for="user">Choose user to share with</label>
                            <select id="user" name="user" class="form-control">
                                <option selected value="-1">Choose...</option>
                                <c:forEach items="${listUsers}" var="user">
                                    <option value="${user.id}">${user.fullName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <br/>
                        <div class="form-row">
                            <label>Choose permissions</label>
                        </div>
                        <div class="form-row">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="checkAdd" name="add" value="add">
                                <label class="form-check-label" for="checkAdd">Add</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="checkEdit" name="edit" value="edit">
                                <label class="form-check-label" for="checkEdit">Edit</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="checkbox" id="checkShare" name="share"
                                       value="share">
                                <label class="form-check-label" for="checkShare">Share</label>
                            </div>
                        </div>
                        <br/>
                        <input type="hidden" id="listIdShare" name="listId" value="${list.id}">
                        <button type="submit" class="btn btn-primary">Share list</button>
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
                    <h5 class="modal-title" id="deleteListModalLabel">Delete list</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form action="${pageContext.request.contextPath}/list/delete" method="POST">
                        <label>Are you sure you want to delete ${list.name}?</label> <br/>
                        <input type="hidden" id="listIdDelete" name="listId" value="${list.id}">
                        <button type="submit" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Delete list</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="sharedUserModal" tabindex="-1" role="dialog" aria-labelledby="sharedUserModalTitle"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="sharedUserModalTitle">Shared users</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <ul class="list-group">
                        <c:forEach items="${sharedWith}" var="sharedUser">
                            <li class="list-group-item">${sharedUser.fullName}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <%@include file="parts/_footer.jspf" %>
    <%@include file="parts/_importsjs.jspf" %>
</body>

</html>