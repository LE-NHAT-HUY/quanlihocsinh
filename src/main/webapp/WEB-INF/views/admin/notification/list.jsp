<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex justify-content-between align-items-center">
        <div>
            <h2>Danh sách thông báo</h2>
            <p class="text-muted mb-0">Toàn bộ lịch sử thông báo đã gửi trong hệ thống</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/notifications/add" class="btn btn-primary">
            <i class="bi bi-pencil-square me-1"></i> Soạn thông báo
        </a>
    </div>

    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success mt-3">${sessionScope.flashSuccess}</div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger mt-3">${sessionScope.flashError}</div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <section class="section dashboard">
        <div class="card recent-sales overflow-auto">
            <div class="card-body mt-4">
                <table class="table table-borderless datatable">
                    <thead>
                        <tr>
                            <th class="text-center">STT</th>
                            <th class="text-center">Tiêu đề</th>
                            <th class="text-center">Nội dung</th>
                            <th class="text-center">Người gửi</th>
                            <th class="text-center">Gửi đến</th>
                            <th class="text-center">Ngày tạo</th>
                            <th class="text-center">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="stt" value="0" />
                        <c:forEach var="item" items="${notifications}">
                            <c:set var="stt" value="${stt + 1}" />
                            <tr>
                                <td class="text-center">${stt}</td>
                                <td class="fw-semibold">
                                    <a href="${pageContext.request.contextPath}/admin/notifications/detail?id=${item.notificationID}" class="text-primary text-decoration-none fw-semibold">
                                        ${item.title}
                                    </a>
                                </td>
                                <td style="max-width: 420px;">
                                    <div class="text-truncate" title="${item.content}">${item.content}</div>
                                </td>
                                <td class="text-center">${item.senderFullName != null ? item.senderFullName : item.senderUsername}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${item.targetType == 'ALL'}">Toàn trường</c:when>
                                        <c:when test="${item.targetType == 'ALL_TEACHER'}">Toàn bộ giáo viên</c:when>
                                        <c:when test="${item.targetType == 'ALL_STUDENT'}">Toàn bộ học sinh</c:when>
                                        <c:otherwise>
                                            Lớp ${item.targetClassName != null ? item.targetClassName : item.targetClassID}
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <c:if test="${not empty item.createdDate}">
                                        <fmt:formatDate value="${item.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                                    </c:if>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/admin/notifications/hide" method="post" class="d-inline me-1">
                                        <input type="hidden" name="id" value="${item.notificationID}" />
                                        <button type="submit" class="btn btn-sm btn-outline-secondary"
                                                onclick="return confirm('Bạn có chắc chắn muốn ẩn thông báo này?');">
                                            <i class="bi bi-eye-slash"></i> Ẩn
                                        </button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/admin/notifications/delete" method="post" class="d-inline"
                                          onsubmit="return confirm('Bạn có chắc chắn muốn xóa vĩnh viễn thông báo này?');">
                                        <input type="hidden" name="id" value="${item.notificationID}" />
                                        <button type="submit" class="btn btn-sm btn-outline-danger">
                                            <i class="bi bi-trash"></i> Xóa
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
        columnDefs: [{ orderable: false, targets: [6] }]
    });
});
</script>