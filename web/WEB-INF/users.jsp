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
                        <th>${user.getRole()}</th>
                        <th><a href="User?editUser=${loop.index}" >Edit</a></th>
                        <th>
                            <form action="User?delete=${loop.index}" method="post" >
                                <button name="action" value="deleteUser" type="submit">Delete</button>
                            </form>
                        </th>
                    </tr>
                </c:forEach>
            </tbody>


        </table>
        <!--add functionality for if its add a user then 
        display add user however when its edit user change txt to edit user-->
        <h2>${addOrEdit}</h2>

        <form  action="User" method="post">

            <c:choose>
                <c:when test="${addOrEdit == 'Edit User'}">
                    <div>Email: ${selectedUser.getEmail()}</div>
                </c:when>

                <c:otherwise>
                    Email: <input type="text" name="emailInput">
                    <br>
                </c:otherwise>
            </c:choose>

            First name: <input type="text" name="firstNameInput">
            <br>
            Last name: <input type="text" name="lastNameInput">
            <br>
            Password: <input type="password" name="passwordInput" >
            <br>
            Role: 

            <select name="roleInput" type="text">
                <option value="1">dont hardcode this value but for now System Admin</option>
                <option value="2">Regular User</option>
            </select>

            <br>

            <c:if test="${addOrEdit == 'Add User'}">
                <input name="action" type="submit" value="Add user">
            </c:if>

            <c:if test="${addOrEdit == 'Edit User'}">
                <input name="action" type="submit" value="Update">
            </c:if>

        </form>

        <c:if test="${editUser != null || addOrEdit == 'Edit User'}">
            <form id="test" action="User?editUser=null" method="get"><button form="test" type="submit" value="cancel">Cancel</button></form>
        </c:if>
            <c:if test="${message != ''}">
                <div>${message}</div>
            </c:if>    

    </body>
</html>
