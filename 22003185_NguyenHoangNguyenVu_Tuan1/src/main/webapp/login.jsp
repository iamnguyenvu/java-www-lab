<%--
  Created by IntelliJ IDEA.
  User: Student
  Date: 8/21/2025
  Time: 11:16 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <h1>DANG NHAP</h1>
    <div>
        <label for="username">Ten dang nhap: </label>
        <input type="text" name="username" id="username">
    </div>
    <div>
        <label for="password">Mat khau: </label>
        <input type="password" name="password" id="password">
    </div>
    <button type="submit">Dang nhap</button>
</form>

</body>
</html>
