<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 8/22/2025
  Time: 1:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>UPLOAD
    <MULTI-></MULTI->
    FILES
</h1>
<form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data">
    <div>
        <h3>File #1</h3>
        <input type="file" name="file">
    </div>
    <div>
        <h3>File #2</h3>
        <input type="file" name="file">
    </div>
    <div>
        <h3>File #3</h3>
        <input type="file" name="file">
    </div>
    <div>
        <h3>File #4</h3>
        <input type="file" name="file">
    </div>
    <div>
        <h3>File #5</h3>
        <input type="file" name="file">
    </div>
    <div>
        <button type="submit">Upload</button>
        <button type="reset">Reset</button>
    </div>
</form>
</body>
</html>
