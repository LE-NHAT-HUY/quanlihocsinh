<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<h2>Chỉnh sửa Niên khóa</h2>

<form action="${pageContext.request.contextPath}/admin/cohort/edit" method="post">
    <!-- Hidden field chứa ID -->
    <input type="hidden" name="cohortID" value="${cohort.cohortID}" />

    <div>
        <label for="cohortName">Mã khóa:</label>
        <input type="number" id="cohortName" name="cohortName" value="${cohort.cohortName}" required />
    </div>

    <div>
        <label for="startYear">Năm vào:</label>
        <input type="number" id="startYear" name="startYear" value="${cohort.startYear}" required />
    </div>

    <div>
        <label for="endYear">Năm ra:</label>
        <input type="number" id="endYear" name="endYear" value="${cohort.endYear}" required />
    </div>

    <div>
        <label for="isActive">Hiển thị:</label>
        <input type="checkbox" id="isActive" name="isActive"
            <c:if test="${cohort.isActive}">checked</c:if> />
    </div>

    <div>
        <button type="submit">Cập nhật</button>
        <a href="${pageContext.request.contextPath}/admin/cohort/list">Hủy</a>
    </div>
</form>
