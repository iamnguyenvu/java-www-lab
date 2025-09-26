<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/17/2025
  Time: 8:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Detail</title>
</head>
<body>
    <h2>Product Detail</h2>
    <c:if test="${not empty product}">
        <div>
            <h3 style="font-weight: bold">${product.model}</h3>
            <img src="images/${product.imgUrl}" alt="${product.model}"/>
            <p>Price: $${product.price}</p>
            <p>Description: ${product.description}</p>
            <form method="post" action="${pageContext.request.contextPath}/cart">
                <input type="text" size="2" value="1" name="quantity"/>
                <input type="hidden" name="productId" value="${product.id}"/>
                <input type="hidden" name="model" value="${product.model}"/>
                <input type="hidden" name="price" value="${product.price}"/>
                <input type="hidden" name="action" value="add"/>
                <input type="submit" name="addToCart" value="Add to Cart"/>
            </form>
        </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/product">Back to Product List</a>
</body>
</html>
