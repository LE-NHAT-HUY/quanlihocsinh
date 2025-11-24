<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Chỉnh sửa Năm học/Học kỳ</h2>
        <a href="${pageContext.request.contextPath}/admin/YearSemester" class="btn btn-secondary mb-2">
            <i class="bi bi-arrow-left-circle"></i> Quay lại
        </a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-6">
                <div class="card p-3">
                    <form method="post" action="${pageContext.request.contextPath}/admin/YearSemester">
                        <input type="hidden" name="action" value="edit" />
                        <input type="hidden" name="id" value="${yearSemester.yearSemesterID}" />
                        <div class="mb-3">
                            <label for="semesterName" class="form-label">Tên Học kỳ</label>
                            <input type="text" class="form-control" name="semesterName" id="semesterName"
                                   value="${yearSemester.semesterName}" required />
                        </div>
                        <div class="mb-3">
                            <label for="schoolYear" class="form-label">Năm học</label>
                            <input type="text" class="form-control" name="schoolYear" id="schoolYear"
                                   value="${yearSemester.schoolYear}" required />
                        </div>
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" name="isActive" id="isActive"
                                   <c:if test="${yearSemester.isActive}">checked</c:if> />
                            <label class="form-check-label" for="isActive">Hiển thị</label>
                        </div>
                        <button type="submit" class="btn btn-success">Cập nhật</button>
                        <a href="${pageContext.request.contextPath}/admin/YearSemester" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>
