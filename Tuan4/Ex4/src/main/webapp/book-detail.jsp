<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div>
    <c:choose>
        <c:when test="${empty book}">
            <div style="text-align: center; padding: 40px;">
                <h2>Book not found</h2>
                <form action="${pageContext.request.contextPath}/books" method="get">
                    <button type="submit">Back to Product List</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div style="display: flex; gap: 30px; align-items: flex-start;">
                <div style="flex: 1; max-width: 300px;">
                    <c:if test="${not empty book.url}">
                        <img src="${pageContext.request.contextPath}/images/${book.url}" 
                             alt="${book.title}" 
                             style="width: 100%; max-width: 250px; border: 1px solid #ddd;"/>
                    </c:if>
                </div>
                
                <div style="flex: 2;">
                    <h3>${book.title} 
                        <c:if test="${not empty book.author}"> - Author: ${book.author}</c:if>
                    </h3>
                    <p style="color: #4CAF50; font-weight: bold; font-size: 20px;">
                        Price: <fmt:formatNumber value="${book.price}" type="number" groupingUsed="true"/> VND
                    </p>
                    <p>Available: <strong>${book.quantity}</strong> books</p>
                    <p>Book ID: ${book.bookId}</p>
                    
                    <div style="margin-top: 20px;">
                        <c:choose>
                            <c:when test="${book.quantity > 0}">
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="add"/>
                                    <input type="hidden" name="bookId" value="${book.bookId}"/>
                                    <label for="quantity">Quantity:</label>
                                    <input type="number" id="quantity" name="quantity" value="1" min="1" max="${book.quantity}" required style="width: 60px; margin: 0 10px;"/>
                                    <button type="submit" style="background-color: #4CAF50; color: white; padding: 10px 20px; border: none; cursor: pointer;">
                                        Add to Cart
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button disabled style="background-color: #ccc; color: #666; padding: 10px 20px; border: none; cursor: not-allowed;">
                                    Out of Stock
                                </button>
                            </c:otherwise>
                        </c:choose>
                        
                        <a href="${pageContext.request.contextPath}/books" 
                           style="margin-left: 15px; color: #2196F3; text-decoration: none; padding: 10px 15px; border: 1px solid #2196F3;">
                            Back to Book List
                        </a>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
