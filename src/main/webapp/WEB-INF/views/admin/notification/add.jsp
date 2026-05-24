<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Soạn thông báo mới</h2>
        <p class="text-muted mb-0">Gửi thông báo đến toàn trường hoặc một lớp cụ thể</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3">${errorMessage}</div>
    </c:if>

    <section class="section dashboard">
        <div class="row">
            <div class="col-10 col-xl-8">
                <div class="card">
                    <div class="card-body mt-3">
                        <form action="${pageContext.request.contextPath}/admin/notifications/add" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="title" class="form-label">Tiêu đề</label>
                                <input type="text" class="form-control" id="title" name="title" value="${notification.title}" required>
                            </div>

                            <div class="mb-3">
                                <label for="content" class="form-label">Nội dung</label>
                                <textarea class="form-control" id="content" name="content" rows="6" required>${notification.content}</textarea>
                            </div>

                            <div class="mb-3">
                                <label for="senderDepartment" class="form-label">Đơn vị gửi</label>
                                <input type="text" class="form-control" id="senderDepartment" name="senderDepartment"
                                       value="${not empty notification.senderDepartment ? notification.senderDepartment : 'Phòng Đào tạo'}">
                            </div>

                            <div class="mb-3">
                                <label for="targetType" class="form-label">Gửi đến</label>
                                <select class="form-select" id="targetType" name="targetType" onchange="toggleTargetClass()" required>
                                    <c:forEach var="type" items="${targetTypes}">
                                        <option value="${type}" ${notification.targetType == type ? 'selected' : ''}>
                                            <c:choose>
                                                <c:when test="${type == 'ALL'}">Toàn trường</c:when>
                                                <c:when test="${type == 'ALL_TEACHER'}">Toàn bộ giáo viên</c:when>
                                                <c:when test="${type == 'ALL_STUDENT'}">Toàn bộ học sinh</c:when>
                                                <c:otherwise>Chọn lớp cụ thể</c:otherwise>
                                            </c:choose>
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3" id="targetClassGroup" style="display:none;">
                                <label for="targetClassID" class="form-label">Lớp nhận thông báo</label>
                                <select class="form-select" id="targetClassID" name="targetClassID">
                                    <option value="">-- Chọn lớp --</option>
                                    <c:forEach var="cls" items="${classes}">
                                        <option value="${cls.classID}" ${notification.targetClassID != null && notification.targetClassID == cls.classID ? 'selected' : ''}>
                                            ${cls.gradeID}${cls.className}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="attachments" class="form-label">Tệp đính kèm</label>
                                <input type="file" class="form-control" id="attachments" name="attachments" multiple>
                                <div class="form-text">Có thể chọn nhiều file, hỗ trợ PDF, Word hoặc tài liệu khác.</div>
                            </div>

                            <input type="hidden" name="senderUsername" value="${sessionScope.user.username}" />

                            <button type="submit" class="btn btn-primary">Gửi thông báo</button>
                            <a href="${pageContext.request.contextPath}/admin/notifications" class="btn btn-secondary">Hủy</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script>
function toggleTargetClass() {
    var targetType = document.getElementById('targetType').value;
    var targetClassGroup = document.getElementById('targetClassGroup');
    if (targetType === 'CLASS') {
        targetClassGroup.style.display = 'block';
    } else {
        targetClassGroup.style.display = 'none';
    }
}
window.addEventListener('DOMContentLoaded', toggleTargetClass);
</script>