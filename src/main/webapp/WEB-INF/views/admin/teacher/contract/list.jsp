<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <c:url var="addContractUrl" value="/admin/teacher-contract">
        <c:param name="action" value="add" />
        <c:if test="${selectedTeacherId > 0}">
            <c:param name="teacherID" value="${selectedTeacherId}" />
        </c:if>
    </c:url>
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <div>
            <h2 class="mb-0">Quản lý Hợp đồng giáo viên</h2>
            <p class="text-muted mb-0">
                <c:choose>
                    <c:when test="${not empty selectedTeacher}">Giáo viên: ${selectedTeacher.fullName}</c:when>
                    <c:otherwise>Danh sách toàn bộ hợp đồng</c:otherwise>
                </c:choose>
            </p>
        </div>
        <a href="${addContractUrl}" class="btn btn-primary">
            <i class="bi bi-plus-lg me-1"></i> Thêm hợp đồng
        </a>
    </div>

    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success">${sessionScope.flashSuccess}</div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger">${sessionScope.flashError}</div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <section class="section">
        <div class="row g-3 mb-3">
            <div class="col-lg-6">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Lọc theo giáo viên</h5>
                        <form method="get" action="${pageContext.request.contextPath}/admin/teacher-contract" class="row g-2 align-items-end">
                            <div class="col-md-8">
                                <label class="form-label">Giáo viên</label>
                                <select name="teacherID" class="form-select">
                                    <option value="0">Tất cả giáo viên</option>
                                    <c:forEach var="teacher" items="${teachers}">
                                        <option value="${teacher.id}" ${teacher.id == selectedTeacherId ? 'selected' : ''}>${teacher.fullName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <button type="submit" class="btn btn-outline-primary w-100">Áp dụng</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card h-100">
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h5 class="card-title">Thao tác nhanh</h5>
                        <p class="text-muted mb-3">Chọn một giáo viên để thêm mới hợp đồng liên quan.</p>
                        <a class="btn btn-success" href="${addContractUrl}">Thêm mới theo giáo viên</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable align-middle">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th>Giáo viên</th>
                                    <th>Số HĐ</th>
                                    <th>Loại HĐ</th>
                                    <th class="text-center">Ngày ký</th>
                                    <th class="text-center">Ngày hiệu lực</th>
                                    <th class="text-center">Ngày hết hạn</th>
                                    <th class="text-center">Hệ số lương</th>
                                    <th class="text-center">Lương cơ bản</th>
                                    <th>Trạng thái</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0" />
                                <c:forEach var="contract" items="${contracts}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td>${contract.teacherName}</td>
                                        <td>${contract.contractNumber}</td>
                                        <td>${contract.contractType}</td>
                                        <td class="text-center">${contract.signDate}</td>
                                        <td class="text-center">${contract.startDate}</td>
                                        <td class="text-center">${contract.endDate}</td>
                                        <td class="text-center">${contract.salaryCoefficient}</td>
                                        <td class="text-center">${contract.baseSalary}</td>
                                        <td>${contract.contractStatus}</td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/teacher-contract?action=edit&id=${contract.contractID}<c:if test='${selectedTeacherId > 0}'>&teacherID=${selectedTeacherId}</c:if>" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher-contract?action=delete&id=${contract.contractID}<c:if test='${selectedTeacherId > 0}'>&teacherID=${selectedTeacherId}</c:if>" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa hợp đồng này không?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        pageLength: 10,
        lengthMenu: [5, 10, 25, 50, 100],
        order: [],
        language: {
            emptyTable: 'Chưa có hợp đồng nào'
        },
        columnDefs: [{ orderable: false, targets: [10] }]
    });
});
</script>
