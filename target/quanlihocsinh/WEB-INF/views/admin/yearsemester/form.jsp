<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm/Sửa năm học - học kỳ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>
            <c:if test="${yearSemester != null}">Sửa năm học - học kỳ</c:if>
            <c:if test="${yearSemester == null}">Thêm năm học - học kỳ</c:if>
        </h1>
        <form action="year-semester" method="post">
            <c:if test="${yearSemester != null}">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="yearSemesterID" value="${yearSemester.yearSemesterID}">
            </c:if>
            <c:if test="${yearSemester == null}">
                <input type="hidden" name="action" value="insert">
            </c:if>
            <div class="mb-3">
                <label for="semesterName" class="form-label">Tên học kỳ</label>
                <input type="text" class="form-control" id="semesterName" name="semesterName" value="${yearSemester.semesterName}" required>
            </div>
            <div class="mb-3">
                <label for="schoolYear" class="form-label">Năm học</label>
                <input type="text" class="form-control" id="schoolYear" name="schoolYear" value="${yearSemester.schoolYear}" required>
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="isActive" name="isActive" ${yearSemester.active ? 'checked' : ''}>
                <label class="form-check-label" for="isActive">Đang hoạt động</label>
            </div>
            <button type="submit" class="btn btn-success">Lưu</button>
            <a href="year-semester" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</body>
</html>