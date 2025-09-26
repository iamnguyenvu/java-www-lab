<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: auto;
            padding: 20px;
        }
        header {
            background: url("images/header-bg.png");
            background-size: cover;
            background-position: center;
            color: white;
            padding: 15px 30px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
        }
        header span {
            font-size: 24px;
            font-weight: bold;
            padding: 10px 20px;
            background-color: rgba(76, 175, 80, 0.8);
            border-radius: 8px;
        }
        nav {
            margin: 0;
        }
        nav a {
            color: white;
            margin: 0 8px;
            text-decoration: none;
            padding: 10px 16px;
            background-color: rgba(76, 175, 80, 0.8);
            border-radius: 6px;
            transition: all 0.3s ease;
        }
        aside {
            width: 30%;
            float: left;
            padding: 15px;
            background-color: #f4f4f4;
            border-radius: 8px;
            box-sizing: border-box;
        }
        .search-section {
            margin-bottom: 20px;
            padding: 15px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .search-section h2, .search-section h3 {
            margin-top: 0;
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 8px;
        }
        .search-form input[type="text"], .price-form input[type="number"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .price-range {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .price-range input {
            flex: 1;
        }
        .price-range span {
            font-weight: bold;
        }
        .search-form button, .price-form button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 4px;
            cursor: pointer;
            margin: 5px 5px 5px 0;
            transition: background-color 0.3s ease;
        }
        .search-form button:hover, .price-form button:hover {
            background-color: #45a049;
        }
        .clear-filter {
            background-color: #f44336 !important;
        }
        .clear-filter:hover {
            background-color: #da190b !important;
        }
        main {
            width: 70%;
            float: right;
            padding: 15px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            box-sizing: border-box;
        }
        div {
            overflow: hidden;
        }
    </style>
</head>
<body>
<header>
    <div class="header-container">
        <span>IUH BOOKSTORE</span>
        <nav>
            <a href="${pageContext.request.contextPath}/books">Home</a>
            <a href="${pageContext.request.contextPath}/cart">Cart</a>
            <a href="#">Services</a>
            <a href="#">Product</a>
            <a href="#">Contact</a>
        </nav>
    </div>

</header>
<div>
    <aside>
        <div class="search-section">
            <h2>ABOUT US</h2>
            <p>About us...</p>
        </div>
        <div class="search-section">
            <h2>SEARCH BOOK</h2>
            <form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
                <input type="text" name="query" placeholder="Enter book title or author..." value="${param.query}"/>
                <input type="hidden" name="minPrice" value="${param.minPrice}">
                <input type="hidden" name="maxPrice" value="${param.maxPrice}">
                <button type="submit">Search</button>
            </form>
        </div>
        <div class="search-section">
            <h3>FILTER BY PRICE</h3>
            <form action="${pageContext.request.contextPath}/search" method="get" class="price-form">
                <div class="price-range">
                    <input type="number" name="minPrice" step="10000" min="0" value="${param.minPrice}"/>
                    <span> - </span>
                    <input type="number" name="maxPrice" step="10000" max="9999999" value="${param.maxPrice}"/>
                </div>
                <input type="hidden" name="query" value="${param.query}">
                <button type="submit">Filter</button>
                <button type="button" onclick="window.location.href='${pageContext.request.contextPath}/books'"
                        class="clear-filter">Clear Filter
                </button>
            </form>
        </div>
    </aside>
    <main>
<%--        <nav style="padding: 10px 0; border-bottom: 1px solid #eee; margin-bottom: 20px;">--%>
<%--            <a href="${pageContext.request.contextPath}/books" style="color: #007bff; text-decoration: none;">Home</a>--%>
<%--            <c:if test="${pageTitle != 'Book List'}">--%>
<%--                <span style="margin: 0 8px; color: #666;">/</span>--%>
<%--                <span style="color: #666;">${pageTitle}</span>--%>
<%--            </c:if>--%>
<%--        </nav>--%>
        
        <jsp:include page="${contentPage}"/>
    </main>
</div>
</body>
</html>