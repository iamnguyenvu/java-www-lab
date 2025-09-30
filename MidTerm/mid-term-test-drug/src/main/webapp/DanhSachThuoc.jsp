<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/30/2025
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sach thuoc</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/quan-ly-thuoc?action=category">Danh sach cac loai thuoc</a> |
    <a href="${pageContext.request.contextPath}/quan-ly-thuoc">Danh sach thuoc</a> |
    <a href="${pageContext.request.contextPath}/quan-ly-thuoc?action=insert">Them thuoc</a>
    <div>
        <form action="${pageContext.request.contextPath}/quan-ly-thuoc" method="get">
            <input type="hidden" name="action" value="search"/>
            <input name="keyword" value="${param.keyword}" required/>
            <button type="submit">Tim kiem</button>
        </form>
    </div>

    <form action="${pageContext.request.contextPath}/quan-ly-thuoc" method="get">
        <input type="hidden" name="action" value="filter"/>
        <select name="maLoai">
            <option value="">Tat ca</option>
            <c:forEach items="${loaiThuoc}" var="loai">
                <option value="${loai.maLoai}"
                        <c:if test="${maLoai == loai.maLoai}">selected</c:if>>${loai.tenLoai}</option>
            </c:forEach>
        </select>
        <button type="submit">Loc</button>
    </form>

    <h2>Danh sach thuoc</h2>
    <table class="table table-primary">
        <tr>
            <th>Ma thuoc</th>
            <th>Ten thuoc</th>
            <th>Loai thuoc</th>
            <th>Gia</th>
            <th>Nam sx</th>
        </tr>
        <c:forEach items="${listThuoc}" var="thuoc">
            <tr>
                <td>${thuoc.maThuoc}</td>
                <td>${thuoc.tenThuoc}</td>
                <td>${thuoc.loaiThuoc.tenLoai}</td>
                <td>${thuoc.gia}</td>
                <td>${thuoc.namSX}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>