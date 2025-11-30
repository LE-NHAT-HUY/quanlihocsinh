<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h2>Đăng ký tài khoản</h2>
<form method="post" action="register">
    <label>Username:</label>
    <input type="text" name="username" required><br>

    <label>Password:</label>
    <input type="password" name="password" required><br>

    <label>Full Name:</label>
    <input type="text" name="fullName"><br>

    <button type="submit">Đăng ký</button>
</form>

${error}
