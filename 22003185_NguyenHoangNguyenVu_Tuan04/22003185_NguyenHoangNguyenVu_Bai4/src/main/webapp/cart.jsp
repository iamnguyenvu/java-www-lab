<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .cart-table {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
    }
    .cart-table th, .cart-table td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }
    .cart-table th {
        background-color: #f4f4f4;
    }
    .cart-actions {
        display: flex;
        gap: 10px;
        margin-top: 15px;
    }
    .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .btn-primary {
        background-color: #007bff;
        color: white;
    }
    .btn-secondary {
        background-color: #6c757d;
        color: white;
    }
    .btn-danger {
        background-color: #dc3545;
        color: white;
    }
</style>

<div>
    <h2>Shopping Cart</h2>
    <div>
        <table class="cart-table">
            <tr>
                <th>Book ID</th>
                <th>Title</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total</th>
                <th>Remove</th>
            </tr>
            <c:forEach items="${sessionScope.cart.cartBooks}" var="item">
                <tr>
                    <td>${item.book.bookId}</td>
                    <td>${item.book.title}</td>
                    <td><fmt:formatNumber value="${item.book.price}" type="number" groupingUsed="true"/></td>
                    <td>${item.quantity}</td>
                    <td><fmt:formatNumber value="${item.total}" type="number" groupingUsed="true"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="bookId" value="${item.book.bookId}"/>
                            <input type="hidden" name="action" value="remove"/>
                            <button type="submit" class="btn btn-danger">Remove</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    
    <div style="text-align: right; margin: 15px 0; padding: 10px; background-color: #f8f9fa; border: 1px solid #dee2e6;">
        <h3 style="margin: 0; color: #333;">Total Price: <span style="color: #28a745;"><fmt:formatNumber value="${sessionScope.cart.totalPrice}" type="number" groupingUsed="true"/></span></h3>
    </div>
    
    <div class="cart-actions">
        <form action="${pageContext.request.contextPath}/payment" method="get">
            <input type="hidden" name="totalPrice" value="${sessionScope.cart.totalPrice}">
            <button type="submit" class="btn btn-primary">Checkout</button>
        </form>
        <form action="${pageContext.request.contextPath}/books">
            <button type="submit" class="btn btn-secondary">Continue Shopping</button>
        </form>
    </div>
</div>
