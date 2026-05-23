<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa tài khoản</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
</head>
<body>
<div class="container py-4" style="max-width: 760px;">
    <h3 class="mb-4">Chỉnh sửa tài khoản</h3>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/editUser">
        <input type="hidden" name="id" value="${user.userID}" />

        <div class="mb-3">
            <label class="form-label">Tên đăng nhập</label>
            <input type="text" name="username" class="form-control" value="${user.username}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Mật khẩu mới</label>
            <input type="password" name="password" class="form-control" placeholder="Để trống nếu không đổi mật khẩu" />
        </div>

        <div class="mb-3">
            <label class="form-label">Họ và tên</label>
            <input type="text" name="fullname" class="form-control" value="${user.profile.fullName}" required />
        </div>

        <div class="mb-3">
            <label class="form-label">Vai trò hiện tại</label>
            <input type="text" class="form-control" value="${user.roleId == 1 ? 'Admin' : (user.roleId == 2 ? 'Giáo viên' : (user.roleId == 3 ? 'Học sinh' : 'Khách'))}" readonly />
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Hủy</a>
        </div>
    </form>
</div>
</body>
</html>