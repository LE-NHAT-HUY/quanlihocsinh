<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">
    <div class="pagetitle">
        <h2>Sửa thông tin môn học</h2>
        <a href="list" class="btn btn-secondary">Quay lại danh sách</a>
    </div>

    <form action="edit" method="post" class="mt-3">
        <input type="hidden" name="subjectID" value="${subject.subjectID}">
        <div class="mb-3">
            <label for="subjectName" class="form-label">Tên môn học</label>
            <input type="text" name="subjectName" class="form-control" id="subjectName"
                   value="${subject.subjectName}" required>
        </div>
        <div class="mb-3">
            <label for="numberOfLesson" class="form-label">Số tiết</label>
            <input type="number" name="numberOfLesson" class="form-control" id="numberOfLesson"
                   value="${subject.numberOfLesson}">
        </div>
        <div class="mb-3">
            <label for="semester" class="form-label">Học kỳ</label>
            <input type="text" name="semester" class="form-control" id="semester"
                   value="${subject.semester}">
        </div>
        <div class="mb-3">
            <label for="departmentID" class="form-label">Phòng ban</label>
            <input type="number" name="departmentID" class="form-control" id="departmentID"
                   value="${subject.departmentID}">
        </div>
        <div class="mb-3 form-check">
            <input type="checkbox" name="isActive" class="form-check-input" id="isActive"
                   <c:if test="${subject.isActive}">checked</c:if>>
            <label class="form-check-label" for="isActive">Hoạt động</label>
        </div>
        <button type="submit" class="btn btn-primary">Cập nhật</button>
    </form>
</main>
