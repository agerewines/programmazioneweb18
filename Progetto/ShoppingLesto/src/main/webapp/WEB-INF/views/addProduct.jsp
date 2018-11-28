<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title>ShoppingLesto | <fmt:message key="admin.h.add_product" /> - Webprogramming18</title>

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
            <!-- Search form -->
            <form class="form-inline md-form form-sm mt-0">
                <i class="fa fa-search" aria-hidden="true"></i>
                <input class="form-control form-control-sm ml-3 w-75 search-box" type="text" placeholder="<fmt:message key="search" />" aria-label="Search">
                <button type="button" class="btn btn-primary float-right m-2 addProduct"
                        data-toggle="modal" data-target="#addCustomProductModal">
                    <div class="d-lg-none"><i class="fas fa-plus"></i></div>
                    <div class="d-none d-lg-block"><fmt:message key="addproduct.customprod" /></div>
                </button>
            </form>
            <br/>

            <!-- Image loader -->
            <table class="table table-hover table-striped" id="allProductTable">
                <thead>
                <tr>
                    <th scope="col"></th>
                    <th style="width: 25%" scope="col"><fmt:message key="list.th.name" /></th>
                    <th style="width: 45%" scope="col"><fmt:message key="list.th.description" /></th>
                    <th style="width: 10%" scope="col"><fmt:message key="new_list.label.category" /></th>
                    <th style="width: 10%" scope="col"><fmt:message key="list.th.image" /></th>
                    <th style="width: 5%" scope="col"><fmt:message key="list.th.price" /></th>
                    <th style="width: 5%" scope="col"><fmt:message key="product.th.add" /></th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <button type="button" id="showAll" class="btn btn-primary float-left m-2">
                <fmt:message key="addproduct.showall" />
            </button>
            <br/>
        </div>
        <div class="col-md-1">
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
                    <c:if test="${!anon}">
                        <input id="hiddenAnon" type="hidden" name="anonymous" value="true">
                    </c:if>
                    <input id="hiddenListId" type="hidden" name="listId" value="${param.listId}">
                    <button type="submit" class="btn btn-secondary" data-dismiss="modal"><fmt:message key="product.button.cancel" /></button>
                    <button type="submit" class="btn btn-primary"><fmt:message key="product.button.add" /></button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Product -->
<!-- Modal add product button -->
<div class="modal fade" id="addCustomProductModal" tabindex="-1" role="dialog" aria-labelledby="addCustomProductModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addCustomProductModalLabel">Add Custom Product</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/product/custom/new" method="POST"
                      enctype='multipart/form-data'>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="nameProd"><fmt:message key="list.th.name" /></label>
                            <input type="text" class="form-control" id="nameProd" placeholder="<fmt:message key="list.th.name" />" name="nameProd">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="descriptionProd"><fmt:message key="list.th.description" /></label>
                            <textarea class="form-control" id="descriptionProd" name="descriptionProd" rows="3"
                                      placeholder="<fmt:message key="list.th.description" />"></textarea>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-12">
                            <label for="priceProd"><fmt:message key="list.th.price" /></label>
                            <input type="number" class="form-control" id="priceProd" placeholder="<fmt:message key="list.th.price" />" name="price" step="0.01">
                        </div>
                    </div>
                    <div class="form-row">
                        <label for="productCategory"><fmt:message key="list.label.choose_category" /></label>
                        <div class="form-group col-md-12">
                            <select id="productCategory" name="category" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group  col-md-12">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="photo" name="photo">
                                <label class="custom-file-label" for="photo"><fmt:message key="admin.h.add_photo" /></label>
                            </div>
                        </div>
                    </div>
                    <input type="hidden" name="custom" value="1">
                    <input type="hidden" name="listId" value="${param.listId}">
                    <button type="submit" class="btn btn-primary"><fmt:message key="admin.h.add_product" /></button>
                </form>
            </div>
        </div>
    </div>
</div>


<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>

