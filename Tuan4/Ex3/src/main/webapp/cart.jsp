<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 9/17/2025
  Time: 7:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
    <div class="container">
        <h1 style="font-weight: bold">Cart</h1>
        <c:choose>
            <c:when test="${empty sessionScope.cart.items}">
                <h3>Your cart is empty.</h3>
            </c:when>
            <c:otherwise>
                <table class="table" border="1" style="border-collapse: collapse; width: 100%;">
                    <tr>
                        <th>Model</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach items="${sessionScope.cart.items}" var="item">
                        <tr>
                            <td>${item.product.model}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/cart"
                                      style="display: inline">
                                    <input type="hidden" name="action" value="update"/>
                                    <input type="hidden" name="productId" value="${item.product.id}"/>
                                    <input type="number" name="quantity" value="${item.quantity}" min="1"/>
                                    <input type="submit" value="Update"/>
                                </form>
                            </td>
                            <td>$${item.product.price}</td>
                            <td>$${item.subtotal}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/cart">
                                    <input name="action" type="hidden" value="remove"/>
                                    <input name="productId" type="hidden" value="${item.product.id}"/>
                                    <input type="submit" value="Remove"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <h3>Total Amount: $${sessionScope.cart.total}</h3>
                <form method="post" action="${pageContext.request.contextPath}/cart">
                    <input name="action" type="hidden" value="clear"/>
                    <input type="submit" value="Clear Cart" style="background-color: red; color: white;"/>
                </form>
            </c:otherwise>
        </c:choose>
        <br/>
        <a href="${pageContext.request.contextPath}/products">Continue Shopping</a>
    </div>
</body>
</html>
