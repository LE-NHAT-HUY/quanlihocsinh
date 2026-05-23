<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Thêm mới Học sinh</h2>
    <a href="${pageContext.request.contextPath}/admin/student?action=list" class="btn btn-secondary">Quay lại</a>
  </div>

  <section class="section mt-3">
    <c:if test="${not empty param.error}">
      <div class="alert alert-danger">${param.error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/student" method="post" enctype="multipart/form-data">
      <input type="hidden" name="action" value="add" />
      <input type="hidden" name="existingImages" id="existingImages" value="" />

      <div class="mb-3">
        <label class="form-label">Mã HS</label>
        <input type="text" name="studentID" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Họ tên</label>
        <input type="text" name="fullName" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Ngày sinh</label>
        <input type="date" name="birth" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Giới tính</label>
        <select name="gender" class="form-control">
          <option value="Nam">Nam</option>
          <option value="Nữ">Nữ</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Địa chỉ</label>
        <input type="text" name="address" class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Dân tộc</label>
        <input type="text" name="nation" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Tôn giáo</label>
        <input type="text" name="religion" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Trạng thái</label>
        <input type="text" name="statusStudent" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">SĐT</label>
        <input type="text" name="numberPhone" class="form-control" />
      </div>

      <div class="mb-3 form-check">
        <input type="checkbox" name="isActive" class="form-check-input" id="isActive" checked />
        <label class="form-check-label" for="isActive">Hoạt động</label>
      </div>

      <!-- Ảnh upload -->
      <div class="mb-3">
        <label class="form-label">Ảnh</label>
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

      <div class="mb-3">
        <label class="form-label">Xóm</label>
        <input type="text" name="hamlet" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Xã</label>
        <input type="text" name="commune" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Tỉnh</label>
        <input type="text" name="province" class="form-control" />
      </div>
      <div class="mb-3">
        <label class="form-label">Quốc tịch</label>
        <input type="text" name="nationality" class="form-control" />
      </div>

      <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
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
