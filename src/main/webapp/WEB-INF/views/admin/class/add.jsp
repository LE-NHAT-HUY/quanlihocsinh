<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Thêm lớp mới</h2>
    <a href="${pageContext.request.contextPath}/admin/class?action=list" class="btn btn-secondary">Quay lại</a>
  </div>

  <section class="section mt-3">
    <form action="${pageContext.request.contextPath}/admin/class" method="post">
      <input type="hidden" name="action" value="add" />

      <div class="mb-3">
        <label class="form-label">Tên lớp</label>
        <input type="text" name="className" class="form-control" required />
      </div>

      <div class="mb-3">
        <label class="form-label">Khối (GradeID)</label>
        <input type="number" name="gradeID" class="form-control" required />
      </div>

      <div class="mb-3">
        <label class="form-label">Khóa (CohortID)</label>
        <input type="number" name="cohortID" class="form-control" />
      </div>

      <div class="mb-3">
        <label class="form-label">Số tối đa</label>
        <input type="number" name="maxStudents" class="form-control" value="30" />
      </div>

      <div class="mb-3">
        <label class="form-label">Số hiện tại</label>
        <input type="number" name="currentStudents" class="form-control" value="0" />
      </div>

      <div class="mb-3">
        <label class="form-label">Niên khóa (ví dụ: 2024-2025)</label>
        <input type="text" name="schoolYear" class="form-control" />
      </div>

      <div class="mb-3 form-check">
        <input type="checkbox" name="isActive" class="form-check-input" id="isActive" checked />
        <label class="form-check-label" for="isActive">Hoạt động</label>
      </div>

      <button type="submit" class="btn btn-primary">Thêm</button>
    </form>
  </section>
</main>
