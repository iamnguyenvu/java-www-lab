<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .payment-form {
        max-width: 600px;
        margin: 0 auto;
    }
    .payment-table {
        width: 100%;
        border-collapse: collapse;
    }
    .payment-table td {
        padding: 10px;
        vertical-align: top;
    }
    .payment-table input[type="text"], .payment-table textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    .payment-methods {
        display: flex;
        gap: 15px;
        flex-wrap: wrap;
    }
    .payment-methods label {
        display: flex;
        align-items: center;
        gap: 5px;
    }
    .btn {
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin: 0 5px;
    }
    .btn-primary {
        background-color: #007bff;
        color: white;
    }
    .btn-secondary {
        background-color: #6c757d;
        color: white;
    }
</style>

<h2>Checkout</h2>

<c:choose>
    <c:when test="${empty sessionScope.cart.cartBooks}">
        <p>Your cart is empty. Please add items to your cart before proceeding to checkout.</p>
        <a href="${pageContext.request.contextPath}/books">Continue Shopping</a>
    </c:when>
    <c:otherwise>
        <div class="payment-form">
            <form method="post" action="${pageContext.request.contextPath}/payment">
                <table class="payment-table">
                    <tr>
                        <td style="width: 30%;"><label for="fullname">Full Name:</label></td>
                        <td><input type="text" id="fullname" name="fullname" required/></td>
                    </tr>
                    <tr>
                        <td><label for="address">Shipping Address:</label></td>
                        <td><textarea id="address" name="address" rows="3" required></textarea></td>
                    </tr>
                    <tr>
                        <td><label for="totalPrice">Total Price:</label></td>
                        <td>
                            <input type="text" id="totalPrice" value="<fmt:formatNumber value='${param.totalPrice}' type='number' groupingUsed='true'/>" readonly style="background-color: #f0f0f0;"/>
                            <input type="hidden" name="totalPrice" value="${param.totalPrice}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Payment Method:</td>
                        <td>
                            <div class="payment-methods">
                                <label><input type="radio" name="paymentMethod" value="paypal" required/> PayPal</label>
                                <label><input type="radio" name="paymentMethod" value="atm" required/> ATM</label>
                                <label><input type="radio" name="paymentMethod" value="debit" required/> Debit Card</label>
                                <label><input type="radio" name="paymentMethod" value="visa" required/> Visa/MasterCard</label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center; padding-top: 20px;">
                            <button type="submit" class="btn btn-primary">Complete Order</button>
                            <a href="${pageContext.request.contextPath}/cart">
                                <button type="button" class="btn btn-secondary">Cancel</button>
                            </a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </c:otherwise>
</c:choose>
