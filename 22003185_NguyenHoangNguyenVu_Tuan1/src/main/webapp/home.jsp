<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 8/22/2025
  Time: 1:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Xin chao, ${sessionScope.user}</h1>
<a href="${pageContext.request.contextPath}/secure/secret.jsp">Thu muc bi mat</a>
<a href="${pageContext.request.contextPath}/logout">Dang xuat</a>
</body>
</html>
