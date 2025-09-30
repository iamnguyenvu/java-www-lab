<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 8:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Thuoc Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Form them thuoc</h2>
    <form action="${pageContext.request.contextPath}/quan-ly-thuoc" method="post" class="form">
        <input type="hidden" name="action" value="insert">
        Ten thuoc: <input name="tenThuoc" required />
        Gia: <input name="gia" type="number" required />
        Nam sx: <input name="namSX" type="number" required />
        <select name="maLoai">
            <c:forEach items="${loaiThuoc}" var="loai">
                <option value="${loai.maLoai}">${loai.tenLoai}</option>
            </c:forEach>
        </select>
        <button type="submit">Them</button>
    </form>
</div>
</body>
</html>
