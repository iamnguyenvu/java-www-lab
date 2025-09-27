<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/27/2025
  Time: 9:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tin Tuc Form</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <form class="form four-column-summary" action="${pageContext.request.contextPath}/danh-sach-tin-tuc"
          method="post">
    <c:choose>
        <c:when test="${not empty tinTuc}">
            <h2>Chinh sua Tin Tuc</h2>
                <input type="hidden" name="maTT" value="${tinTuc.maTT}"/>
                Tieu De: <input required type="text" name="tieuDe" value="${tinTuc.tieuDe}"/><br/>
                Noi Dung Tin Tuc: <textarea required type="text" name="noiDungTT" value="${tinTuc.noiDungTT}" pattern="(?s)^.{0,255}" rows="4"
                                            maxlength="255" placeholder="Noi dung <= 255 ki tu"></textarea><br/>
                Lien Ket: <input required type="text" name="lienKet" value="${tinTuc.lienKet}" pattern="^http://.{2,248}$"
                                 placeholder="http://ex.com"/><br/>
                Danh Muc:
                <c:if test="${not empty danhMucs}">
                    <select name="maDM">
                        <c:forEach var="dm" items="${danhMucs}">
                            <option value="${dm.maDM}"
                                    <c:if test="${dm.maDM == tinTuc.maDM}">selected</c:if>
                            >${dm.tenDanhMuc}</option>
                        </c:forEach>
                    </select><br/>
                </c:if>
                <input type="submit" value="Chinh sua"/>
        </c:when>
        <c:otherwise>
            <h2>Them Tin Tuc</h2>
                Ma Tin Tuc: <input type="number" name="maTT" value="" required/><br/>
                Tieu De: <input type="text" name="tieuDe" value="" required/><br/>
                Noi Dung Tin Tuc: <textarea type="text" name="noiDungTT" value="" pattern="(?s)^.{0,255}" rows="4"
                                            maxlength="255" placeholder="Noi dung <= 255 ki tu" required></textarea><br/>
                Lien Ket: <input type="text" name="lienKet" value="" pattern="^http://.{2,248}$"
                                 placeholder="http://ex.com" required/><br/>
                Danh Muc:
                <select name="maDM">
                    <c:forEach var="dm" items="${danhMucs}">
                        <option value="${dm.maDM}">${dm.tenDanhMuc}</option>
                    </c:forEach>
                </select><br/>
                <input type="submit" value="Them"/>
        </c:otherwise>
    </c:choose>

    </form>
    <a href="${pageContext.request.contextPath}/danh-sach-tin-tuc">Danh sach Tin Tuc</a>
</div>
</body>
</html>
