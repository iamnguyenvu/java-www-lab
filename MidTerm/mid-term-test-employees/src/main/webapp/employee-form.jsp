<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employees Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${not empty employee}">
            <h2>Edit Employee</h2>
            <form action="${pageContext.request.contextPath}/employees" method="post">
                <input type="hidden" name="action" value="edit"/>
                <input type="hidden" name="id" value="${employee.id}"/>
                Name: <input type="text" name="name" value="${employee.name}"/><br/>
                Salary: <input type="text" name="salary" value="${employee.salary}"/><br/>
                Dept:
                <select name="departmentId">
                    <c:forEach items="${departments}" var="d">
                        <option value="${d.id}"
                                <c:if test="${d.id == employee.department.id}">selected</c:if>>${d.name}</option>
                    </c:forEach>
                </select>
                <button type="submit">Edit</button>
            </form>

        </c:when>
        <c:otherwise>
            <h2>Create Employee</h2>
            <form action="${pageContext.request.contextPath}/employees" method="post">
                <input type="hidden" name="action" value="create"/>
                Name: <input type="text"  name="name"/><br/>
                Salary: <input type="text" name="salary"/><br/>
                Dept:
                <select name="departmentId">
                    <c:forEach items="${departments}" var="d">
                        <option value="${d.id}">${d.name}</option>
                    </c:forEach>
                </select>
                <button type="submit">Create</button>
            </form>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/employees">Employees</a>
</div>
</body>
</html>
