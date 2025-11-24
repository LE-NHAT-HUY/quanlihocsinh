<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>YearSemester List</title></head>
<body>
<h2>Danh sách năm học – học kỳ</h2>
<a href="yearsemester/create">Thêm mới</a>
<table border="1">
<tr>
<th>ID</th>
<th>Semester</th>
<th>School Year</th>
<th>Active</th>
<th>Action</th>
</tr>
<c:forEach var="ys" items="${listyearsemester}">
<tr>
<td>${ys.yearSemesterID}</td>
<td>${ys.semesterName}</td>
<td>${ys.schoolYear}</td>
<td>${ys.isActive}</td>
<td>
<a href="yearsemester/edit?id=${ys.yearSemesterID}">Edit</a> |
<a href="yearsemester/delete?id=${ys.yearSemesterID}" onclick="return confirm('Xóa?')">Delete</a>
</td>
</tr>
</c:forEach>
</table>
</body>
</html>
