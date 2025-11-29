<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Thêm Menu</h2>
    </div>

    <section class="section dashboard">
        <form action="${pageContext.request.contextPath}/admin/menu/add" method="post" class="row g-3">

            <div class="col-md-6">
                <label class="form-label">Tên Menu</label>
                <input type="text" name="menuName" class="form-control" required />
            </div>

            <div class="col-md-6">
                <label class="form-label">Controller</label>
                <input type="text" name="controllerName" class="form-control" />
            </div>

            <div class="col-md-6">
                <label class="form-label">Action</label>
                <input type="text" name="actionName" class="form-control" />
            </div>

            <div class="col-md-3">
                <label class="form-label">Level</label>
                <input type="number" name="levels" class="form-control" />
            </div>

            <div class="col-md-3">
                <label class="form-label">ParentID</label>
                <input type="number" name="parentID" class="form-control" />
            </div>

            <div class="col-md-3">
                <label class="form-label">Thứ tự</label>
                <input type="number" name="menuOrder" class="form-control" />
            </div>

            <div class="col-md-3">
                <label class="form-label">Vị trí</label>
                <input type="number" name="position" class="form-control" />
            </div>

            <div class="col-md-6">
                <label class="form-label">Icon</label>
                <input type="text" name="icon" class="form-control" />
            </div>

            <div class="col-md-6">
                <label class="form-label">ID Name</label>
                <input type="text" name="idName" class="form-control" />
            </div>

            <div class="col-md-6">
                <label class="form-label">Item Target</label>
                <input type="text" name="itemTarget" class="form-control" />
            </div>

            <div class="col-12">
                <label class="form-label">Hiển thị</label>
                <input type="checkbox" name="isActive" checked />
            </div>

            <div class="col-12">
                <button type="submit" class="btn btn-success">
                    <i class="bi bi-save"></i> Lưu
                </button>
                <a href="${pageContext.request.contextPath}/admin/menu" class="btn btn-secondary">Quay lại</a>
            </div>
        </form>
    </section>
</main>
