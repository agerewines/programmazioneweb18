<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 12/10/2018
  Time: 09:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>

<body>
<p style="color: red;">${errorMessage}</p><br/>
<form method="POST" action="${pageContext.request.contextPath}/login">
    <table border="0">
        <tr>
            <td>Email</td>
            <td><input type="email" name="email" value= "${user.mail}" /> </td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" name="password" value= "${user.password}" /> </td>
        </tr>
        <tr>
            <td>Remember me</td>
            <td colspan = "2">
                <input type="checkbox" name="rememberMe" value= "Y" />
                <a href="${pageContext.request.contextPath}/password/new">Forgot password?</a>
            </td>
        </tr>
        <tr>
            <td colspan ="2">
                <input type="submit" value= "Login" />
                <a href="${pageContext.request.contextPath}/${referer}">Cancel</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
