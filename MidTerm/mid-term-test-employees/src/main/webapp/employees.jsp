<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Employees List</h2>
    <a href="${pageContext.request.contextPath}/employees?action=create">Add Employee</a>
    <table class="table table-primary">
        <tr>
            <th>ID</th>
            <th>Name Employee</th>
            <th>Salary</th>
            <th>Dept</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${employees}" var="e">
            <tr>
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>${e.salary}</td>
                <td>${e.department.id}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/employees?action=edit&id=${e.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/employees?action=delete&id=${e.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/departments">View Departments</a>
</div>
</body>
</html>
