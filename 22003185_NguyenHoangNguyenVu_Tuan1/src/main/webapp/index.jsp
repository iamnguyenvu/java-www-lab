<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/processFormUpload" method="post" enctype="multipart/form-data">
    <h1>HTML, Form Example with File Upload</h1>

    <div>
        <label for="name">Name: </label>
        <input type="text" name="name" id="name">
    </div>

    <div>
        <label for="password">Password: </label>
        <input type="password" name="password" id="password">
    </div>

    <div>
        <label>Gender: </label>
        <input type="radio" name="gender" value="Male" id="male"><label for="male">Male</label>
        <input type="radio" name="gender" value="Female" id="female"><label for="female">Female</label>
    </div>

    <div>
        <label>Hobbies: </label>
        <input type="checkbox" name="hobbies" value="reading" id="reading"><label for="reading">Reading</label>
        <input type="checkbox" name="hobbies" value="sports" id="sports"><label for="sports">Sports</label>
        <input type="checkbox" name="hobbies" value="music" id="music"><label for="music">Music</label>
    </div>

    <div>
        <label for="country">Country: </label>
        <select name="country" id="country">
            <option value="vietnam">Vietnam</option>
            <option value="us">US</option>
            <option value="uk">UK</option>
            <option value="japan">Japan</option>
        </select>
    </div>

    <div>
        <label for="birthDate">Birth Date: </label>
        <input type="date" name="birthDate" id="birthDate">
    </div>

    <div>
        <label for="profilePicture">Profile Picture: </label>
        <input type="file" name="profilePicture" id="profilePicture">
    </div>

    <button type="submit">Submit</button>
</form>
</body>
</html>