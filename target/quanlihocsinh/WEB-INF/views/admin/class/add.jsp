<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm mới lớp học</h2>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">

                        <form action="${pageContext.request.contextPath}/admin/class?action=add" method="post">

                            <div class="mb-3">
                                <label for="className" class="form-label">Tên lớp</label>
                                <input type="text" class="form-control" id="className" name="className" required>
                            </div>

                            <div class="mb-3">
                                <label for="cohortID" class="form-label">Khóa học</label>
                                <select class="form-select" id="cohortID" name="cohortID" required>
                                    <option value="">-- Chọn khóa --</option>
                                    <c:forEach var="cohort" items="${cohorts}">
                                        <option value="${cohort.cohortID}">
                                            ${cohort.cohortName} (${cohort.startYear} - ${cohort.endYear})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="maxStudents" class="form-label">Sĩ số tối đa</label>
                                <input type="number" class="form-control" id="maxStudents" name="maxStudents" required>
                            </div>

                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="isActive" name="isActive" checked>
                                <label class="form-check-label" for="isActive">Hiển thị</label>
                            </div>

                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-plus-circle"></i> Thêm mới
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/class?action=list" class="btn btn-secondary">
                                Hủy
                            </a>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
