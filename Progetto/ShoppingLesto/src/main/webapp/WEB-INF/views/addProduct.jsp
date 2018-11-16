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
        <div class="col-md-2">
        </div>
        <div class="col-lg-8 col-md-8 col-12">
            <%@include file="parts/_successMessage.jspf" %>
            <!-- Search form -->
            <form class="form-inline md-form form-sm mt-0">
                <i class="fa fa-search" aria-hidden="true"></i>
                <input class="form-control form-control-sm ml-3 w-75 search-box" type="text" placeholder="Search" aria-label="Search">
            </form>
            <br/>
            <table class="table table-hover table-striped" id="allProductTable">
                <thead>
                <tr>
                    <th style="width: 10%" scope="col">Photos</th>
                    <th style="width: 25%" scope="col">Name</th>
                    <th style="width: 55%" scope="col">Description</th>
                    <th style="width: 5%" scope="col">Price</th>
                    <th style="width: 5%" scope="col">Edit</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <br/>
        </div>
        <div class="col-md-2">
        </div>
    </div>
</div>


<!-- Modal product button -->
<div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addProductModalLabel"><fmt:message key="product.h.add_product" /></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/add" method="POST">
                    <label><fmt:message key="product.h.sure" /></label> <br/>
                    <input id="hiddenProdId" type="hidden" name="prodId">
                    <div id="prova"></div>
                    <input id="hiddenListId" type="hidden" name="listId" value="${param.listId}">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="product.button.cancel" /></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="product.button.add" /></button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

<script type="text/javascript">
    $('#allProductTable').DataTable();
    var timeout = null;
    var addProd = function (e) {
        console.log($(e.target).data('id'));
        $('#hiddenProdId').val($(this).data('id'));
    };
    $('.search-box').keyup(function(e) {
        if (timeout !== null) {
            clearTimeout(timeout);
        }
        timeout = setTimeout(function() {
            $.ajax({
                'url': "/ShoppingLesto/list/availableproduct?listId=" + new URLSearchParams(window.location.search).get('listId') + "&q=" + e.target.value,
                'method': "GET",
                'contentType': 'application/json'
            }).done(function (responseJson) {
                var rows = "";
                $.each(responseJson, function(key, value) {
                    console.log(value);
                    rows += "<tr>\n" +
                        "            <td>\n";

                    if(value.photos == null){
                        rows    += "<img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                        "                             src=\"${pageContext.request.contextPath}/images/avatars/Products/default.png\" alt=\"default product photo\"/>"
                    }else{
                        $.each(value.photos, function(photoKey, photoValue) {
                            rows += "                            <img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                                "                                 src=\"${pageContext.request.contextPath}/images?id=\""+ photoValue.id +"\"&resource=products\"\n" +
                                "                                 onerror=\"this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';\"/>\n";
                        });

                    }
                    rows += "            </td>" +
                        "            <td>"+ value.name +"</td>\n" +
                        "            <td>"+ value.description +"</td>\n" +
                        "            <td>"+ value.price +" €</td>\n" +
                        "            <td>\n" +
                        "                <button type=\"button\" class=\"btn btn-primary addButton\"\n" +
                        "                        style=\"padding: 0 .375rem 0 .375rem;\"\n" +
                        "                        data-toggle=\"modal\" data-target=\"#addProductModal\" data-id=\""+ value.id +"\">\n" +
                        "                    <i class=\"fas fa-plus\"></i>\n" +
                        "                </button>\n" +
                        "            </td>\n" +
                        "        </tr>\n";
                });
                $('table tbody').html(rows);
                $('.addButton').click(addProd);
            });
        }, 300);
    });
</script>
</body>

</html>