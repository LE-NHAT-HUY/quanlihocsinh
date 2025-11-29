<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Sửa Học sinh</h2>
    <a href="${pageContext.request.contextPath}/admin/student?action=list" class="btn btn-secondary">Quay lại</a>
  </div>

  <section class="section mt-3">
    <form action="${pageContext.request.contextPath}/admin/student" method="post">
      <input type="hidden" name="action" value="edit" />
      <input type="hidden" name="id" value="${student.id}" />

      <div class="mb-3">
        <label class="form-label">Mã HS</label>
        <input type="text" name="studentID" class="form-control" value="${student.studentID}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Họ tên</label>
        <input type="text" name="fullName" class="form-control" value="${student.fullName}" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Ngày sinh</label>
        <input type="date" name="birth" class="form-control" value="<c:if test='${not empty student.birth}'><fmt:formatDate value='${student.birth}' pattern='yyyy-MM-dd'/></c:if>" />
      </div>
      <div class="mb-3">
        <label class="form-label">Giới tính</label>
        <select name="gender" class="form-control">
          <option value="Nam" ${student.gender=='Nam' ? 'selected' : ''}>Nam</option>
          <option value="Nữ" ${student.gender=='Nữ' ? 'selected' : ''}>Nữ</option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Địa chỉ</label>
        <input type="text" name="address" class="form-control" value="${student.address}" />
      </div>

      <div class="mb-3">
        <label class="form-label">Dân tộc</label>
        <input type="text" name="nation" class="form-control" value="${student.nation}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Tôn giáo</label>
        <input type="text" name="religion" class="form-control" value="${student.religion}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Trạng thái</label>
        <input type="text" name="statusStudent" class="form-control" value="${student.statusStudent}" />
      </div>
      <div class="mb-3">
        <label class="form-label">SĐT</label>
        <input type="text" name="numberPhone" class="form-control" value="${student.numberPhone}" />
      </div>

      <div class="mb-3 form-check">
        <input type="checkbox" name="isActive" class="form-check-input" id="isActive" <c:if test="${student.isActive}">checked</c:if>/>
        <label class="form-check-label" for="isActive">Hoạt động</label>
      </div>

      <div class="mb-3">
        <label class="form-label">Ảnh (URL hoặc base64)</label>
        <input type="text" name="images" class="form-control" value="${student.images}" />
      </div>

      <div class="mb-3">
        <label class="form-label">Xóm</label>
        <input type="text" name="hamlet" class="form-control" value="${student.hamlet}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Xã</label>
        <input type="text" name="commune" class="form-control" value="${student.commune}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Tỉnh</label>
        <input type="text" name="province" class="form-control" value="${student.province}" />
      </div>
      <div class="mb-3">
        <label class="form-label">Quốc tịch</label>
        <input type="text" name="nationality" class="form-control" value="${student.nationality}" />
      </div>

      <button type="submit" class="btn btn-primary">Cập nhật</button>
    </form>
  </section>
</main>
