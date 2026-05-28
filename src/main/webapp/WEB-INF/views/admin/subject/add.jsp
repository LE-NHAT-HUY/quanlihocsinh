<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0">Thêm môn học mới</h2>
        <a href="${pageContext.request.contextPath}/admin/subject?action=list" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách
        </a>
    </div>

    <section class="section dashboard mt-3">
        <div class="card">
            <div class="card-body pt-4">
                <form action="${pageContext.request.contextPath}/admin/subject?action=add" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="subjectID" class="form-label">Mã môn</label>
                            <input type="text" name="subjectID" class="form-control" id="subjectID" placeholder="Tự sinh hoặc nhập theo quy ước">
                        </div>
                        <div class="col-md-6">
                            <label for="subjectName" class="form-label">Tên môn học</label>
                            <input type="text" name="subjectName" class="form-control" id="subjectName" required>
                        </div>

                        <div class="col-md-6">
                            <label for="numberOfLesson" class="form-label">Số tiết mặc định</label>
                            <input type="number" name="numberOfLesson" class="form-control" id="numberOfLesson" min="0">
                        </div>

                        <div class="col-md-6">
                            <label for="semester" class="form-label">Học kỳ</label>
                            <select name="semester" class="form-select" id="semester">
                                <option value="">-- Chọn học kỳ --</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">Cả năm</option>
                            </select>
                        </div>

                        <div class="col-md-6">
                            <label for="departmentID" class="form-label">Phòng ban / Tổ bộ môn</label>
                            <select name="departmentID" class="form-select" id="departmentID">
                                <option value="">-- Chọn phòng ban --</option>
                                <c:forEach var="department" items="${departments}">
                                    <option value="${department.departmentID}">${department.departmentName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-md-6 d-flex align-items-end">
                            <div class="form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" id="isActive" checked>
                                <label class="form-check-label" for="isActive">Hoạt động</label>
                            </div>
                        </div>

                        <div class="col-12 d-flex justify-content-end gap-2 pt-2">
                            <a href="${pageContext.request.contextPath}/admin/subject?action=list" class="btn btn-outline-secondary">Hủy</a>
                            <button type="submit" class="btn btn-success">Thêm mới</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
</main>
