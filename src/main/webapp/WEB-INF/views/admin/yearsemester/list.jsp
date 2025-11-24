<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.quanlihocsinh.model.YearSemester" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách năm học/học kỳ</title>
    <link rel="stylesheet" href="<c:url value='/css/bootstrap.min.css'/>">
</head>
<body>
<div class="container">
    <h2>Danh sách năm học/học kỳ</h2>
    <a href="<c:url value='/admin/yearsemester/add'/>" class="btn btn-success mb-2">Thêm mới</a>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>Học kỳ</th>
            <th>Năm học</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<YearSemester> list = (List<YearSemester>) request.getAttribute("yearsemesters");
            if (list != null) {
                for (YearSemester ys : list) {
        %>
        <tr>
            <td><%= ys.getYearSemesterID() %></td>
            <td><%= ys.getSemesterName() %></td>
            <td><%= ys.getSchoolYear() %></td>
            <td><%= ys.getIsActive() ? "Hoạt động" : "Không hoạt động" %></td>
            <td>
                <a href="<%= request.getContextPath() + "/admin/yearsemester/edit?id=" + ys.getYearSemesterID() %>" class="btn btn-primary btn-sm">Sửa</a>
                <a href="<%= request.getContextPath() + "/admin/yearsemester/delete?id=" + ys.getYearSemesterID() %>" 
                   class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
