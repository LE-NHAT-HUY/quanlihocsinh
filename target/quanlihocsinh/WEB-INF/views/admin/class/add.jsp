<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>
<%@ page import="com.quanlihocsinh.model.tblClass" %>
<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm lớp học mới</h2>
    </div>

    <section class="section">
        <form action="${pageContext.request.contextPath}/admin/class/add" method="post">
            <div class="mb-3">
                <label for="className" class="form-label">Tên lớp</label>
                <input type="text" class="form-control" id="className" name="className" required />
            </div>
            <div class="mb-3">
                <label for="maxStudents" class="form-label">Sĩ số tối đa</label>
                <input type="number" class="form-control" id="maxStudents" name="maxStudents" required />
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="isActive" name="isActive" checked />
                <label class="form-check-label" for="isActive">Hiển thị</label>
            </div>
            <button type="submit" class="btn btn-success">Thêm mới</button>
            <a href="${pageContext.request.contextPath}/admin/class/list" class="btn btn-secondary">Hủy</a>
        </form>
    </section>
</main>
