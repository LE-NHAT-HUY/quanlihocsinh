<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Thêm mới Học sinh</h2>
    <a href="${pageContext.request.contextPath}/admin/student?action=list" class="btn btn-secondary">Quay lại</a>
  </div>

  <section class="section mt-3">
    <form action="${pageContext.request.contextPath}/admin/student" method="post">
      <input type="hidden" name="action" value="add" />

      <div class="mb-3">
        <label class="form-label">Mã HS</label>
        <input type="text" name="studentID" class="form-control" />
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

      <div class="mb-3">
        <label class="form-label">Ảnh (URL hoặc base64)</label>
        <input type="text" name="images" class="form-control" />
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
