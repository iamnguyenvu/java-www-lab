<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/27/2025
  Time: 9:03 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh Sach Tin Tuc</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <c:choose>
        <c:when test="${not empty listTinTucByDanhMuc}">
            <h2>Danh Sach Tin Tuc Theo Danh Muc</h2>
            <c:forEach var="dm" items="${danhMucs}">
                <h2>Danh Muc ${dm.tenDanhMuc}</h2>
                <table class="table table-primary">
                    <tr>
                        <th>Ma Tin Tuc</th>
                        <th>Tieu De</th>
                        <th colspan="4">Noi Dung Tin Tuc</th>
                        <th>Lien Ket</th>
                        <th>Ma Danh Muc</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach var="tt" items="${listTinTucByDanhMuc[dm.maDM]}">
                        <tr>
                            <td>${tt.maTT}</td>
                            <td>${tt.tieuDe}</td>
                            <td colspan="4">${tt.noiDungTT}</td>
                            <td>${tt.lienKet}</td>
                            <td>${tt.maDM}</td>
                            <td>
                                    <%--                                <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc?action=view&maTT=${tt.maTT}">View</a>--%>
                                <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc?action=edit&maTT=${tt.maTT}">Edit</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </c:forEach>
        </c:when>
        <c:otherwise>
        </c:otherwise>
    </c:choose>
    <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc?action=new">Them </a><br/>
    <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc?action=manage">Huy du lieu</a>
</div>
</body>
</html>
