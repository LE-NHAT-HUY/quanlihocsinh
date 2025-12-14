<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="container-fluid p-0">
    
    <!-- Welcome Banner -->
    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-2">
                    <i class="bi bi-hand-wave me-2"></i>
                    Xin chào, 
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.profile.fullname}">
                            <c:out value="${sessionScope.user.profile.fullname}" />
                        </c:when>
                        <c:otherwise>
                            <c:out value="${sessionScope.user.username}" />
                        </c:otherwise>
                    </c:choose>!
                </h2>
                <p class="mb-0 opacity-75">
                    <c:choose>
                        <c:when test="${sessionScope.user.roleId == 1}">
                            Chào mừng quản trị viên đến với hệ thống quản lý trường học.
                        </c:when>
                        <c:when test="${sessionScope.user.roleId == 2}">
                            Chào mừng giáo viên đến với hệ thống quản lý.
                        </c:when>
                        <c:when test="${sessionScope.user.roleId == 3}">
                            Chào mừng học sinh đến với cổng thông tin trường học.
                        </c:when>
                        <c:otherwise>
                            Chào mừng bạn đến với hệ thống.
                        </c:otherwise>
                    </c:choose>
                </p>
            </div>
            <div class="col-md-4 text-end">
                <div class="d-flex align-items-center justify-content-end">
                    <div class="me-3 text-end">
                        <small class="d-block opacity-75">Ngày hôm nay</small>
                        <strong><fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy"/></strong>
                    </div>
                    <i class="bi bi-calendar3 fs-1 opacity-50"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        
        <!-- CỘT TRÁI -->
        <div class="col-lg-8">
            
            <!-- Thông tin cá nhân -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white border-bottom">
                    <h5 class="card-title mb-0">
                        <i class="bi bi-person-badge me-2 text-primary"></i>Thông tin cá nhân
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3 text-center mb-3 mb-md-0">
                            <c:choose>
                                <c:when test="${not empty sessionScope.user.profile.images}">
                                    <img src="${pageContext.request.contextPath}/${sessionScope.user.profile.images}" 
                                         alt="Avatar" class="rounded-circle mb-2" 
                                         style="width: 120px; height: 120px; object-fit: cover; border: 4px solid #e9ecef;">
                                </c:when>
                                <c:otherwise>
                                    <div class="rounded-circle mx-auto d-flex align-items-center justify-content-center mb-2" 
                                         style="width: 120px; height: 120px; background: #e9ecef;">
                                        <i class="bi bi-person-fill fs-1 text-secondary"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <span class="badge bg-primary">
                                <c:choose>
                                    <c:when test="${sessionScope.user.roleId == 1}">Quản trị viên</c:when>
                                    <c:when test="${sessionScope.user.roleId == 2}">Giáo viên</c:when>
                                    <c:when test="${sessionScope.user.roleId == 3}">Học sinh</c:when>
                                    <c:otherwise>Người dùng</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="col-md-9">
                            <div class="row g-3">
                                <div class="col-sm-6">
                                    <label class="text-muted small">Họ và tên</label>
                                    <p class="mb-0 fw-semibold">
                                        <c:out value="${sessionScope.user.profile.fullname}" default="Chưa cập nhật" />
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <label class="text-muted small">Tên đăng nhập</label>
                                    <p class="mb-0 fw-semibold">
                                        <c:out value="${sessionScope.user.username}" />
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <label class="text-muted small">Giới tính</label>
                                    <p class="mb-0">
                                        <c:out value="${sessionScope.user.profile.gender}" default="Chưa cập nhật" />
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <label class="text-muted small">Ngày sinh</label>
                                    <p class="mb-0">
                                        <c:choose>
                                            <c:when test="${not empty sessionScope.user.profile.birth}">
                                                <fmt:formatDate value="${sessionScope.user.profile.birth}" pattern="dd/MM/yyyy"/>
                                            </c:when>
                                            <c:otherwise>Chưa cập nhật</c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <label class="text-muted small">Số điện thoại</label>
                                    <p class="mb-0">
                                        <c:out value="${sessionScope.user.profile.phone}" default="Chưa cập nhật" />
                                    </p>
                                </div>
                                <div class="col-sm-6">
                                    <label class="text-muted small">Địa chỉ</label>
                                    <p class="mb-0">
                                        <c:out value="${sessionScope.user.profile.address}" default="Chưa cập nhật" />
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Giới thiệu trường -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white border-bottom">
                    <div class="d-flex justify-content-between align-items-center">
                        <h5 class="card-title mb-0">
                            <i class="bi bi-building me-2 text-success"></i>Giới thiệu Trường THPT Anh Sơn I
                        </h5>
                        <span class="badge bg-warning text-dark">
                            <i class="bi bi-award-fill me-1"></i>Trường chuẩn quốc gia
                        </span>
                    </div>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-7">
                            <p class="mb-2">
                                <strong>Gần 60 năm phát triển</strong>, nhà trường đã đào tạo nhiều thế hệ học sinh
                                thành đạt, góp phần xây dựng quê hương Nghệ An.
                            </p>
                            <ul class="mb-2">
                                <li>Tỷ lệ tốt nghiệp THPT trên 98% hằng năm.</li>
                                <li>Nhiều học sinh đạt giải cấp tỉnh, cấp quốc gia.</li>
                                <li>Cơ sở vật chất hiện đại, môi trường thân thiện.</li>
                                <li>Đội ngũ giáo viên tâm huyết, chuyên môn cao.</li>
                            </ul>
                            <p class="mb-0 text-muted small">
                                <i class="bi bi-lightbulb me-1"></i>
                                <em>Tầm nhìn: Trở thành trường chuẩn quốc gia, tiên phong đổi mới giáo dục miền Trung.</em>
                            </p>
                        </div>
                        <div class="col-md-5">
                            <img src="${pageContext.request.contextPath}/assets/img/Untitled-1-1.jpg5_-1.jpg"
                                 alt="Trường THPT Anh Sơn I"
                                 class="img-fluid rounded shadow-sm" />
                        </div>
                    </div>
                </div>
            </div>

            <!-- Bản đồ -->
            <div class="card shadow-sm">
                <div class="card-header bg-white border-bottom">
                    <h5 class="card-title mb-0">
                        <i class="bi bi-geo-alt me-2 text-danger"></i>Vị trí trường trên bản đồ
                    </h5>
                </div>
                <div class="card-body">
                    <div class="ratio ratio-16x9 rounded overflow-hidden">
                        <iframe 
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3773.123456789!2d105.2076!3d18.9271!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zMTjCsDU1JzM3LjUiTiAxMDXCsDEyJzI3LjUiRQ!5e0!3m2!1svi!2s!4v1234567890"
                            style="border:0;" 
                            allowfullscreen="" 
                            loading="lazy">
                        </iframe>
                    </div>
                    <small class="text-muted mt-2 d-block">
                        <i class="bi bi-info-circle me-1"></i>
                        18°55'37.5"N 105°12'27.5"E — THPT Anh Sơn I, Nghệ An
                    </small>
                </div>
            </div>

        </div>

        <!-- CỘT PHẢI -->
        <div class="col-lg-4">

            <!-- Thống kê nhanh -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white border-bottom">
                    <h5 class="card-title mb-0">
                        <i class="bi bi-bar-chart me-2 text-info"></i>Thống kê nhanh
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row g-3">
                        <div class="col-6">
                            <div class="p-3 rounded-3 text-center" style="background: #e3f2fd;">
                                <i class="bi bi-mortarboard fs-3 text-primary mb-2 d-block"></i>
                                <div class="fs-4 fw-bold text-primary">
                                    <c:out value="${totalStudents}" default="--" />
                                </div>
                                <small class="text-muted">Học sinh</small>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-3 rounded-3 text-center" style="background: #e8f5e9;">
                                <i class="bi bi-person-workspace fs-3 text-success mb-2 d-block"></i>
                                <div class="fs-4 fw-bold text-success">
                                    <c:out value="${totalTeachers}" default="--" />
                                </div>
                                <small class="text-muted">Giáo viên</small>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-3 rounded-3 text-center" style="background: #fff3e0;">
                                <i class="bi bi-door-open fs-3 text-warning mb-2 d-block"></i>
                                <div class="fs-4 fw-bold text-warning">
                                    <c:out value="${totalClasses}" default="--" />
                                </div>
                                <small class="text-muted">Lớp học</small>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="p-3 rounded-3 text-center" style="background: #fce4ec;">
                                <i class="bi bi-book fs-3 text-danger mb-2 d-block"></i>
                                <div class="fs-4 fw-bold text-danger">
                                    <c:out value="${totalSubjects}" default="--" />
                                </div>
                                <small class="text-muted">Môn học</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Truy cập nhanh -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white border-bottom">
                    <h5 class="card-title mb-0">
                        <i class="bi bi-lightning me-2 text-warning"></i>Truy cập nhanh
                    </h5>
                </div>
                <div class="card-body p-0">
                    <div class="list-group list-group-flush">
                        <c:if test="${sessionScope.user.roleId == 1}">
                            <a href="${pageContext.request.contextPath}/admin/students" class="list-group-item list-group-item-action">
                                <i class="bi bi-people me-2"></i>Quản lý học sinh
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/teachers" class="list-group-item list-group-item-action">
                                <i class="bi bi-person-badge me-2"></i>Quản lý giáo viên
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/classes" class="list-group-item list-group-item-action">
                                <i class="bi bi-door-open me-2"></i>Quản lý lớp học
                            </a>
                            <a href="${pageContext.request.contextPath}/admin/users" class="list-group-item list-group-item-action">
                                <i class="bi bi-shield-lock me-2"></i>Quản lý tài khoản
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.user.roleId == 2}">
                            <a href="${pageContext.request.contextPath}/teacher/classes" class="list-group-item list-group-item-action">
                                <i class="bi bi-door-open me-2"></i>Lớp giảng dạy
                            </a>
                            <a href="${pageContext.request.contextPath}/teacher/grades" class="list-group-item list-group-item-action">
                                <i class="bi bi-journal-check me-2"></i>Nhập điểm
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.user.roleId == 3}">
                            <a href="${pageContext.request.contextPath}/student/grades" class="list-group-item list-group-item-action">
                                <i class="bi bi-trophy me-2"></i>Xem điểm
                            </a>
                            <a href="${pageContext.request.contextPath}/student/schedule" class="list-group-item list-group-item-action">
                                <i class="bi bi-calendar-week me-2"></i>Thời khóa biểu
                            </a>
                        </c:if>
                        <a href="${pageContext.request.contextPath}/profile" class="list-group-item list-group-item-action">
                            <i class="bi bi-person-circle me-2"></i>Hồ sơ cá nhân
                        </a>
                    </div>
                </div>
            </div>

            <!-- Tổ chuyên môn -->
            <div class="card shadow-sm">
                <div class="card-header bg-white border-bottom">
                    <h5 class="card-title mb-0">
                        <i class="bi bi-telephone me-2 text-secondary"></i>Liên hệ
                    </h5>
                </div>
                <div class="card-body" style="max-height: 350px; overflow-y: auto;">
                    <div class="mb-3">
                        <div class="fw-semibold text-primary mb-1">
                            <i class="bi bi-star-fill me-1 small"></i>Ban giám hiệu
                        </div>
                        <ul class="ps-3 mb-0 small">
                            <li>Ngô Đình Anh - <a href="tel:0937858693">0937 858 693</a></li>
                            <li>Nguyễn Thanh Hằng - <a href="tel:0986557575">0986 557 575</a></li>
                            <li>Nguyễn Thị Hương - <a href="tel:0885858586">0885 858 586</a></li>
                        </ul>
                    </div>
                    <hr>
                    <div class="mb-3">
                        <div class="fw-semibold mb-1">Tổ Ngoại ngữ</div>
                        <ul class="ps-3 mb-0 small">
                            <li>Phạm Thế Báo - <a href="tel:0382737481">0382 737 481</a></li>
                            <li>Nguyễn Thị Đào - <a href="tel:0994748423">0994 748 423</a></li>
                        </ul>
                    </div>
                    <div class="mb-3">
                        <div class="fw-semibold mb-1">Tổ Toán</div>
                        <ul class="ps-3 mb-0 small">
                            <li>Thành Công - <a href="tel:0576868686">0576 868 686</a></li>
                        </ul>
                    </div>
                    <div class="mb-3">
                        <div class="fw-semibold mb-1">Tổ GDCT</div>
                        <ul class="ps-3 mb-0 small">
                            <li>Nguyễn Thị Minh Anh - <a href="tel:0585959584">0585 959 584</a></li>
                            <li>Nguyễn Văn Tiến - <a href="tel:0986546574">0986 546 574</a></li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>