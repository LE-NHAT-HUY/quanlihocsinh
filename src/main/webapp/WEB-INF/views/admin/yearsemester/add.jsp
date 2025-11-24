<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm năm học/học kỳ</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">
</head>
<body>
<div class="container">
    <h2>Thêm năm học/học kỳ</h2>
    <form method="post" action="<c:url value='/admin/yearsemester/add'/>">
        <div class="form-group">
            <label>Học kỳ</label>
            <input type="text" name="semesterName" class="form-control" required>
        </div>
        <div class="form-group">
            <label>Năm học</label>
            <input type="text" name="schoolYear" class="form-control" required>
        </div>
        <div class="form-check">
            <input type="checkbox" name="isActive" class="form-check-input" checked>
            <label class="form-check-label">Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-success mt-2">Thêm mới</button>
        <a href="<c:url value='/admin/yearsemester/list'/>" class="btn btn-secondary mt-2">Hủy</a>
    </form>
</div>
</body>
</html>
