<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid p-0">
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4 p-lg-5">
            <div class="d-flex justify-content-between align-items-start gap-3 flex-wrap">
                <div>
                    <div class="text-uppercase text-primary fw-bold small mb-2">Hộp thư thông báo</div>
                    <h3 class="fw-bold mb-2">Thông báo của tôi</h3>
                    <p class="text-muted mb-0">Thông báo dành cho giáo viên và các lớp bạn phụ trách.</p>
                </div>
                <div class="d-flex gap-2 align-items-start flex-wrap">
                    <form class="d-flex gap-2" method="get" action="${pageContext.request.contextPath}/teacher/notifications">
                        <div class="input-group" style="min-width: 320px;">
                            <span class="input-group-text bg-white"><i class="bi bi-search"></i></span>
                            <input type="text" class="form-control" name="q" value="${searchKeyword}" placeholder="Tìm theo tiêu đề, người gửi, đơn vị...">
                        </div>
                        <button type="submit" class="btn btn-primary">Tìm</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/teacher/notifications/add" class="btn btn-outline-primary">
                        <i class="bi bi-pencil-square me-1"></i> Soạn thông báo
                    </a>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${not empty sessionScope.flashSuccess}">
        <div class="alert alert-success">${sessionScope.flashSuccess}</div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.flashError}">
        <div class="alert alert-danger">${sessionScope.flashError}</div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <c:choose>
        <c:when test="${empty notifications}">
            <div class="card shadow-sm border-0">
                <div class="card-body py-5 text-center text-muted">
                    <i class="bi bi-envelope-open fs-1 d-block mb-3"></i>
                    <h5 class="fw-bold">Chưa có thông báo nào</h5>
                    <p class="mb-0">Khi nhà trường hoặc giáo viên khác gửi thông báo, nội dung sẽ xuất hiện ở đây.</p>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="d-grid gap-3">
                <c:forEach var="item" items="${notifications}">
                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-start gap-3 flex-wrap">
                                <div class="flex-grow-1">
                                    <div class="d-flex align-items-center gap-2 mb-2 flex-wrap">
                                        <span class="badge ${item.targetType == 'ALL' ? 'bg-danger' : 'bg-primary'}">
                                            <c:choose>
                                                <c:when test="${item.targetType == 'ALL'}">Toàn trường</c:when>
                                                <c:when test="${item.targetType == 'ALL_TEACHER'}">Toàn bộ giáo viên</c:when>
                                                <c:when test="${item.targetType == 'ALL_STUDENT'}">Toàn bộ học sinh</c:when>
                                                <c:otherwise>Theo lớp</c:otherwise>
                                            </c:choose>
                                        </span>
                                        <a href="${pageContext.request.contextPath}/teacher/notifications/detail?id=${item.notificationID}" class="text-primary fw-bold text-decoration-none fs-5">
                                            ${item.title}
                                        </a>
                                    </div>

                                    <div class="text-muted small mb-3" style="white-space: pre-wrap;">
                                        ${item.content}
                                    </div>

                                    <div class="d-flex flex-wrap gap-3 text-muted small border-top pt-3">
                                        <span><i class="bi bi-calendar3 me-1 text-primary"></i>
                                            <c:if test="${not empty item.createdDate}">
                                                <fmt:formatDate value="${item.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </c:if>
                                        </span>
                                        <span><i class="bi bi-person me-1 text-primary"></i>${item.senderFullName != null ? item.senderFullName : item.senderUsername}</span>
                                        <span><i class="bi bi-info-circle me-1 text-primary"></i>${not empty item.senderDepartment ? item.senderDepartment : 'Chưa cập nhật'}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
