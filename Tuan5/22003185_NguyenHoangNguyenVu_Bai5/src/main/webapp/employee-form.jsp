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
        <input type="hidden" name="id"/>
        Name <input name="name"/>
        Salary <input name="salary" />
        Department:
        <select name="deparmentId">
            <c:forEach var="dep" items="${deparments}">
                <option value="${dep.department_id}">${dep.name}</option>
            </c:forEach>
        </select>

        <input type="submit" value="Save" />
    </form>
</div>
</body>
</html>
