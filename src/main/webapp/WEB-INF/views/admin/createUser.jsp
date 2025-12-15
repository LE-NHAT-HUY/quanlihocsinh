<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tạo tài khoản</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap + Font Awesome -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #f8fafc, #e0f2fe);
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }

        .wrapper {
            display: flex;
            max-width: 950px;
            width: 100%;
            background: #ffffff;
            border-radius: 24px;
            box-shadow: 0 40px 90px rgba(0,0,0,.12);
            overflow: hidden;
            animation: fadeUp .8s ease;
        }

        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* CỘT TRÁI */
        .left {
            flex: 1;
            background: linear-gradient(135deg, #2563eb, #22c55e);
            color: #fff;
            padding: 60px 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
        }

        .left i {
            font-size: 64px;
            margin-bottom: 20px;
            opacity: .95;
        }

        .left h2 {
            font-weight: 700;
            margin-bottom: 15px;
        }

        .left p {
            opacity: .9;
            line-height: 1.6;
        }

        /* CỘT PHẢI */
        .right {
            flex: 1;
            padding: 50px 45px;
        }

        .right h3 {
            font-weight: 700;
            margin-bottom: 25px;
            text-align: center;
        }

        .form-label {
            font-weight: 600;
            color: #334155;
        }

        .form-control, .form-select {
            border-radius: 14px;
            padding: 14px;
            background: #f1f5f9;
            border: 2px solid transparent;
            transition: .3s;
        }

        .form-control:focus, .form-select:focus {
            background: #ffffff;
            border-color: #2563eb;
            box-shadow: none;
        }

        .btn-submit {
            background: linear-gradient(135deg, #2563eb, #22c55e);
            border: none;
            border-radius: 16px;
            padding: 14px;
            font-weight: 600;
            transition: .3s;
        }

        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 18px 40px rgba(37,99,235,.35);
        }

        .error-text {
            background: #fee2e2;
            border-left: 4px solid #ef4444;
            color: #991b1b;
            padding: 12px;
            border-radius: 12px;
            font-size: 14px;
            margin-bottom: 15px;
        }

        @media (max-width: 768px) {
            .left { display: none; }
        }
    </style>

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

<div class="wrapper">

    <!-- BÊN TRÁI -->
    <div class="left">
        <i class="fas fa-user-shield"></i>
        <h2>Quản trị hệ thống</h2>
        <p>
            Tạo tài khoản đăng nhập <br>
            và liên kết giáo viên / học sinh
        </p>
    </div>

    <!-- BÊN PHẢI -->
    <div class="right">
        <h3>Tạo tài khoản</h3>

        <c:if test="${not empty error}">
            <div class="error-text">
                <i class="fas fa-circle-exclamation"></i>
                ${error}
            </div>
        </c:if>

        <form method="post"
              action="${pageContext.request.contextPath}/admin/createUser">

            <div class="mb-3">
                <label class="form-label">Tên đăng nhập</label>
                <input name="username" class="form-control"
                       placeholder="Nhập username" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password"
                       class="form-control"
                       placeholder="Nhập mật khẩu" required />
            </div>

            <div class="mb-3">
                <label class="form-label">Vai trò</label>
                <select name="roleId" id="roleId"
                        class="form-select"
                        onchange="onRoleChange()">
                    <option value="1">Admin</option>
                    <option value="2">Teacher</option>
                    <option value="3">Student</option>
                </select>
            </div>

            <div class="mb-4" id="profileDiv" style="display:none;">
                <label class="form-label">Liên kết profile</label>
                <select name="personId" id="personId"
                        class="form-select">
                    <option value="">-- Chọn --</option>
                    <c:forEach var="p" items="${persons}">
                        <option value="${p.personId}">
                            ${p.fullname} (${p.personType})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit"
                    class="btn btn-submit w-100 text-white">
                <i class="fas fa-user-plus"></i>
                Tạo tài khoản
            </button>
        </form>
    </div>

</div>

<script>
    onRoleChange();
</script>

</body>
</html>
