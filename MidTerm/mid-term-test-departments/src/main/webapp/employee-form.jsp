<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 10/1/2025
  Time: 11:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <c:choose>
    <c:when test="${not empty employee}">
        <h2>Edit Employee</h2>
        <form action="${pageContext.request.contextPath}/employees" method="post">
            <input name="action" type="hidden" value="edit">
            <input name="id" type="hidden" value="${employee.id}">
            Name: <input name="name" value="${employee.name}"> <br>
            Salary: <input name="salary" value="${employee.salary}"> <br>
            Dept:
            <select name="departmentId">
                <c:forEach items="${departments}" var="d">
                    <option value="${d.id}" <c:if test="${departmentId == d.id}">selected</c:if>>${d.name}</option>
                </c:forEach>
            </select>
            <button type="submit">Edit</button>
        </form>
    </c:when>

    <c:otherwise>
    <h2>Add Employee</h2>
    <form action="${pageContext.request.contextPath}/employees" method="post">
        <input name="action" type="hidden" value="create">
        Name: <input name="name"> <br>
        Salary: <input type="number" name="salary" required> <br>
        Dept:
        <select name="departmentId">
            <c:forEach items="${departments}" var="d">
                <option value="${d.id}">${d.name}</option>
            </c:forEach>
        </select>
        <button type="submit">Create</button>
        </c:otherwise>
        </c:choose>
</div>
</body>
</html>
