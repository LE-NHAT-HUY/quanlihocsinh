<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Create YearSemester</title></head>
<body>
<h2>Thêm mới năm học – học kỳ</h2>
<form action="create" method="post">
Semester: <input type="text" name="semesterName" required><br>
School Year: <input type="text" name="schoolYear" required><br>
Active: <input type="checkbox" name="isActive"><br>
<input type="submit" value="Thêm mới">
</form>
<a href="../yearsemester">Trở về</a>
</body>
</html>
