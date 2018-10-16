<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <title></title>
  <%@include file="parts/_imports.jspf" %>
</head>
<body>

  <div class="row">
    <div class="col-sm-8">
      <c:choose>
        <c:when test="${not empty errorMessage}">
          <c:out value="${errorMessage}"/>
          <c:remove var="errorMessage"/>
        </c:when>
      </c:choose>
    </div>
  </div>
</body>
</html>
