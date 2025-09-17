<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #4CAF50;
            color: white;
            padding: 10px 0;
            text-align: center;
        }
        nav a {
            color: white;
            margin: 0 15px;
            text-decoration: none;
        }
        aside {
            width: 30%;
            float: left;
            padding: 15px;
            background-color: #f4f4f4;
        }
        main {
            width: 75%;
            float: right;
            padding: 15px;
        }
        div {
            overflow: hidden;
        }
    </style>
</head>
<body>
    <header>
        <span>IUH BOOKSTORE</span>
        <nav>
            <a href="book-list.jsp">Home</a>
            <a href="#">Examples</a>
            <a href="#">Services</a>
            <a href="#">Product</a>
            <a href="#">Contact</a>
        </nav>
    </header>
    <div>
        <aside>
            <div>
                <h2>ABOUT US</h2>
            </div>
            <div>
                <h2>SEARCH SITE</h2>
                <form action="search" method="get">
                    <input type="text" name="query" placeholder="">
                </form>
            </div>
        </aside>
        <main>
            <jsp:include page="${contentPage}" />
        </main>
    </div>
</body>
</html>