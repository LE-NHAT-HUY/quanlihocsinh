<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="profile-page">
    <!-- Profile Header Banner -->
    <div class="profile-header mb-4">
        <div class="profile-cover" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); height: 180px; border-radius: 12px 12px 0 0;"></div>
        <div class="profile-header-content px-4 pb-4" style="background: #fff; border-radius: 0 0 12px 12px; margin-top: -60px; position: relative;">
            <div class="row align-items-end">
                <div class="col-auto">
                    <div class="profile-avatar-wrapper" style="margin-top: -40px;">
                        <c:choose>
                            <c:when test="${not empty student and not empty student.images}">
                                <img src="${pageContext.request.contextPath}/assets/img/${student.images}" 
                                     alt="Profile" class="profile-avatar"
                                     onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.png'" />
                            </c:when>
                            <c:when test="${not empty person and not empty person.images}">
                                <img src="${pageContext.request.contextPath}/assets/img/${person.images}" 
                                     alt="Profile" class="profile-avatar"
                                     onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.png'" />
                            </c:when>
                            <c:otherwise>
                                <div class="profile-avatar d-flex align-items-center justify-content-center bg-light">
                                    <i class="bi bi-person-fill" style="font-size: 4rem; color: #adb5bd;"></i>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <button class="btn btn-sm btn-light rounded-circle avatar-edit-btn" title="Thay đổi ảnh">
                            <i class="bi bi-camera-fill"></i>
                        </button>
                    </div>
                </div>
                <div class="col pt-4">
                    <div class="d-flex flex-wrap align-items-center gap-2 mb-1">
                        <h3 class="mb-0 fw-bold">
                            <c:choose>
                                <c:when test="${not empty student}">${student.fullName}</c:when>
                                <c:when test="${not empty person}">${person.fullname}</c:when>
                                <c:otherwise>Chưa có tên</c:otherwise>
                            </c:choose>
                        </h3>
                        <c:if test="${not empty student}">
                            <span class="badge ${student.statusStudent == 'Đang học' ? 'bg-success' : 'bg-secondary'} rounded-pill">
                                <i class="bi bi-check-circle me-1"></i>${student.statusStudent}
                            </span>
                        </c:if>
                    </div>
                    <p class="text-muted mb-0">
                        <c:if test="${not empty student}">
                            <i class="bi bi-hash me-1"></i>${student.studentID}
                            <span class="mx-2">|</span>
                        </c:if>
                        <i class="bi bi-mortarboard me-1"></i>Học sinh Trường THPT Anh Sơn I
                    </p>
                </div>
                <div class="col-auto pt-4">
                    <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                        <i class="bi bi-pencil-square me-1"></i>Chỉnh sửa
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div class="row g-4">
        <!-- Left Column -->
        <div class="col-lg-4">
            <!-- Quick Info Card -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-white border-bottom">
                    <h6 class="mb-0 fw-semibold">
                        <i class="bi bi-info-circle me-2 text-primary"></i>Thông tin nhanh
                    </h6>
                </div>
                <div class="card-body">
                    <div class="quick-info-item d-flex align-items-center mb-3">
                        <div class="quick-info-icon bg-primary-subtle text-primary">
                            <i class="bi bi-calendar3"></i>
                        </div>
                        <div class="ms-3">
                            <small class="text-muted d-block">Ngày sinh</small>
                            <span class="fw-medium">
                                <c:out value="${student != null ? student.birth : (person != null ? person.birth : 'Chưa cập nhật')}" />
                            </span>
                        </div>
                    </div>
                    <div class="quick-info-item d-flex align-items-center mb-3">
                        <div class="quick-info-icon bg-success-subtle text-success">
                            <i class="bi bi-gender-ambiguous"></i>
                        </div>
                        <div class="ms-3">
                            <small class="text-muted d-block">Giới tính</small>
                            <span class="fw-medium">
                                <c:out value="${student != null ? student.gender : (person != null ? person.gender : 'Chưa cập nhật')}" />
                            </span>
                        </div>
                    </div>
                    <div class="quick-info-item d-flex align-items-center mb-3">
                        <div class="quick-info-icon bg-warning-subtle text-warning">
                            <i class="bi bi-telephone"></i>
                        </div>
                        <div class="ms-3">
                            <small class="text-muted d-block">Số điện thoại</small>
                            <span class="fw-medium">
                                <c:out value="${student != null ? student.numberPhone : (person != null ? person.phone : 'Chưa cập nhật')}" />
                            </span>
                        </div>
                    </div>
                    <div class="quick-info-item d-flex align-items-center">
                        <div class="quick-info-icon bg-danger-subtle text-danger">
                            <i class="bi bi-geo-alt"></i>
                        </div>
                        <div class="ms-3">
                            <small class="text-muted d-block">Địa chỉ</small>
                            <span class="fw-medium">
                                <c:out value="${student != null ? student.address : (person != null ? person.address : 'Chưa cập nhật')}" />
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Additional Sections Card -->
            <div class="card shadow-sm">
                <div class="card-header bg-white border-bottom">
                    <h6 class="mb-0 fw-semibold">
                        <i class="bi bi-folder2-open me-2 text-primary"></i>Hồ sơ mở rộng
                    </h6>
                </div>
                <div class="card-body p-0">
                    <div class="list-group list-group-flush">
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-primary">1</span>
                            <span>Quan hệ thân nhân</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-success">2</span>
                            <span>Đối tượng đào tạo</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-info">3</span>
                            <span>Thông tin Đảng/Đoàn/Quân Ngũ</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-warning">4</span>
                            <span>Tài khoản NH/Bảo hiểm</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-danger">5</span>
                            <span>Quá trình học tập</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-secondary">6</span>
                            <span>Liên hệ</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                        <a href="#" class="list-group-item list-group-item-action d-flex align-items-center section-link">
                            <span class="section-number bg-dark">7</span>
                            <span>Hồ sơ số hóa</span>
                            <i class="bi bi-chevron-right ms-auto"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Right Column -->
        <div class="col-lg-8">
            <!-- Tabs Navigation -->
            <div class="card shadow-sm">
                <div class="card-header bg-white border-bottom p-0">
                    <ul class="nav nav-tabs card-header-tabs" id="profileTabs" role="tablist">
                        <li class="nav-item" role="presentation">
                            <button class="nav-link active px-4 py-3" id="overview-tab" data-bs-toggle="tab" data-bs-target="#overview" type="button">
                                <i class="bi bi-person-vcard me-2"></i>Thông tin cá nhân
                            </button>
                        </li>
                        <li class="nav-item" role="presentation">
                            <button class="nav-link px-4 py-3" id="academic-tab" data-bs-toggle="tab" data-bs-target="#academic" type="button">
                                <i class="bi bi-book me-2"></i>Học tập
                            </button>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <div class="tab-content" id="profileTabsContent">
                        <!-- Overview Tab -->
                        <div class="tab-pane fade show active" id="overview" role="tabpanel">
                            <!-- Basic Info Section -->
                            <div class="info-section mb-4">
                                <h6 class="section-title">
                                    <i class="bi bi-person me-2"></i>Thông tin cơ bản
                                </h6>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Mã học sinh</label>
                                            <p><c:out value="${student != null ? student.studentID : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Họ và tên</label>
                                            <p><c:out value="${student != null ? student.fullName : (person != null ? person.fullname : 'N/A')}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Ngày sinh</label>
                                            <p><c:out value="${student != null ? student.birth : (person != null ? person.birth : 'N/A')}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Giới tính</label>
                                            <p><c:out value="${student != null ? student.gender : (person != null ? person.gender : 'N/A')}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Dân tộc</label>
                                            <p><c:out value="${student != null ? student.nation : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Tôn giáo</label>
                                            <p><c:out value="${student != null ? student.religion : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Quốc tịch</label>
                                            <p><c:out value="${student != null ? student.nationality : 'Việt Nam'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Số điện thoại</label>
                                            <p><c:out value="${student != null ? student.numberPhone : (person != null ? person.phone : 'N/A')}" /></p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Address Section -->
                            <div class="info-section mb-4">
                                <h6 class="section-title">
                                    <i class="bi bi-house me-2"></i>Địa chỉ thường trú
                                </h6>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Xóm/Thôn</label>
                                            <p><c:out value="${student != null ? student.hamlet : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Xã/Phường</label>
                                            <p><c:out value="${student != null ? student.commune : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Tỉnh/Thành phố</label>
                                            <p><c:out value="${student != null ? student.province : 'N/A'}" /></p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Địa chỉ đầy đủ</label>
                                            <p><c:out value="${student != null ? student.address : (person != null ? person.address : 'N/A')}" /></p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Status Section -->
                            <div class="info-section">
                                <h6 class="section-title">
                                    <i class="bi bi-activity me-2"></i>Trạng thái
                                </h6>
                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Trạng thái học</label>
                                            <p>
                                                <c:if test="${student != null}">
                                                    <span class="badge ${student.statusStudent == 'Đang học' ? 'bg-success' : 'bg-secondary'} rounded-pill">
                                                        ${student.statusStudent}
                                                    </span>
                                                </c:if>
                                                <c:if test="${student == null}">N/A</c:if>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="info-field">
                                            <label>Hiển thị hồ sơ</label>
                                            <p>
                                                <c:if test="${student != null}">
                                                    <span class="badge ${student.isActive ? 'bg-success' : 'bg-secondary'} rounded-pill">
                                                        ${student.isActive ? 'Đang hoạt động' : 'Không hoạt động'}
                                                    </span>
                                                </c:if>
                                                <c:if test="${student == null}">N/A</c:if>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Academic Tab -->
                                    <div class="tab-pane fade" id="academic" role="tabpanel">
                    <div class="py-4">
                        <h5 class="fw-bold mb-3">
                            <i class="bi bi-book me-2 text-primary"></i>Thông tin học tập
                        </h5>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <div class="border rounded p-3 bg-light">
                                    <strong>Lớp hiện tại:</strong>
                                    <c:choose>
                                        <c:when test="${not empty currentClass}">
                                            ${currentClass.gradeID}${currentClass.className}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Chưa có lớp</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="col-md-6">
                                <div class="border rounded p-3 bg-light">
                                    <strong>Khóa học:</strong>
                                    <c:choose>
                                        <c:when test="${not empty currentCohort}">
                                            ${currentCohort.cohortName} (${currentCohort.startYear} - ${currentCohort.endYear})
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-muted">Chưa có khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


                        <!-- Security Tab -->
                        <div class="tab-pane fade" id="security" role="tabpanel">
                            <div class="info-section mb-4">
                                <h6 class="section-title">
                                    <i class="bi bi-key me-2"></i>Đổi mật khẩu
                                </h6>
                                <form class="row g-3">
                                    <div class="col-12">
                                        <label class="form-label">Mật khẩu hiện tại</label>
                                        <input type="password" class="form-control" placeholder="Nhập mật khẩu hiện tại">
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Mật khẩu mới</label>
                                        <input type="password" class="form-control" placeholder="Nhập mật khẩu mới">
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Xác nhận mật khẩu mới</label>
                                        <input type="password" class="form-control" placeholder="Nhập lại mật khẩu mới">
                                    </div>
                                    <div class="col-12">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-lg me-1"></i>Cập nhật mật khẩu
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Edit Profile Modal -->
<div class="modal fade" id="editProfileModal" tabindex="-1">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <i class="bi bi-pencil-square me-2"></i>Chỉnh sửa thông tin
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" class="form-control" value="${student != null ? student.fullName : (person != null ? person.fullname : '')}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" class="form-control" value="${student != null ? student.numberPhone : (person != null ? person.phone : '')}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Ngày sinh</label>
                            <input type="date" class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Giới tính</label>
                            <select class="form-select">
                                <option>Nam</option>
                                <option>Nữ</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <label class="form-label">Địa chỉ</label>
                            <textarea class="form-control" rows="2">${student != null ? student.address : (person != null ? person.address : '')}</textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-primary">
                    <i class="bi bi-check-lg me-1"></i>Lưu thay đổi
                </button>
            </div>
        </div>
    </div>
