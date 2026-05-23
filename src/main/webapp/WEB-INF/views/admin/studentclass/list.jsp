<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách học sinh theo lớp</h2>
    </div>

    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success">${sessionScope.flashSuccess}</div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger">${sessionScope.flashError}</div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <section class="section dashboard">
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/admin/studentclass/list" class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <select name="classID" class="form-select" onchange="this.form.submit()">
                            <option value="">-- Tất cả lớp --</option>
                            <c:forEach var="cls" items="${classes}">
                                <option value="${cls.classID}" ${classID == cls.classID ? 'selected' : ''}>
                                    ${cls.gradeID}${cls.className} (${cls.currentStudents}/${cls.maxStudents})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search me-1"></i> Lọc
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card recent-sales overflow-auto">
            <div class="card-header bg-white border-bottom">
                <div class="d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">
                        <i class="bi bi-people me-2 text-primary"></i>
                        Danh sách học sinh
                        <c:if test="${classID > 0}">
                            <c:forEach var="cls" items="${classes}">
                                <c:if test="${cls.classID == classID}">
                                    <span class="badge bg-info ms-2">${cls.gradeID}${cls.className}</span>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </h5>
                    <c:if test="${classID > 0}">
                        <a href="${pageContext.request.contextPath}/admin/studentclass/add?classID=${classID}&yearSemesterID=${yearSemesterID}"
                           class="btn btn-primary btn-sm">
                            <i class="bi bi-person-plus me-1"></i> Thêm học sinh
                        </a>
                    </c:if>
                </div>
            </div>

            <div class="card-body p-0">
                <style>
                    /* 1. Loại bỏ màu nền và đường kẻ ngang cho bảng */
                    .table-clean, .table-clean tr, .table-clean td, .table-clean th {
                        border: none !important;
                        background-color: transparent !important;
                    }
                    /* Tùy chỉnh khoảng cách hàng sau khi bỏ border */
                    .table-clean tbody tr td {
                        padding: 12px 8px;
                    }
                </style>

                <div class="table-responsive">
                    <table class="table table-borderless table-clean mb-0 datatable">
                        <thead>
                            <tr>
                                <th class="text-center" style="width: 60px;">STT</th>
                                <th>Mã học sinh</th>
                                <th>Họ và tên</th>
                                <th>Giới tính</th>
                                <th>Ngày sinh</th>
                                <th class="text-center">Trạng thái</th>
                                <th class="text-center" style="width: 120px;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sc" items="${studentsInClass}" varStatus="loop">
                                <tr>
                                    <td class="text-center">${loop.index + 1}</td>
                                    <td><strong>${sc.studentID}</strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty sc.student}">
                                                ${sc.student.fullName}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">N/A</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${not empty sc.student}">
                                            ${sc.student.gender}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty sc.student and not empty sc.student.birth}">
                                            <fmt:formatDate value="${sc.student.birth}" pattern="dd/MM/yyyy"/>
                                        </c:if>
                                    </td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${sc.active}">
                                                <span class="">Đang học</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Không hoạt động</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                         <a href="${pageContext.request.contextPath}/admin/studentclass/delete?studentClassID=${sc.studentClassID}&classID=${classID}&yearSemesterID=${yearSemesterID}" 
                                            class="btn btn-danger btn-sm" title="Xóa học sinh khỏi lớp"
                                            onclick="return confirm('Bạn có chắc muốn xóa học sinh này khỏi lớp?')">
                                            <i class="bi bi-trash"></i>
                                         </a>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty studentsInClass}">
                                <tr>
                                    <td colspan="7" class="text-center py-4 text-muted">
                                        <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                                        <c:choose>
                                            <c:when test="${classID == null or classID == 0}">
                                                Vui lòng chọn lớp để xem danh sách học sinh
                                            </c:when>
                                            <c:otherwise>
                                                Chưa có học sinh nào trong lớp này
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<c:if test="${not empty studentsInClass}">
<script>
$(document).ready(function () {
    $('.datatable').DataTable({
        "pageLength": 10,
        "lengthMenu": [5,10,25,50,100],
        "order": [],
        "columnDefs": [
            { "orderable": false, "targets": [6] }
        ]
    });
});
</script>
</c:if>