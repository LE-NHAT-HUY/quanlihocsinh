<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h2>Thêm Năm học / Học kỳ</h2>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin">Trang chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/yearsemester/list">Danh sách</a></li>
                <li class="breadcrumb-item active">Thêm mới</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row justify-content-center">
            <div class="col-lg-6">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Nhập thông tin</h5>

                        <form action="${pageContext.request.contextPath}/admin/yearsemester/add" method="post">

                            <div class="mb-3">
                                <label class="form-label">Học kỳ</label>
                                <input type="text" name="semesterName" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Năm học</label>
                                <input type="text" name="schoolYear" class="form-control" required>
                            </div>

                            <div class="form-check mb-3">
                                <input class="form-check-input" type="checkbox" name="isActive" checked>
                                <label class="form-check-label">
                                    Hoạt động
                                </label>
                            </div>

                            <button type="submit" class="btn btn-success">
                                <i class="bi bi-save"></i> Lưu
                            </button>

                            <a href="${pageContext.request.contextPath}/admin/yearsemester/list" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Hủy
                            </a>

                        </form>

                    </div>
                </div>

            </div>
        </div>
    </section>

</main>
