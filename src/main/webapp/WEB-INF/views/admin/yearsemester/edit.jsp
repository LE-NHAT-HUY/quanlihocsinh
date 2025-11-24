<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.quanlihocsinh.model.YearSemester" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa năm học/học kỳ</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">
</head>
<body>
<div class="container">
    <h2>Sửa năm học/học kỳ</h2>
    <%
        YearSemester ys = (YearSemester) request.getAttribute("yearsemester");
    %>
    <form method="post" action="<c:url value='/admin/yearsemester/edit'/>">
        <input type="hidden" name="yearSemesterID" value="<%= ys.getYearSemesterID() %>">
        <div class="form-group">
            <label>Học kỳ</label>
            <input type="text" name="semesterName" class="form-control" value="<%= ys.getSemesterName() %>" required>
        </div>
        <div class="form-group">
            <label>Năm học</label>
            <input type="text" name="schoolYear" class="form-control" value="<%= ys.getSchoolYear() %>" required>
        </div>
        <div class="form-check">
            <input type="checkbox" name="isActive" class="form-check-input" <%= ys.getIsActive() ? "checked" : "" %>>
            <label class="form-check-label">Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary mt-2">Cập nhật</button>
        <a href="<c:url value='/admin/yearsemester/list'/>" class="btn btn-secondary mt-2">Hủy</a>
    </form>
</div>
</body>
</html>
