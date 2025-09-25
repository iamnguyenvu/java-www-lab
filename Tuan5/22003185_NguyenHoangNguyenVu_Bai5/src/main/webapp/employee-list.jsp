<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/25/2025
  Time: 10:40 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Employees</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>

<div class="container">
    <h2>Employees List</h2>
    <a href="${pageContext.request.contextPath}/employees/action=new">Add Employee</a>
    <table class="table table-primary">
        <tr>
            <th>ID</th>
            <th>Employee Name</th>
            <th>Salary</th>
            <th>Department</th>
            <th>Action</th>
        </tr>

        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.salary}</td>
                <td>${emp.departmentId}</td>e
                <td>
                    <a href="${pageContext.request.contextPath}/employees?action=edit&id=${emp.id}">Edit</a>
                    <a href="${pageContext.request.contextPath}/employees?action=delete&id=${emp.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/departments">Department</a>

</div>

</body>
</html>
