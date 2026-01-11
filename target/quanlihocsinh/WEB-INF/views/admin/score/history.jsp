<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử thay đổi điểm</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .badge-update { background-color: #ffc107; color: #000; } /* Màu vàng */
        .badge-insert { background-color: #28a745; color: #fff; } /* Màu xanh lá */
        
        /* Giúp nội dung dài tự xuống dòng và giữ nguyên định dạng dòng */
        .content-cell { 
            max-width: 450px; 
            word-wrap: break-word; 
            white-space: pre-line; 
            font-size: 0.95em;
            color: #333;
        }
        .table th { vertical-align: middle; text-align: center; }
        .table td { vertical-align: middle; }
    </style>
</head>
<body>

<div class="container-fluid mt-4">
    
    <div class="alert alert-info">
        <strong>Debug Info:</strong> 
        Số lượng bản ghi tìm thấy: 
        <span class="badge badge-light" style="font-size: 1.2em;">
            ${logs != null ? logs.size() : "NULL (Chưa nhận được biến 'logs')"}
        </span>
    </div>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="text-primary"><i class="fas fa-history"></i> Lịch sử thay đổi điểm số</h3>
        <a href="${pageContext.request.contextPath}/admin/scores" class="btn btn-secondary">
            <i class="fa fa-arrow-left"></i> Quay lại bảng điểm
        </a>
    </div>

    <div class="card shadow">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-bordered table-hover table-striped mb-0">
                    <thead class="thead-dark">
                    <tr>
                        <th style="width: 150px;">Thời gian</th>
                        <th style="width: 180px;">Giáo viên</th>
                        <th style="width: 180px;">Học sinh</th>
                        <th style="width: 200px;">Môn / Kỳ</th>
                        <th style="width: 100px;">Hành động</th>
                        <th>Chi tiết thay đổi</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    <c:if test="${empty logs}">
                        <tr>
                            <td colspan="6" class="text-center py-4 text-muted">
                                <i>Chưa có dữ liệu lịch sử nào được ghi nhận.</i>
                            </td>
                        </tr>
                    </c:if>

                    <c:forEach var="log" items="${logs}">
                        <tr>
                            <td class="text-center">
                                <fmt:formatDate value="${log.changeDate}" pattern="dd/MM/yyyy"/><br>
                                <small class="text-muted"><fmt:formatDate value="${log.changeDate}" pattern="HH:mm:ss"/></small>
                            </td>
                            <td>
                                <i class="fas fa-chalkboard-teacher text-muted"></i> ${log.teacherName}
                            </td>
                            <td>
                                <i class="fas fa-user-graduate text-muted"></i> ${log.studentName}
                            </td>
                            <td>
                                <strong>${log.subjectName}</strong><br>
                                <small class="text-info">${log.semesterName}</small>
                            </td>
                            <td class="text-center">
                                <c:choose>
                                    <c:when test="${log.actionType == 'INSERT'}">
                                        <span class="badge badge-insert p-2">Thêm mới</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-update p-2">Cập nhật</span>
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
    </div>
</div>

</body>
</html>