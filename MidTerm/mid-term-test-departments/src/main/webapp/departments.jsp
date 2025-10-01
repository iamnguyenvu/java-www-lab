<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 10/1/2025
  Time: 10:14 PM
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
    <h1>Department List</h1>
    <a href="${pageContext.request.contextPath}/departments?action=create">Add Department</a>
    <form action="${pageContext.request.contextPath}/departments">
        <input type="hidden" name="action" value="search">
        <input name="keyword" value="${param.keyword}" >
        <button type="submit">Search</button> </br>
    </form>
    <table class="table table-primary">
        <tr>
            <th>DEPT ID</th>
            <th>Name Department</th>
            <th>Aciton</th>
        </tr>
        <c:forEach items="${departments}" var="d">
            <tr>
                <td>${d.id}</td>
                <td>${d.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/departments?action=edit&id=${d.id}">Edit</a> |
                    <a href="${pageContext.request.contextPath}/departments?action=delete&id=${d.id}">Delete</a> |
                    <a href="${pageContext.request.contextPath}/departments?action=view&id=${d.id}">Employees</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
