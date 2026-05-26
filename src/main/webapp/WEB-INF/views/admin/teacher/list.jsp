<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0">Danh sách Giáo viên</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher?action=add" class="btn btn-primary">
            <i class="bi bi-plus-lg me-1"></i> Thêm mới
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

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <table class="table table-borderless datatable align-middle">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Mã GV</th>
                                    <th class="text-center">Họ tên</th>
                                    <th class="text-center">Giới tính</th>
                                    <th class="text-center">Điện thoại</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Hoạt động</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="teacher" items="${teachers}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${teacher.teacherID}</td>
                                        <td>${teacher.fullName}</td>
                                        <td class="text-center">${teacher.gender}</td>
                                        <td class="text-center">${teacher.numberPhone}</td>
                                        <td class="text-center">${teacher.email}</td>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/admin/teacher" method="get" class="d-inline">
                                                <input type="hidden" name="action" value="toggleStatus"/>
                                                <input type="hidden" name="id" value="${teacher.id}" />
                                                <input type="checkbox" name="isActive" ${teacher.isActive ? 'checked' : ''} onchange="this.form.submit()" />
                                            </form>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=edit&id=${teacher.id}" class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher-degree?teacherID=${teacher.id}" class="btn btn-warning btn-sm text-white">
                                                <i class="bi bi-award"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher-contract?teacherID=${teacher.id}" class="btn btn-info btn-sm text-white">
                                                <i class="bi bi-file-earmark-text"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/teacher?action=delete&id=${teacher.id}" class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc muốn xóa giáo viên này không?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty teachers}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4 text-muted">Chưa có giáo viên nào</td>
                                    </tr>
                                </c:if>
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
        columnDefs: [{ orderable: false, targets: [6, 7] }]
    });
});
</script>
