<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tạo tài khoản</title>

    <script>
        function onRoleChange() {
            var role = document.getElementById("roleId").value;
            var profileDiv = document.getElementById("profileDiv");
            var personId = document.getElementById("personId");

            if (role === "1") {
                profileDiv.style.display = "none";
                personId.removeAttribute("required");
                personId.value = "";
            } else {
                profileDiv.style.display = "block";
                personId.setAttribute("required", "required");
            }
        }
    </script>
</head>

<body>
<h2>Tạo tài khoản và liên kết profile</h2>

<c:if test="${not empty error}">
    <div style="color:red">${error}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/admin/createUser">

    <label>Username</label>
    <input name="username" required /><br/>

    <label>Password</label>
    <input name="password" type="password" required /><br/>

    <label>Role</label>
    <select name="roleId" id="roleId" onchange="onRoleChange()">
        <option value="1">Admin</option>
        <option value="2">Teacher</option>
        <option value="3">Student</option>
    </select><br/>

    <div id="profileDiv" style="display:none; margin-top:10px;">
        <label>Chọn profile</label>
        <select name="personId" id="personId">
            <option value="">-- Chọn --</option>
            <c:forEach var="p" items="${persons}">
                <option value="${p.personId}">
                    ${p.fullname} (${p.personType})
                </option>
            </c:forEach>
        </select>
    </div>

    <br/>
    <button type="submit">Tạo</button>
</form>

<script>
    onRoleChange();
</script>

</body>
</html>
