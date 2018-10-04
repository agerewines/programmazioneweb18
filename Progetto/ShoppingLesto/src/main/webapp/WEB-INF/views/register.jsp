<%-- 
    Document   : register
    Created on : Oct 3, 2018, 4:37:26 PM
    Author     : alessandrogerevini
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/register" method="POST">
            <table>
            <tr>
               <td>First Name</td>
               <td><input type="text" name="firstName"/> </td>
            </tr>
            <tr>
               <td>Last Name</td>
               <td><input type="text" name="lastName"/> </td>
            </tr>
            <tr>
               <td>Email</td>
               <td><input type="email" name="mail"/> </td>
            </tr>
            <tr>
               <td>Password</td>
               <td><input type="password" name="password"/> </td>
            </tr>
            <tr>
               <td>Password Confirmation</td>
               <td><input type="password" name="confirmation"/> </td>
            </tr>
            <tr>
               <td colspan ="2">
                  <input type="submit" value= "Register" />
               </td>
            </tr>
         </table>
        </form>
    </body>
</html>
