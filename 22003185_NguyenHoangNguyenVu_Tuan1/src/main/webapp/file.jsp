<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 8/22/2025
  Time: 9:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload to Database</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/fileUploadToDatabase" method="post" enctype="multipart/form-data">
    <h1>File Upload to Database</h1>
    First Name: <input type="text" name="firstName" required/><br/>
    Last Name: <input type="text" name="lastName" required/><br/>
    Portrait Photo: <input type="file" name="photo" accept="image/*" required/><br/>
    <button type="submit">Save</button>
</form>
</body>
</html>
