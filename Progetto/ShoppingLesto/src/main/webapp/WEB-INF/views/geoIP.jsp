<%--
  Created by IntelliJ IDEA.
  User: samuele
  Date: 05/11/18
  Time: 20.08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Geolocation</title>
</head>
<body>
    <form id="ipForm" action="${pageContext.request.contextPath}/geoip" method="POST">
        <input type="text" name="ipAddress" id="ip"/>
        <input type="submit" name="submit" value="submit" />
    </form>

    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js">
    </script>

    <script type="text/javascript">
        $(document).ready (function () {
            $.get( "https://api.ipify.org?format=json",
                function( data ) {
                    $("#ip").val(data.ip) ;
                });
    </script>-->

    <iframe width="425" height="350" src="${map}"></iframe>

</body>
</html>