<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 9/25/2025
  Time: 11:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Form</title>
</head>
<body>
<div class="container">
    <form action="${pageContext.request.contextPath}/employees" method="post">
        <c:choose>
            <c:when test="${not empty employee}">
                <h2>Edit Employee</h2>
                Name <input name="name" value="${employee.name}"/>
                Salary <input name="salary" value="${employee.salary}"/>
                <input type="hidden" name="id" value="${employee.id}"/>
                Department:
                <c:if test="${not empty departments}">
                    <select name="departmentId">
                        <c:forEach var="dep" items="${departments}">
                            <option value="${dep.id}"
                                    <c:if test="${dep.id == employee.departmentId}">selected</c:if>>${dep.name}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <input type="submit" value="Update"/>
            </c:when>
            <c:otherwise>
                <input type="hidden" name="id"/>
                Name <input name="name"/>
                Salary <input name="salary"/>
                Department:
                <select name="departmentId">
                    <c:forEach var="dep" items="${departments}">
                        <option value="${dep.id}">${dep.name}</option>
                    </c:forEach>
                </select>

                <input type="submit" value="Save"/>
            </c:otherwise>
        </c:choose>

    </form>
</div>
</body>
</html>
