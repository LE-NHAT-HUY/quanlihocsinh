<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<h2>Thêm Niên Khóa Mới</h2>

<form action="${pageContext.request.contextPath}/admin/cohort/add" method="post">
    <table>
        <tr>
            <td>Niên khóa:</td>
            <td><input type="number" name="cohortName" required /></td>
        </tr>
        <tr>
            <td>Năm vào:</td>
            <td><input type="number" name="startYear" required /></td>
        </tr>
        <tr>
            <td>Năm ra:</td>
            <td><input type="number" name="endYear" required /></td>
        </tr>
        <tr>
            <td>Hiển thị:</td>
            <td><input type="checkbox" name="isActive" checked /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Thêm mới" />
            </td>
        </tr>
    </table>
</form>

<a href="${pageContext.request.contextPath}/admin/cohort/list">Quay lại danh sách</a>
