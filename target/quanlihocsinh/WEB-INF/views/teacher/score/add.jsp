<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<div class="container-fluid p-0">

    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-1">
                    <i class="bi bi-pencil-square me-2"></i>Nhập điểm chi tiết
                </h2>
                <p class="mb-0 opacity-75">Cập nhật điểm số cho học sinh trong lớp.</p>
            </div>
            <div class="col-md-4 text-end">
                <i class="bi bi-calculator fs-1 opacity-50"></i>
            </div>
        </div>
    </div>

    <section class="section dashboard">

        <div class="card shadow-sm">
            <div class="card-header bg-white border-bottom py-3 d-flex justify-content-between align-items-center">
                 <h5 class="card-title mb-0">
                    <i class="bi bi-list-ol me-2"></i>Danh sách học sinh
                </h5>
                <a href="${pageContext.request.contextPath}/teacher/scores?classID=${classID}&subjectID=${subjectID}&yearSemesterID=${yearSemesterID}" class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left me-1"></i> Trở lại xem
                </a>
            </div>
            
            <div class="card-body mt-3">
                <form method="post" action="${pageContext.request.contextPath}/teacher/scores/saveBulk" id="scoreForm">
                    <input type="hidden" name="classID" value="${classID}">
                    <input type="hidden" name="subjectID" value="${subjectID}">
                    <input type="hidden" name="yearSemesterID" value="${yearSemesterID}">

                    <c:choose>
                        <c:when test="${empty studentsInClass}">
                            <div class="alert alert-warning text-center m-4" role="alert">
                                <i class="bi bi-exclamation-triangle-fill fs-3 d-block mb-2"></i>
                                <h5>Chưa có học sinh nào!</h5>
                                <p>Vui lòng liên hệ quản trị viên để thêm học sinh vào lớp này.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered align-middle datatable">
                                    <thead class="table-light text-center align-middle">
                                        <tr>
                                            <th style="width: 50px;">STT</th>
                                            <th style="width: 80px;">Mã HS</th>
                                            <th style="min-width: 150px;">Họ và tên</th>
                                            <th style="width: 60px;">GT</th>
                                            
                                            <th class="bg-primary bg-opacity-10" style="width: 65px;">Miệng<br><small>(1)</small></th>
                                            <th class="bg-primary bg-opacity-10" style="width: 65px;">Miệng<br><small>(2)</small></th>
                                            <th class="bg-success bg-opacity-10" style="width: 65px;">15p<br><small>(1)</small></th>
                                            <th class="bg-success bg-opacity-10" style="width: 65px;">15p<br><small>(2)</small></th>
                                            <th class="bg-warning bg-opacity-10" style="width: 70px;">Giữa kỳ</th>
                                            <th class="bg-danger bg-opacity-10" style="width: 70px;">Cuối kỳ</th>
                                            
                                            <th class="table-secondary" style="width: 60px;">TB</th>
                                            <th class="table-secondary">Xếp loại</th>
                                            <th style="min-width: 100px;">Ghi chú</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${studentsInClass}" var="sc" varStatus="loop">
                                            <c:set var="student" value="${sc.student}" />
                                            <c:set var="score" value="${scoreMap[sc.studentID]}" />
                                            <tr>
                                                <td class="text-center text-muted fw-bold">${loop.index + 1}</td>
                                                <td class="text-center text-primary small fw-bold">${sc.studentID}</td>
                                                <td class="fw-semibold">
                                                    ${student != null ? student.fullName : '<span class="text-danger fst-italic">Không có tên</span>'}
                                                </td>
                                                <td class="text-center small">
                                                    <c:choose>
                                                        <c:when test="${student.gender == 'M' or student.gender == 'Nam'}"><span class="text-primary"><i class="bi bi-gender-male"></i></span></c:when>
                                                        <c:when test="${student.gender == 'F' or student.gender == 'Nữ'}"><span class="text-danger"><i class="bi bi-gender-female"></i></span></c:when>
                                                        <c:otherwise>${student.gender}</c:otherwise>
                                                    </c:choose>
                                                </td>

                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="oral1_${sc.studentClassID}" value="${score != null ? score.oralScore1 : ''}" class="form-control form-control-sm text-center score-input fw-bold text-primary"></td>
                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="oral2_${sc.studentClassID}" value="${score != null ? score.oralScore2 : ''}" class="form-control form-control-sm text-center score-input fw-bold text-primary"></td>
                                                
                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="s15_1_${sc.studentClassID}" value="${score != null ? score.score15Minute1 : ''}" class="form-control form-control-sm text-center score-input fw-bold text-success"></td>
                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="s15_2_${sc.studentClassID}" value="${score != null ? score.score15Minute2 : ''}" class="form-control form-control-sm text-center score-input fw-bold text-success"></td>
                                                
                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="mid_${sc.studentClassID}" value="${score != null ? score.midtermScore : ''}" class="form-control form-control-sm text-center score-input fw-bold text-warning"></td>
                                                <td class="p-1"><input type="number" step="0.1" min="0" max="10" name="fin_${sc.studentClassID}" value="${score != null ? score.finalScore : ''}" class="form-control form-control-sm text-center score-input fw-bold text-danger"></td>

                                                <td class="text-center table-secondary fw-bold">
                                                    ${score != null && score.averageScore != null ? score.averageScore : '-'}
                                                </td>
                                                <td class="text-center table-secondary small">
                                                     <c:if test="${score != null && score.academicRating != null}">
                                                        <span class="badge ${score.academicRating == 'Giỏi' ? 'bg-success' : score.academicRating == 'Khá' ? 'bg-primary' : score.academicRating == 'Trung bình' ? 'bg-warning text-dark' : 'bg-danger'}">
                                                            ${score.academicRating}
                                                        </span>
                                                    </c:if>
                                                </td>
                                                
                                                <td class="p-1">
                                                    <input type="text" name="notes_${sc.studentClassID}" value="${score != null ? score.notes : ''}" class="form-control form-control-sm" placeholder="...">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="d-flex justify-content-end align-items-center mt-4 p-3 bg-light rounded border">
                                <div class="me-3 text-muted small">
                                    <i class="bi bi-info-circle me-1"></i>
                                    Điểm số sẽ tự động tính lại trung bình sau khi lưu.
                                </div>
                                <button type="submit" class="btn btn-success btn-lg px-5 shadow" id="saveButton">
                                    <i class="bi bi-save me-2"></i> Lưu tất cả
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </section>
</div>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<style>
    /* Custom style cho input điểm */
    .score-input:focus {
        background-color: #fffde7; /* Màu nền vàng nhạt khi focus */
        border-color: #ffc107;
        box-shadow: 0 0 0 0.2rem rgba(255, 193, 7, 0.25);
    }
</style>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 50, // Hiển thị nhiều học sinh để nhập 1 thể
        "lengthMenu": [10, 25, 50, 100],
        "language": { "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/vi.json" },
        "columnDefs": [ { "orderable": false, "targets": [4,5,6,7,8,9,12] } ] // Tắt sort ở các cột input
    });

    // Validate điểm ngay khi nhập
    $(document).on('input blur', '.score-input', function() {
        let val = parseFloat(this.value);
        if(this.value !== '' && (isNaN(val) || val < 0 || val > 10)){
            alert('Điểm không hợp lệ! Vui lòng nhập từ 0 đến 10.');
            this.value = '';
            $(this).addClass('is-invalid');
        } else {
            $(this).removeClass('is-invalid');
        }
    });

    $('#scoreForm').submit(function(e){
        if(!confirm('Xác nhận lưu bảng điểm? Dữ liệu cũ sẽ bị ghi đè.')){
            e.preventDefault();
        } else {
            $('#saveButton').html('<span class="spinner-border spinner-border-sm me-2"></span>Đang lưu...').prop('disabled', true);
        }
    });
});
</script>