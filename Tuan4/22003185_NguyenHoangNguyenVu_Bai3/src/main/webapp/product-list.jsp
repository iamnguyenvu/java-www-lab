<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/17/2025
  Time: 7:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Product List</title>
    <style>
        body {font-family: Arial, sans-serif; margin: 20px;}
        .card {
            background: #fff; border: 1px solid; border-radius: 6px;
            padding: 10px; width: 180px; text-align: center;
        }
    </style>
</head>
<body>
<h1>Product List</h1>
<div style="display: flex; flex-wrap: wrap; gap: 20px;">
    <a href="cart.jsp" style="width: 100%; margin-bottom: 20px;">View Cart</a>

    <c:if test="${empty products}">
        <p>No products found or products attribute is null</p>
    </c:if>

    <c:forEach items="${products}" var="p">
        <div class="card">
            <h3 style="font-weight: bold">${p.model}</h3>
            <c:if test="${not empty p.imgUrl}">
                <img src="images/${p.imgUrl}" alt="${p.model}" style="width: 150px; height: 150px; object-fit: cover;"/>
            </c:if>
            <p>Price: $${p.price}</p>
            <p>Quantity: ${p.quantity}</p>
            <p>Description: ${p.description}</p>
            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="text" size="2" value="1" name="quantity"/>
                <input type="hidden" name="productId" value="${p.id}"/>
                <input type="hidden" name="model" value="${p.model}"/>
                <input type="hidden" name="price" value="${p.price}"/>
                <input type="hidden" name="action" value="add"/>
                <input type="submit" name="addToCart" value="Add to Cart"/>
            </form>
            <a href="${pageContext.request.contextPath}/product?productId=${p.id}">Product Detail</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
