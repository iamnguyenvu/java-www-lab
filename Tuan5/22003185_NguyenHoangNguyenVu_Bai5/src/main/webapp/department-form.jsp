<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/26/2025
  Time: 11:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Department Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${not empty department}">
            <h2>Edit Department</h2>
            <form action="${pageContext.request.contextPath}/departments" method="post">
                <input type="hidden" name="id" value="${department.id}"/>
                <input type="hidden" name="aciton" value="update"/>
                Name <input name="name" value="${department.name}"/>
                <input type="submit" value="Update"/>
            </form>
        </c:when>
        <c:otherwise>
            <h2>Add Department</h2>
            <form action="${pageContext.request.contextPath}/departments" method="post">
                <input type="hidden" name="action" value="insert"/>
                Name <input name="name"/>
                <input type="submit" value="Save"/>
            </form>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
