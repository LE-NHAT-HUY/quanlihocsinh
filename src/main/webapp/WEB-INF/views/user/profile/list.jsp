<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="student-profile container">
    <div class="row">
        <!-- Profile Photo Column -->
        <div class="col-xl-4 mb-3">
            <div class="card">
                <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">
                    <c:choose>
                        <c:when test="${not empty student and not empty student.images}">
                            <img src="${pageContext.request.contextPath}/assets/img/${student.images}" 
                                 alt="Profile" class="rounded-circle" 
                                 onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.png'" />
                        </c:when>
                        <c:when test="${not empty person and not empty person.images}">
                            <img src="${pageContext.request.contextPath}/assets/img/${person.images}" 
                                 alt="Profile" class="rounded-circle" 
                                 onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.png'" />
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/assets/images/default-avatar.png" 
                                 alt="Profile" class="rounded-circle"/>
                        </c:otherwise>
                    </c:choose>

                    <h2>
                        <c:choose>
                            <c:when test="${not empty student}">
                                ${student.fullName}
                            </c:when>
                            <c:when test="${not empty person}">
                                ${person.fullname}
                            </c:when>
                            <c:otherwise>
                                Chưa có tên
                            </c:otherwise>
                        </c:choose>
                    </h2>

                    <h3 class="text-muted">
                        <c:choose>
                            <c:when test="${not empty student}">
                                ${student.studentID}
                            </c:when>
                            <c:otherwise>
                                &nbsp;
                            </c:otherwise>
                        </c:choose>
                    </h3>

                    <div class="mt-2">
                        <c:choose>
                            <c:when test="${not empty student}">
                                <span class="badge ${student.statusStudent == 'Đang học' ? 'bg-success' : 'bg-secondary'}">
                                    ${student.statusStudent}
                                </span>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <!-- Information Column -->
        <div class="col-xl-8 mb-3">
            <div class="card">
                <div class="card-body pt-3">

                    <!-- Tabs -->
                    <ul class="nav nav-tabs nav-tabs-bordered mb-3">
                        <li class="nav-item">
                            <a class="nav-link active" data-bs-toggle="tab" href="#overview">Thông tin tổng quan</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-bs-toggle="tab" href="#edit">Chỉnh sửa thông tin</a>
                        </li>
                    </ul>

                    <div class="tab-content pt-2">
                        <!-- Overview Tab -->
                        <div class="tab-pane fade show active" id="overview">
                            <div class="row">
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Mã học sinh</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.studentID : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Họ và tên</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.fullName : (person != null ? person.fullname : '')}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Ngày sinh</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.birth : (person != null ? person.birth : '')}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Giới tính</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.gender : (person != null ? person.gender : '')}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Số điện thoại</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.numberPhone : (person != null ? person.phone : 'Chưa cập nhật')}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Dân tộc</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.nation : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Tôn giáo</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.religion : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Xóm/Thôn</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.hamlet : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Xã/Phường</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.commune : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Tỉnh/Thành phố</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.province : ''}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Quốc tịch</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.nationality : ''}" />
                                    </div>
                                </div>
                                <div class="col-12 info-field">
                                    <label class="field-label">Địa chỉ đầy đủ</label>
                                    <div class="field-value">
                                        <c:out value="${student != null ? student.address : (person != null ? person.address : '')}" />
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Trạng thái học</label>
                                    <div class="field-value">
                                        <c:if test="${student != null}">
                                            <span class="badge ${student.statusStudent == 'Đang học' ? 'bg-success' : 'bg-secondary'}">
                                                ${student.statusStudent}
                                            </span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col-lg-6 info-field">
                                    <label class="field-label">Hiển thị</label>
                                    <div class="field-value">
                                        <c:if test="${student != null}">
                                            <span class="badge ${student.isActive ? 'bg-success' : 'bg-secondary'}">
                                                ${student.isActive ? 'Có' : 'Không'}
                                            </span>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <!-- Additional Sections -->
                            <div class="additional-sections mt-3">
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">1</span>
                                        Quan hệ thân nhân
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">2</span>
                                        Đối tượng đào tạo
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">3</span>
                                        Thông tin Đảng/Đoàn/Quân Ngũ
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">4</span>
                                        Thông tin tài khoản NH/Bảo hiểm
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">5</span>
                                        Quá trình học tập
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">6</span>
                                        Liên hệ
                                    </h6>
                                </div>
                                <div class="section-card p-3 mb-2 rounded" style="background-color:#e3f2fd;">
                                    <h6 style="margin:0;">
                                        <span class="badge bg-primary me-2">7</span>
                                        Hồ sơ số hóa
                                    </h6>
                                </div>
                            </div>
                        </div>

                        <!-- Edit Tab -->
                        <div class="tab-pane fade" id="edit">
                            <p>Tính năng chỉnh sửa đang được phát triển...</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<style>
.info-field { padding: 10px 0; border-bottom: 1px solid #ebeef4; }
.info-field:last-child { border-bottom: none; }
.field-label { font-weight:600; color:#012970; margin-bottom:5px; }
.field-value { padding: 8px 12px; border-radius:6px; background:#f8f9fa; border:1px solid #ddd; display:flex; align-items:center; }
.profile-card img { width:150px; height:150px; object-fit:cover; border:5px solid #fff; box-shadow:0 0 20px rgba(1,41,112,0.1); }
.additional-sections .section-card:hover { background-color:#bbdefb; cursor:pointer; transform:translateY(-2px); box-shadow:0 4px 8px rgba(0,0,0,0.1); }
.nav-tabs-bordered .nav-link.active { background-color:#fff; color:#4154f1; border-bottom:2px solid #4154f1; }
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.additional-sections .section-card').forEach(card=>{
        card.addEventListener('click',function(){ 
            alert('Tính năng đang được phát triển...'); 
        });
    });
});
</script>
