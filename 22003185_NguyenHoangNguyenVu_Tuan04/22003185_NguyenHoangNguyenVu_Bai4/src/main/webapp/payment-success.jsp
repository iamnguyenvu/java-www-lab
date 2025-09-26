<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="text-align: center; padding: 40px;">
    <h2>Payment Successful!</h2>
    <p>Thank you for your order. Your payment has been processed successfully.</p>
    <p>You will receive a confirmation email shortly.</p>

    <div style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/books">
            <button type="button" style="padding: 10px 20px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">Continue Shopping</button>
        </a>
    </div>
</div>
