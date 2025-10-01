<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 11:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Department List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Departments List</h2>
    <a href="${pageContext.request.contextPath}/departments?action=create">Add Department</a>
    <div>
        Tim phong ban:
        <form action="${pageContext.request.contextPath}/departments" method="get">
            <input type="hidden" name="action" value="search"/>
            <input name="keyword" value="${param.keyword}">
            <button type="submit">Search</button>
        </form>
    </div>
    <table class="table table-primary">
        <tr>
            <th>DEPT ID</th>
            <th>Name Department</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${departments}" var="e">
            <tr>
                <td>${e.id}</td>
                <td>${e.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/departments?action=edit&id=${e.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/departments?action=delete&id=${e.id}">Delete</a> |
                    <a href="${pageContext.request.contextPath}/departments?action=view&id=${e.id}">Employees</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/employees">View Employees</a>
</div>
</body>
</html>

