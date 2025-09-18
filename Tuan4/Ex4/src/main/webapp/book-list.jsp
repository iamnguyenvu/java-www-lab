<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .book-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
        margin-top: 20px;
    }
    
    .book-card {
        border: 1px solid #ddd;
        border-radius: 8px;
        padding: 15px;
        background: white;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        max-width: 200px;
    }
    
    .book-image {
        max-width: 100px;
        max-height: 160px;
        width: 100%;
        height: auto;
        object-fit: cover;
        border-radius: 4px;
        margin-bottom: 10px;
        display: block;
        margin-left: auto;
        margin-right: auto;
    }
    
    .book-title {
        font-size: 18px;
        font-weight: bold;
        margin: 10px 0 5px 0;
        color: #333;
    }
    
    .book-price {
        font-size: 20px;
        font-weight: bold;
        color: #4CAF50;
        margin: 10px 0;
    }
    
    .book-quantity {
        color: #666;
        margin-bottom: 15px;
    }
    
    .book-actions {
        display: flex;
        gap: 10px;
    }
    
    .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        text-decoration: none;
        text-align: center;
        font-size: 14px;
        flex: 1;
        display: inline-flex;
        align-items: center;
        justify-content: center;
    }
    
    .btn-primary {
        background-color: #007bff;
        color: white;
    }
    
    .btn-success {
        background-color: #28a745;
        color: white;
    }
</style>

<h2>Book List</h2>

<div class="book-grid">
    <c:forEach items="${books}" var="book">
        <div class="book-card">
            <c:if test="${not empty book.url}">
                <img src="images/${book.url}" alt="${book.title}" class="book-image"/>
            </c:if>
            
            <div class="book-title">${book.title} <c:if test="${not empty book.author}"> - Author: ${book.author}</c:if></div>
            
            <div class="book-price">
                Price: <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/>
            </div>
            
            <div class="book-quantity">Quantity: ${book.quantity}</div>

            <div class="book-actions">
                <a href="${pageContext.request.contextPath}/book?id=${book.id}" class="btn btn-primary">
                    View Details
                </a>
                <form action="${pageContext.request.contextPath}/cart" method="post" style="flex: 1;">
                    <input type="hidden" name="bookId" value="${book.id}"/>
                    <input type="hidden" name="action" value="add"/>
                    <button type="submit" class="btn btn-success" style="width: 100%;">Add to Cart</button>
                </form>
            </div>
        </div>
    </c:forEach>
</div>