</div>

<style>
/* Profile Header */
.profile-avatar {
    width: 140px;
    height: 140px;
    border-radius: 50%;
    object-fit: cover;
    border: 5px solid #fff;
    box-shadow: 0 4px 15px rgba(0,0,0,0.15);
}

.profile-avatar-wrapper {
    position: relative;
    display: inline-block;
}

.avatar-edit-btn {
    position: absolute;
    bottom: 8px;
    right: 8px;
    width: 36px;
    height: 36px;
    border: 2px solid #fff;
    box-shadow: 0 2px 8px rgba(0,0,0,0.15);
}

/* Quick Info */
.quick-info-icon {
    width: 48px;
    height: 48px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.25rem;
}

/* Section Links */
.section-number {
    width: 28px;
    height: 28px;
    border-radius: 8px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    font-size: 0.8rem;
    font-weight: 600;
    color: #fff;
    margin-right: 12px;
}

.section-link {
    padding: 14px 16px;
    transition: all 0.2s ease;
}

.section-link:hover {
    background-color: #f8f9fa;
    padding-left: 20px;
}

/* Tabs */
#profileTabs .nav-link {
    border: none;
    color: #6c757d;
    border-bottom: 3px solid transparent;
    border-radius: 0;
    font-weight: 500;
}

#profileTabs .nav-link:hover {
    color: #4154f1;
    border-color: transparent;
}

#profileTabs .nav-link.active {
    color: #4154f1;
    background: transparent;
    border-bottom-color: #4154f1;
}

/* Info Sections */
.section-title {
    font-weight: 600;
    color: #012970;
    padding-bottom: 12px;
    margin-bottom: 16px;
    border-bottom: 2px solid #f0f0f0;
}

.info-field {
    background: #f8f9fa;
    padding: 14px 16px;
    border-radius: 10px;
    border: 1px solid #e9ecef;
    height: 100%;
}

.info-field label {
    font-size: 0.8rem;
    color: #6c757d;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-bottom: 4px;
    display: block;
}

.info-field p {
    margin: 0;
    font-weight: 500;
    color: #2c3e50;
}

/* Responsive */
@media (max-width: 768px) {
    .profile-header-content .row {
        text-align: center;
    }
    .profile-header-content .col-auto {
        width: 100%;
        margin-bottom: 16px;
    }
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Section links click handler
    document.querySelectorAll('.section-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            alert('Tính năng đang được phát triển...');
        });
    });
});
</script>

