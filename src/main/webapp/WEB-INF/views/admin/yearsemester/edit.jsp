<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h2>Sửa Năm học / Học kỳ</h2>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin">Trang chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/yearsemester/list">Danh sách</a></li>
                <li class="breadcrumb-item active">Sửa</li>
            </ol>
        </nav>
    </div>

    <section class="section">
        <div class="row justify-content-center">
            <div class="col-lg-6">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Chỉnh sửa thông tin</h5>

                        <form action="${pageContext.request.contextPath}/admin/yearsemester/edit" method="post">

                            <input type="hidden" name="yearSemesterID" value="${yearsemester.yearSemesterID}" />

                            <div class="mb-3">
                                <label class="form-label">Học kỳ</label>
                                <input type="text" 
                                       name="semesterName" 
                                       class="form-control"
                                       value="${yearsemester.semesterName}"
                                       required />
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Năm học</label>
                                <input type="text" 
                                       name="schoolYear" 
                                       class="form-control"
                                       value="${yearsemester.schoolYear}"
                                       required />
                            </div>

                            <div class="form-check mb-3">
                                <input type="checkbox" 
                                       name="isActive" 
                                       class="form-check-input"
                                       ${yearsemester.isActive ? 'checked' : ''} />
                                <label class="form-check-label">Hoạt động</label>
                            </div>

                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-save"></i> Cập nhật
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
