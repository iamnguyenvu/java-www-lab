<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 10/1/2025
  Time: 11:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employees</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <h2>Employee List</h2>
  <a href="${pageContext.request.contextPath}/employees?action=create">Add Employee</a>
  <table class="table table-primary">
    <tr>
      <th>ID</th>
      <th>Name Employee</th>
      <th>Salary</th>
      <th>Dept</th>
      <th>Action</th>
    </tr>
    <c:forEach items="${employees}" var="d">
      <tr>
        <td>${d.id}</td>
        <td>${d.name}</td>
        <td>${d.salary}</td>
        <td>${d.department.id}</td>
        <td>
          <a href="${pageContext.request.contextPath}/employees?action=edit&id=${d.id}">Edit</a> |
          <a href="${pageContext.request.contextPath}/employees?action=delete&id=${d.id}">Delete</a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <a href="${pageContext.request.contextPath}/departments">Department</a>
</div>
</body>
</html>
