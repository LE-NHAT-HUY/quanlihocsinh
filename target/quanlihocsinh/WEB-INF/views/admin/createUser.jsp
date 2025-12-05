<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><title>Tạo tài khoản</title></head>
<body>
<h2>Tạo tài khoản và liên kết profile</h2>
<c:if test="${not empty error}">
  <div style="color:red">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/admin/createUser">
    <label>Username</label><input name="username" required /><br/>
    <label>Password</label><input name="password" type="password" required /><br/>
    <label>Role</label>
    <select name="roleId">
        <option value="1">Admin</option>
        <option value="2">Teacher</option>
        <option value="3">Student</option>
    </select><br/>

    <label>Chọn profile</label>
    <select name="personId" required>
        <option value="">-- Chọn --</option>
        <c:forEach var="p" items="${persons}">
            <option value="${p.personId}">${p.fullname} (${p.personType})</option>
        </c:forEach>
    </select><br/>

    <button type="submit">Tạo</button>
</form>
</body>
</html>
