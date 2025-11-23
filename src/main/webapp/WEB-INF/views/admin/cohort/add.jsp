<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quanlihocsinh.model.Cohort" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm mới niên khóa</h2>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <form action="${pageContext.request.contextPath}/admin/cohort/add" method="post">
                            <div class="mb-3">
                                <label for="cohortName" class="form-label">Niên khóa</label>
                                <input type="text" class="form-control" id="cohortName" name="cohortName" required>
                            </div>
                            <div class="mb-3">
                                <label for="startYear" class="form-label">Năm vào</label>
                                <input type="number" class="form-control" id="startYear" name="startYear" required>
                            </div>
                            <div class="mb-3">
                                <label for="endYear" class="form-label">Năm ra</label>
                                <input type="number" class="form-control" id="endYear" name="endYear" required>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="isActive" name="isActive" checked>
                                <label class="form-check-label" for="isActive">Hiển thị</label>
                            </div>
                            <button type="submit" class="btn btn-success"><i class="bi bi-plus-circle"></i> Thêm mới</button>
                            <a href="${pageContext.request.contextPath}/admin/cohort/list" class="btn btn-secondary">Hủy</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