<script type="text/javascript">
    $(document).ready(function(){
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
        var datatable =  $('#allProductTable').DataTable({
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
            } ],
            searching: false
        });

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
                    datatable.clear().draw();
                    $.each(responseJson, function(key, value) {
                        var row = "";
                        row += "<tr>\n" +
                            "            <td></td>" +
                            "            <td>"+ value.name +"</td>\n" +
                            "            <td>"+ value.description +"</td>\n" +
                            "            <td>"+ value.category.name +"</td>\n" +
                            "            <td>\n";
                        if(value.photos == null){
                            row    += "<img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                                "                             src=\"${pageContext.request.contextPath}/images/avatars/Products/default.png\" alt=\"default product photo\"/>"
                        }else{
                            $.each(value.photos, function(photoKey, photoValue) {
                                row += "                            <img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                                    "                                 src=\"${pageContext.request.contextPath}/images?id=\""+ photoValue.id +"\"&resource=products\"\n" +
                                    "                                 onerror=\"this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';\"/>\n";
                            });

                        }
                        row += "            </td>" +
                            "            <td>"+ value.price +" €</td>\n" +
                            "            <td>\n" +
                            "                <button type=\"button\" class=\"btn btn-primary addButton\"\n" +
                            "                        style=\"padding: 0 .375rem 0 .375rem;\"\n" +
                            "                        data-toggle=\"modal\" data-target=\"#addProductModal\" data-id=\""+ value.id +"\">\n" +
                            "                    <i class=\"fas fa-plus\"></i>\n" +
                            "                </button>\n" +
                            "            </td>\n" +
                            "        </tr>\n";
                        datatable.row.add($(row)).draw();
                        datatable.language.url(getLang())
                    });
                    $('.addButton').click(addProd);
                });
            }, 300);
        });

        $('.addProduct').click(function(){       // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
            $.get("category/all")
                .done(function(responseJson) {                 // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response JSON...
                    var opts = "";
                    $.each(responseJson, function(key, value) {               // Iterate over the JSON object.
                        opts += "<option value='" + key + "'>" + value + "</option>";
                    });
                    $("#productCategory").append(opts);
                });
        });

        $('#showAll').click(function(e) {
            $.ajax({
                'url': "/ShoppingLesto/list/availableproduct?listId=" + new URLSearchParams(window.location.search).get('listId'),
                'method': "GET",
                'contentType': 'application/json'
            }).done(function (responseJson) {
                datatable.clear().draw();
                $.each(responseJson, function(key, value) {
                    var row = "";
                    row += "<tr>\n" +
                        "            <td></td>" +
                        "            <td>"+ value.name +"</td>\n" +
                        "            <td>"+ value.description +"</td>\n" +
                        "            <td>"+ value.category.name +"</td>\n" +
                        "            <td>";
                    if(value.photos == null){
                        row    += "<img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                            "                             src=\"${pageContext.request.contextPath}/images/avatars/Products/default.png\" alt=\"default product photo\"/>";
                    }else{
                        $.each(value.photos, function(photoKey, photoValue) {
                            row += "                            <img class=\"rounded shadow mb-3 bg-white rounded\" height=\"65\" width=\"65\"\n" +
                                "                                 src=\"${pageContext.request.contextPath}/images?id=\""+ photoValue.id +"\"&resource=products\"\n" +
                                "                                 onerror=\"this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Products/default.png';\"/>\n";
                        });

                    }
                    row +="</td>\n" +
                        "               <td>"+ value.price +" €</td>\n" +
                        "            <td>\n" +
                        "                <button type=\"button\" class=\"btn btn-primary addButton\"\n" +
                        "                        style=\"padding: 0 .375rem 0 .375rem;\"\n" +
                        "                        data-toggle=\"modal\" data-target=\"#addProductModal\" data-id=\""+ value.id +"\">\n" +
                        "                    <i class=\"fas fa-plus\"></i>\n" +
                        "                </button>\n" +
                        "            </td>\n" +
                        "        </tr>\n";
                    datatable.row.add($(row)).draw()
                });
                $('.addButton').click(addProd);
            });
        });
    })

</script>
</body>

</html>