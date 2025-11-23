<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>
<%@ page import="com.quanlihocsinh.model.Grade" %>
<main id="main" class="main">
    <div class="pagetitle">
        <h2>Chỉnh sửa Khối Lớp</h2>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-6">
                <div class="card">
                    <div class="card-body mt-3">
                        <form action="${pageContext.request.contextPath}/admin/grade/edit" method="post" accept-charset="UTF-8">
                            <input type="hidden" name="gradeID" value="${grade.gradeID}">

                            <div class="mb-3">
                                <label for="gradeName" class="form-label">Tên Khối Lớp</label>
                                <input type="text" class="form-control" id="gradeName" name="gradeName"
                                       value="${grade.gradeName}" required>
                            </div>

                            <div class="mb-3">
                                <label for="description" class="form-label">Mô tả</label>
                                <textarea class="form-control" id="description" name="description" rows="3">${grade.description}</textarea>
                            </div>

                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" id="isActive" name="isActive"
                                       ${grade.isActive ? 'checked' : ''}>
                                <label class="form-check-label" for="isActive">Hiển thị</label>
                            </div>

                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                            <a href="${pageContext.request.contextPath}/admin/grade/list" class="btn btn-secondary">Hủy</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
