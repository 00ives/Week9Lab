<%-- 
    Document   : users
    Created on : 19-Oct-2022, 12:11:37 PM
    Author     : ivorl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <table border="1" >
            <thead >
                <tr style=" background-color: #777777" >
                    <th >Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userList}" var="user" varStatus="loop">
                    <tr style=" background-color: #96D4D4">
                        <th>${user.getEmail()}</th>
                        <th>${user.getFirstName()}</th>
                        <th>${user.getLastName()}</th>
                        <th>${user.getRoleId()}</th>
                        <th><a href="User?editUser=${loop.index}" >Edit</a></th>
                        <th><a href="User?delete=${loop.index}" >Delete</a></th>
                    </tr>
                </c:forEach>
            </tbody>


        </table>
        <!--add functionality for if its add a user then 
        display add user however when its edit user change txt to edit user-->
        <h2>${addOrEdit}</h2>

        <form action="User" method="post">

            <c:choose>
                <c:when test="${addOrEdit == 'Edit User'}">
                    <div>Email: ${selectedUser.getEmail()}</div>
                </c:when>

                <c:otherwise>
                    Email: <input type="text" >
                    <br>
                </c:otherwise>
            </c:choose>

            First name: <input type="text" >
            <br>
            Last name: <input type="text" >
            <br>
            Password: <input type="text" >
            <br>
            Role:   <select name="role" type="text">
                <option value="systemAdmin">dont hardcode this value but for now System Admin</option>
                <option value="regularUser">Regular User</option>
            </select>
            <br>
            <input type="submit" method="post" value="Add user">

        </form>

    </body>
</html>
