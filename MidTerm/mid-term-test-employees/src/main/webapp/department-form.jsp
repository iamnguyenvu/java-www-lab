<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 11:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Departments Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <c:choose>
    <c:when test="${not empty department}">
      <h2>Edit Department</h2>
      <form action="${pageContext.request.contextPath}/departments" method="post" class="form">
        <input type="hidden" name="id" value="${department.id}"/>
        <input type="hidden" name="action" value="edit"/>
        Name: <input type="text" name="name" value="${department.name}"/><br/>
        <button type="submit">Edit</button>
      </form>

    </c:when>
    <c:otherwise>
      <h2>Create Department</h2>
      <form action="${pageContext.request.contextPath}/departments" method="post">
        <input type="hidden" name="action" value="create"/>
        Name: <input type="text" name="name"/><br/>
        <button type="submit">Create</button>
      </form>

    </c:otherwise>
  </c:choose>
  <a href="${pageContext.request.contextPath}/departments">Departments</a>
</div>
</body>
</html>

