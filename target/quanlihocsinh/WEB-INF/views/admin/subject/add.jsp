<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm môn học mới</h2>
        <a href="${pageContext.request.contextPath}/admin/subject?action=list" class="btn btn-secondary">Quay lại danh sách</a>
    </div>

    <section class="section dashboard mt-3">
        <form action="${pageContext.request.contextPath}/admin/subject?action=add" method="post">
            <div class="mb-3">
                <label for="subjectName" class="form-label">Tên môn học</label>
                <input type="text" name="subjectName" class="form-control" id="subjectName" required>
            </div>
            <div class="mb-3">
                <label for="numberOfLesson" class="form-label">Số tiết</label>
                <input type="number" name="numberOfLesson" class="form-control" id="numberOfLesson">
            </div>
            <div class="mb-3">
                <label for="semester" class="form-label">Học kỳ</label>
                <input type="text" name="semester" class="form-control" id="semester">
            </div>
            <div class="mb-3">
                <label for="departmentID" class="form-label">Phòng ban</label>
                <input type="number" name="departmentID" class="form-control" id="departmentID">
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" name="isActive" class="form-check-input" id="isActive" checked>
                <label class="form-check-label" for="isActive">Hoạt động</label>
            </div>
            <button type="submit" class="btn btn-success">Thêm mới</button>
        </form>
    </section>
</main>
