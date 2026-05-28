<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0">Thêm Giáo viên mới</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher" class="btn btn-secondary">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${not empty sessionScope.flashSuccess}">
                            <div class="alert alert-success mt-3">${sessionScope.flashSuccess}</div>
                            <c:remove var="flashSuccess" scope="session" />
                        </c:if>
                        <c:if test="${not empty sessionScope.flashError}">
                            <div class="alert alert-danger mt-3">${sessionScope.flashError}</div>
                            <c:remove var="flashError" scope="session" />
                        </c:if>

                        <form action="${pageContext.request.contextPath}/admin/teacher" method="post" enctype="multipart/form-data" class="mt-3">
                            <input type="hidden" name="action" value="add" />
                            <input type="hidden" name="existingImages" id="existingImages" value="" />

                            <h5 class="card-title mt-3 pb-2 border-bottom">Thông tin nhân khẩu học</h5>
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <label class="form-label">Mã GV</label>
                                    <input type="text" name="teacherID" class="form-control" required />
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Họ tên</label>
                                    <input type="text" name="fullName" class="form-control" required />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Ngày sinh</label>
                                    <input type="date" name="birth" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Giới tính</label>
                                    <select name="gender" class="form-select">
                                        <option value="Nam">Nam</option>
                                        <option value="Nữ">Nữ</option>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">CCCD</label>
                                    <input type="text" name="cccd" class="form-control" />
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Dân tộc</label>
                                    <input type="text" name="nation" class="form-control" />
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Tôn giáo</label>
                                    <input type="text" name="religion" class="form-control" />
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Đảng viên</label>
                                    <input type="text" name="groupDV" class="form-control" />
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Quốc tịch</label>
                                    <input type="text" name="nationality" class="form-control" />
                                </div>
                            </div>

                            <h5 class="card-title mt-4 pb-2 border-bottom">Thông tin liên lạc &amp; Nơi ở</h5>
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <label class="form-label">Số điện thoại</label>
                                    <input type="text" name="numberPhone" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Thôn/xóm</label>
                                    <input type="text" name="hamlet" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Xã/Phường</label>
                                    <input type="text" name="commune" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Tỉnh</label>
                                    <input type="text" name="province" class="form-control" />
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Nơi ở hiện tại</label>
                                    <input type="text" name="address" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Tên liên hệ khẩn cấp</label>
                                    <input type="text" name="emergencyContactName" class="form-control" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">SĐT khẩn cấp</label>
                                    <input type="text" name="emergencyPhone" class="form-control" />
                                </div>
                            </div>

                            <h5 class="card-title mt-4 pb-2 border-bottom">Thông tin công tác &amp; Tài chính</h5>
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label class="form-label">Chức vụ</label>
                                    <input type="text" name="position" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Trạng thái công tác</label>
                                    <input type="text" name="statusTeacher" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Mã số phòng ban</label>
                                    <select name="departmentID" class="form-select" required>
                                        <option value="">-- Chọn phòng ban --</option>
                                        <c:forEach var="dept" items="${departments}">
                                            <option value="${dept.departmentID}">${dept.departmentName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Số BHXH</label>
                                    <input type="text" name="numberBHXH" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Mã số thuế</label>
                                    <input type="text" name="taxCode" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Tên Ngân hàng</label>
                                    <input type="text" name="bankName" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Số tài khoản</label>
                                    <input type="text" name="accountNumber" class="form-control" />
                                </div>
                            </div>

                            <h5 class="card-title mt-4 pb-2 border-bottom">Phân công giảng dạy</h5>
                            <div class="border rounded-3 p-3 bg-light">
                                <div id="subjectHint" class="text-muted mb-2">Hãy chọn phòng ban trước để hiển thị môn học phù hợp.</div>
                                <c:choose>
                                    <c:when test="${not empty subjects}">
                                        <div class="row g-2">
                                            <c:forEach var="s" items="${subjects}">
                                                <div class="col-md-4 col-sm-6 subject-item" data-department="${s.departmentID}" style="display: none;">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox"
                                                               name="subjectIDs" value="${s.subjectID}"
                                                               disabled
                                                               id="subject-${s.subjectID}" />
                                                        <label class="form-check-label" for="subject-${s.subjectID}">
                                                            ${s.subjectID} - ${s.subjectName}
                                                        </label>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-muted">Chưa có môn học nào để chọn.</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <h5 class="card-title mt-4 pb-2 border-bottom">Ảnh đại diện &amp; Trạng thái</h5>
                            <div class="row g-3 align-items-start">
                                <div class="col-lg-8">
                                    <label class="form-label">Ảnh</label>
                                    <input type="file" name="imageFile" id="imageFile" class="form-control"
                                           accept="image/jpeg,image/png,image/gif,image/webp" />
                                    <small class="text-muted d-block mt-2">Định dạng: JPEG, PNG, GIF, WebP. Kích thước tối đa: 5MB</small>
                                </div>
                                <div class="col-lg-4">
                                    <label class="form-label d-block">Preview</label>
                                    <img id="imagePreview" src="" alt="Preview"
                                         style="width: 120px; height: 120px; object-fit: cover; border: 1px solid #ddd; border-radius: 6px; display: none;" />
                                </div>
                                <div class="col-12">
                                    <div class="form-check">
                                        <input type="checkbox" name="isActive" class="form-check-input" checked />
                                        <label class="form-check-label">Kích hoạt</label>
                                    </div>
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-success">Thêm mới</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script>
document.addEventListener('DOMContentLoaded', function () {
    const departmentSelect = document.querySelector('select[name="departmentID"]');
    const subjectItems = document.querySelectorAll('.subject-item');
    const subjectHint = document.getElementById('subjectHint');

    function filterSubjects() {
        const selectedDepartmentId = departmentSelect ? departmentSelect.value : '';

        subjectItems.forEach(function (item) {
            const checkbox = item.querySelector('input[type="checkbox"]');
            const itemDepartmentId = item.dataset.department || '';
            const shouldShow = selectedDepartmentId && itemDepartmentId === selectedDepartmentId;

            item.style.display = shouldShow ? '' : 'none';

            if (!shouldShow && checkbox) {
                checkbox.checked = false;
                checkbox.disabled = true;
            } else if (checkbox) {
                checkbox.disabled = false;
            }
        });

        if (subjectHint) {
            subjectHint.style.display = selectedDepartmentId ? 'none' : '';
        }
    }

    if (departmentSelect) {
        departmentSelect.addEventListener('change', filterSubjects);
        filterSubjects();
    }
});

document.getElementById('imageFile').addEventListener('change', function(e) {
    const file = e.target.files[0];
    const preview = document.getElementById('imagePreview');

    if (file) {
        const reader = new FileReader();
        reader.onload = function(event) {
            preview.src = event.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    } else {
        preview.style.display = 'none';
        preview.src = '';
    }
});
</script>
