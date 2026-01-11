<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Lịch sử thay đổi điểm số</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/home">Trang chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/scores">Quản lý điểm</a></li>
                <li class="breadcrumb-item active">Lịch sử</li>
            </ol>
        </nav>
    </div>

    <section class="section dashboard">
        <div class="card recent-sales overflow-auto">
            <div class="card-body mt-4">
                
                <style>
                    .badge-update { background-color: #ffc107; color: #000; } 
                    .badge-insert { background-color: #28a745; color: #fff; } 
                    .content-cell { 
                        max-width: 400px; 
                        word-wrap: break-word; 
                        white-space: pre-line; 
                        font-size: 0.85rem;
                    }
                    /* Tùy chỉnh màu chữ DataTables để khớp với theme */
                    .dataTables_wrapper .dataTables_length, .dataTables_wrapper .dataTables_filter, 
                    .dataTables_wrapper .dataTables_info, .dataTables_wrapper .dataTables_paginate {
                        margin-bottom: 15px;
                        font-size: 0.9rem;
                    }
                </style>

                <div class="d-flex justify-content-end mb-3">
                    <a href="${pageContext.request.contextPath}/admin/scores" class="btn btn-secondary btn-sm">
                        <i class="bi bi-arrow-left"></i> Quay lại
                    </a>
                </div>

                <table class="table table-borderless datatable">
                    <thead>
                        <tr>
                            <th class="text-center">Thời gian</th>
                            <th class="text-center">Giáo viên</th>
                            <th class="text-center">Học sinh</th>
                            <th class="text-center">Môn / Kỳ</th>
                            <th class="text-center">Hành động</th>
                            <th class="text-center">Nội dung thay đổi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="log" items="${logs}">
                            <tr>
                                <td class="text-center">
                                    <fmt:formatDate value="${log.changeDate}" pattern="dd/MM/yyyy"/><br>
                                    <small class="text-muted"><fmt:formatDate value="${log.changeDate}" pattern="HH:mm:ss"/></small>
                                </td>
                                <td><i class="bi bi-person-badge"></i> ${log.teacherName}</td>
                                <td><i class="bi bi-person"></i> ${log.studentName}</td>
                                <td>
                                    <strong>${log.subjectName}</strong><br>
                                    <small class="text-info">${log.semesterName}</small>
                                </td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${log.actionType == 'INSERT'}">
                                            <span class="badge badge-insert">Thêm mới</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-update">Cập nhật</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="content-cell">${log.changeContent}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 10, // Số dòng hiển thị mặc định
        "lengthMenu": [5, 10, 25, 50], // Các tùy chọn số dòng
        "order": [[0, "desc"]], // Mặc định sắp xếp theo cột Thời gian (cột 0) mới nhất
        "language": {
            "search": "Tìm kiếm:",
            "lengthMenu": "Hiển thị _MENU_ dòng",
            "info": "Đang hiển thị _START_ đến _END_ của _TOTAL_ dòng",
            "paginate": {
                "first": "Đầu",
                "last": "Cuối",
                "next": "Tiếp",
                "previous": "Trước"
            },
            "emptyTable": "Chưa có dữ liệu lịch sử nào"
        },
        "columnDefs": [ 
            { "orderable": false, "targets": [5] } // Tắt sắp xếp cho cột Nội dung thay đổi
        ]
    });
});
</script>