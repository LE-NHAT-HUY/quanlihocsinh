<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Sửa thông tin Lớp</h2>
    <a href="${pageContext.request.contextPath}/admin/class?action=list" class="btn btn-secondary">Quay lại</a>
  </div>

  <section class="section mt-3">
    <form action="${pageContext.request.contextPath}/admin/class" method="post">
      <input type="hidden" name="action" value="edit" />
      <input type="hidden" name="id" value="${classItem.classID}" />

      <div class="mb-3">
        <label class="form-label">Tên lớp</label>
        <input type="text" name="className" class="form-control" value="${classItem.className}" required />
      </div>

      <div class="mb-3">
        <label class="form-label">Khối (GradeID)</label>
        <input type="number" name="gradeID" class="form-control" value="${classItem.gradeID}" required />
      </div>

      <div class="mb-3">
        <label class="form-label">Khóa (CohortID)</label>
        <input type="number" name="cohortID" class="form-control" value="${classItem.cohortID}" />
      </div>

      <div class="mb-3">
        <label class="form-label">Số tối đa</label>
        <input type="number" name="maxStudents" class="form-control" value="${classItem.maxStudents}" />
      </div>

      <div class="mb-3">
        <label class="form-label">Số hiện tại</label>
        <input type="number" name="currentStudents" class="form-control" value="${classItem.currentStudents}" />
      </div>

      <div class="mb-3">
        <label class="form-label">Niên khóa</label>
        <input type="text" name="schoolYear" class="form-control" value="${classItem.schoolYear}" />
      </div>

      <div class="mb-3 form-check">
        <input type="checkbox" name="isActive" class="form-check-input" id="isActive" <c:if test="${classItem.active}">checked</c:if>/>
        <label class="form-check-label" for="isActive">Hoạt động</label>
      </div>

      <button type="submit" class="btn btn-primary">Cập nhật</button>
    </form>
  </section>
</main>
