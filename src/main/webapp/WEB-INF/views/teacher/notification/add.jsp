<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid p-0">
    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #0f766e 0%, #2563eb 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-1"><i class="bi bi-pencil-square me-2"></i>Soạn thông báo cho lớp</h2>
                <p class="mb-0 opacity-75">Giáo viên chỉ được chọn các lớp đang giảng dạy.</p>
            </div>
            <div class="col-md-4 text-end">
                <a href="${pageContext.request.contextPath}/teacher/notifications" class="btn btn-light btn-sm fw-semibold">
                    <i class="bi bi-arrow-left me-1"></i> Quay lại
                </a>
            </div>
        </div>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="card shadow-sm">
        <div class="card-body pt-4">
            <form action="${pageContext.request.contextPath}/teacher/notifications/add" method="post">
                <div class="row g-3">
                    <div class="col-12">
                        <label class="form-label fw-semibold">Tiêu đề</label>
                        <input type="text" class="form-control" name="title" value="${notification.title}" required>
                    </div>
                    <div class="col-12">
                        <label class="form-label fw-semibold">Nội dung</label>
                        <textarea class="form-control" name="content" rows="6" required>${notification.content}</textarea>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-semibold">Lớp nhận thông báo</label>
                        <select class="form-select" name="targetClassID" required>
                            <option value="">-- Chọn lớp --</option>
                            <c:forEach var="cls" items="${classes}">
                                <option value="${cls.classID}" ${notification.targetClassID != null && notification.targetClassID == cls.classID ? 'selected' : ''}>
                                    ${cls.gradeID}${cls.className}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 d-flex align-items-end">
                        <div class="alert alert-info w-100 mb-0">
                            <i class="bi bi-info-circle me-1"></i> Thông báo này luôn được gửi theo kiểu <strong>CLASS</strong>.
                        </div>
                    </div>
                </div>

                <input type="hidden" name="senderUsername" value="${sessionScope.user.username}" />
                <input type="hidden" name="targetType" value="CLASS" />

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">Gửi thông báo</button>
                    <a href="${pageContext.request.contextPath}/teacher/notifications" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</div>
