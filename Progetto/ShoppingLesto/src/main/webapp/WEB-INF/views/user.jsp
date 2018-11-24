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

    <title>ShoppingLesto | User ${user.fullName} - Webprogramming18</title>

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
            <h2>
                ${user.fullName}
            </h2>
            <div class="media">
                <img class="align-self-center mr-3 rounded" id="profilePic" height="150" width="150"
                     src="${pageContext.request.contextPath}/images?id=${user.id}&resource=user"
                     onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/images/avatars/Users/default.jpeg';"/>
                <div class="media-body">
                    <ul class="list-inline">
                        <li class="list-inline-item"><h5>Mail:</h5></li>
                        <li class="list-inline-item">${user.mail}</li>
                    </ul>
                </div>
            </div>
            <hr/>
            <div class="justify-content-center">
                <button type="button" class="btn btn-dark m-3" data-toggle="modal" data-target="#editUserModal">
                    Edit user
                </button>
                <button type="button" class="btn btn-dark m-3" data-toggle="modal" data-target="#changePasswordModal">
                    Change password
                </button>
                <button type="button" class="btn btn-danger m-3" data-toggle="modal" data-target="#deleteAccountModal">
                    Delete Account
                </button>
            </div>
        </div>
        <div class="col-md-1">
        </div>
    </div>
</div>

<!-- Delete account modal -->
<!-- Modal -->
<div class="modal fade" id="deleteAccountModal" tabindex="-1" role="dialog" aria-labelledby="deleteAccountModalTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteAccountModalTitle">Delete Account</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this account?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <a href="${pageContext.request.contextPath}/user/delete" class="btn btn-danger">Delete</a>
            </div>
        </div>
    </div>
</div>
<!-- Change password modal -->
<!-- Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="changePasswordModalTitle">Change Password</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${pageContext.request.contextPath}/user/changepw" method="POST" id="changepw">
                <div class="modal-body">
                    <p><em>Change your password here.</em></p>
                    <div class="form-row">
                        <label for="password">New password</label>
                        <div class="col-sm-6" id="result" style="font-weight:bold;padding:6px 12px; display: inline">
                    </div>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="<fmt:message key="login.label.password" />" required>
                    </div>
                    <hr class="my-4">
                    <div class="form-row">
                        <label for="confirmation"><fmt:message key="register.label.confirm"/></label>
                        <input type="password" class="form-control" id="confirmation" name="confirmation"
                               placeholder="<fmt:message key="register.label.confirm" />" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary" id="confirmChangePassword">Confirm</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- Edit user modal -->
<!-- Modal -->
<div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="editUserModalTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalTitle">Edit user</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="${pageContext.request.contextPath}/user/profile" method="POST"
                      enctype="multipart/form-data">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="firstName">First Name</label>
                            <input type="text" class="form-control" id="firstName" placeholder="First Name"
                                   name="firstName"
                                   value="${user.firstName}">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="lastName">Last Name</label>
                            <input type="text" class="form-control" id="lastName" placeholder="Last Name"
                                   name="lastName"
                                   value="${user.lastName}">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group  col-md-12">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="avatar" name="avatar">
                                <label class="custom-file-label" for="avatar">Add your avatar</label>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Modify</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="parts/_footer.jspf" %>
<%@include file="parts/_importsjs.jspf" %>
<script type="text/javascript">
    $(document).ready(function() {
        $('#password').on('keyup', function () {
            var strength = 0;
            var password = $('#password').val();
            var confirmation = $("#confirmation").val();

            if (password.length > 7) strength += 1
            if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))  strength += 1
            if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/))  strength += 1
            if (password.match(/([!,%,&,@,#,$,^,*,?,_,~,.])/))  strength += 1
            if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~,.].*[!,%,&,@,#,$,^,*,?,_,~,.])/)) strength += 1
            if (strength <= 3) {
                $('#confirmChangePassword').prop('disabled', true);
                $('#result').html('Password debole').css('color', 'red');
            } else {
                $('#confirmChangePassword').prop('disabled', false);
                $('#result').html('')
            }
        });

    });
</script>

</body>

</html>