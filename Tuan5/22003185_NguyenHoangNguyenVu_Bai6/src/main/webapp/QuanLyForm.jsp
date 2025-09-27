<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/27/2025
  Time: 9:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quan Ly Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Danh Sach Tin Tuc</h2>
    <table class="table table-primary">
        <tr>
            <th>Ma Tin Tuc</th>
            <th>Tieu De</th>
            <th colspan="4">Noi Dung Tin Tuc</th>
            <th>Lien Ket</th>
            <th>Ma Danh Muc</th>
            <th>Action</th>
        </tr>
        <c:forEach var="tt" items="${tinTucs}">
            <tr>
                <td>${tt.maTT}</td>
                <td>${tt.tieuDe}</td>
                <td colspan="4">${tt.noiDungTT}</td>
                <td>${tt.lienKet}</td>
                <td>${tt.maDM}</td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/danh-sach-tin-tuc">
                        <input type="hidden" name="maTT" value="${tt.maTT}"/>
                        <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc?action=delete&maTT=${tt.maTT}">Delete</a>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
<a href="${pageContext.request.contextPath}/danh-sach-tin-tuc">Danh sach Tin Tuc</a>
</div>
</body>
</html>
