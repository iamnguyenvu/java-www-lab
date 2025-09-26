<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/26/2025
  Time: 10:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Department List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container">
    <h2>Department List</h2>
    <a href="${pageContext.request.contextPath}/departments?action=new">Add Department</a>
    <table class="table table-primary">
        <tr>
            <th>ID</th>
            <th>Department Name</th>
            <th>Action</th>
        </tr>
        <c:forEach var="dep" items="${departments}">
            <tr>
                <td>${dep.id}</td>
                <td>${dep.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/employees?action=view&departmentId=${dep.id}">View Employees</a>
                    <a href="${pageContext.request.contextPath}/departments?action=edit&id=${dep.id}">Edit</a>
                    <a href="${pageContext.request.contextPath}/departments?action=delete&id=${dep.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
