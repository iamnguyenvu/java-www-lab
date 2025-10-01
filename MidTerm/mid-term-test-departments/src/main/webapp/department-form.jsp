<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 10/1/2025
  Time: 10:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Department Form</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <c:choose>
       <c:when test="${not empty department}">
           <h2>Edit Department</h2>
           <form action="${pageContext.request.contextPath}/departments" method="post">
               <input name="action" type="hidden" value="edit">
               <input name="id" type="hidden" value="${department.id}">
               Name: <input name="name" value="${department.name}"> <br>
                <button type="submit">Edit</button>
           </form>
       </c:when>

     <c:otherwise>
        <h2>Add Department</h2>
        <form action="${pageContext.request.contextPath}/departments" method="post">
            <input name="action" type="hidden" value="create">
            Name: <input name="name"> <br>
            <button type="submit">Create</button>
     </c:otherwise>
    </c:choose>
</div>
</body>
</html>
