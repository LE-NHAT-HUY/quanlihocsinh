<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm Giáo viên mới</h2>
        <a href="${pageContext.request.contextPath}/admin/teacher" class="btn btn-secondary mb-2">Quay lại</a>
    </div>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <c:if test="${not empty sessionScope.flashSuccess}">
                            <div class="alert alert-success">${sessionScope.flashSuccess}</div>
                            <c:remove var="flashSuccess" scope="session" />
                        </c:if>
                        <c:if test="${not empty sessionScope.flashError}">
                            <div class="alert alert-danger">${sessionScope.flashError}</div>
                            <c:remove var="flashError" scope="session" />
                        </c:if>
                        <form action="${pageContext.request.contextPath}/admin/teacher" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="add" />
                            <input type="hidden" name="existingImages" id="existingImages" value="" />

                            <div class="mb-3">
                                <label>Mã GV</label>
                                <input type="text" name="teacherID" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Họ tên</label>
                                <input type="text" name="fullName" class="form-control" required />
                            </div>
                            <div class="mb-3">
                                <label>Ngày sinh</label>
                                <input type="date" name="birth" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Giới tính</label>
                                <select name="gender" class="form-select">
                                    <option value="Nam">Nam</option>
                                    <option value="Nữ">Nữ</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label>Địa chỉ</label>
                                <input type="text" name="address" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Trạng thái</label>
                                <input type="text" name="statusTeacher" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>CCCD</label>
                                <input type="text" name="cccd" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Dân tộc</label>
                                <input type="text" name="nation" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Tôn giáo</label>
                                <input type="text" name="religion" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Đảng viên</label>
                                <input type="text" name="groupDV" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Điện thoại</label>
                                <input type="text" name="numberPhone" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Số BHXH</label>
                                <input type="text" name="numberBHXH" class="form-control" />
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" name="isActive" class="form-check-input" checked />
                                <label class="form-check-label">Kích hoạt</label>
                            </div>
                            <div class="mb-3">
                                <label>Mã số phòng ban</label>
                                <input type="number" name="departmentID" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Thôn/xóm</label>
                                <input type="number" name="hamlet" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Huyên/Thành phố</label>
                                <input type="text" name="commune" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Tỉnh</label>
                                <input type="text" name="province" class="form-control" />
                            </div>
                            <div class="mb-3">
                                <label>Quốc tịch</label>
                                <input type="text" name="nationality" class="form-control" />
                            </div>

                            <!-- Ảnh upload -->
                            <div class="mb-3">
                                <label>Ảnh</label>
                                <div class="d-flex align-items-start gap-3">
                                    <div class="flex-grow-1">
                                        <input type="file" name="imageFile" id="imageFile" class="form-control" 
                                               accept="image/jpeg,image/png,image/gif,image/webp" />
                                        <small class="text-muted d-block mt-2">
                                            Định dạng: JPEG, PNG, GIF, WebP. Kích thước tối đa: 5MB
                                        </small>
                                    </div>
                                    <!-- Ảnh preview -->
                                    <div class="image-preview-container" style="flex-shrink: 0;">
                                        <img id="imagePreview" src="" alt="Preview" 
                                             style="width: 100px; height: 100px; object-fit: cover; border: 1px solid #ddd; border-radius: 4px; display: none;" />
                                    </div>
                                </div>
                            </div>

                            <button type="submit" class="btn btn-success">Thêm mới</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script>
  // Preview ảnh khi chọn file
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
    }
  });
</script>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
