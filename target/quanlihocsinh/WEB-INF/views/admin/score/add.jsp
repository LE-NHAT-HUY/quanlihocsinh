<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Nhập điểm học sinh</h2>
    </div>

    <section class="section dashboard">
        <div class="card">
            <div class="card-body mt-3">
                <!-- Thông tin lớp, môn, học kỳ -->
                <div class="alert alert-info mb-4">
                    <div class="row">
                        <div class="col-md-4"><strong>Lớp:</strong> ${classObj.className}</div>
                        <div class="col-md-4"><strong>Môn học:</strong> ${subject.subjectName}</div>
                        <div class="col-md-4"><strong>Học kỳ:</strong> ${yearSemesterID}</div>
                    </div>
                </div>

                <!-- Form nhập điểm -->
                <form method="post" action="${pageContext.request.contextPath}/admin/scores/saveBulk" id="scoreForm">
                    <input type="hidden" name="classID" value="${classID}">
                    <input type="hidden" name="subjectID" value="${subjectID}">
                    <input type="hidden" name="yearSemesterID" value="${yearSemesterID}">

                    <c:choose>
                        <c:when test="${empty studentsInClass}">
                            <div class="alert alert-warning text-center">
                                <i class="fas fa-exclamation-triangle fa-2x"></i>
                                <h5 class="mt-2">Không có học sinh nào trong lớp này!</h5>
                                <p>Vui lòng thêm học sinh vào lớp trước khi nhập điểm.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-borderless datatable">
                                    <thead>
                                        <tr>
                                            <th class="text-center">STT</th>
                                            <th class="text-center">Mã HS</th>
                                            <th class="text-center">Họ tên</th>
                                            <th class="text-center">Giới tính</th>
                                            <th class="text-center">Miệng 1</th>
                                            <th class="text-center">Miệng 2</th>
                                            <th class="text-center">15p1</th>
                                            <th class="text-center">15p2</th>
                                            <th class="text-center">Giữa kỳ</th>
                                            <th class="text-center">Cuối kỳ</th>
                                            <th class="text-center">TB</th>
                                            <th class="text-center">Xếp loại</th>
                                            <th class="text-center">Ghi chú</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${studentsInClass}" var="sc" varStatus="loop">
                                            <c:set var="student" value="${sc.student}" />
                                            <c:set var="score" value="${scoreMap[sc.studentID]}" />
                                            <tr>
                                                <td class="text-center">${loop.index + 1}</td>
                                                <td class="text-center">${sc.studentID}</td>
                                                <td>${student != null ? student.fullName : '<span class="text-danger">Không có thông tin</span>'}</td>
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${student.gender == 'M' or student.gender == 'Nam'}">
                                                            <span class="badge bg-primary">Nam</span>
                                                        </c:when>
                                                        <c:when test="${student.gender == 'F' or student.gender == 'Nữ'}">
                                                            <span class="badge bg-danger">Nữ</span>
                                                        </c:when>
                                                        <c:otherwise>${student.gender}</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <!-- Input điểm -->
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="oral1_${sc.studentClassID}" value="${score != null ? score.oralScore1 : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="oral2_${sc.studentClassID}" value="${score != null ? score.oralScore2 : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="s15_1_${sc.studentClassID}" value="${score != null ? score.score15Minute1 : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="s15_2_${sc.studentClassID}" value="${score != null ? score.score15Minute2 : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="mid_${sc.studentClassID}" value="${score != null ? score.midtermScore : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <input type="number" step="0.1" min="0" max="10" 
                                                           name="fin_${sc.studentClassID}" value="${score != null ? score.finalScore : ''}"
                                                           class="form-control form-control-sm score-input">
                                                </td>
                                                <td class="text-center">
                                                    <c:if test="${score != null && score.averageScore != null}">
                                                        <span class="badge ${score.averageScore >= 5 ? 'bg-success' : 'bg-danger'} fs-6">
                                                            ${score.averageScore}
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${score == null || score.averageScore == null}">
                                                        <span class="text-muted">-</span>
                                                    </c:if>
                                                </td>
                                                <td class="text-center">
                                                    <c:if test="${score != null && score.academicRating != null}">
                                                        <span class="badge ${score.academicRating == 'Giỏi' ? 'bg-success' :
                                                                        score.academicRating == 'Khá' ? 'bg-primary' :
                                                                        score.academicRating == 'Trung bình' ? 'bg-warning' :
                                                                        score.academicRating == 'Yếu' ? 'bg-danger' : 'bg-secondary'}">
                                                            ${score.academicRating}
                                                        </span>
                                                    </c:if>
                                                    <c:if test="${score == null || score.academicRating == null}">
                                                        <span class="text-muted">-</span>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="text" name="notes_${sc.studentClassID}" value="${score != null ? score.notes : ''}"
                                                           class="form-control form-control-sm" placeholder="Ghi chú">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <!-- Nút điều khiển -->
                            <div class="d-flex justify-content-between mt-3">
                                <a href="${pageContext.request.contextPath}/admin/scores?classID=${classID}&subjectID=${subjectID}&yearSemesterID=${yearSemesterID}" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left"></i> Trở lại
                                </a>
                                <button type="submit" class="btn btn-success" id="saveButton">
                                    <i class="fas fa-save"></i> Lưu tất cả điểm
                                </button>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </section>
</main>

<!-- DataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 10,
        "lengthMenu": [5,10,25,50,100],
        "order": [],
        "columnDefs": [ { "orderable": false, "targets": [4,5,6,7,8,9,10,11,12] } ]
    });

    // Kiểm tra điểm hợp lệ
    $('.score-input').on('input blur', function() {
        let val = parseFloat(this.value);
        if(this.value !== '' && (isNaN(val) || val < 0 || val > 10)){
            alert('Điểm phải từ 0 đến 10!');
            this.value = '';
        }
    });

    // Confirm khi submit
    $('#scoreForm').submit(function(e){
        if(!confirm('Bạn có chắc chắn muốn lưu tất cả điểm số? Hành động này sẽ ghi đè dữ liệu cũ.')){
            e.preventDefault();
        }else{
            $('#saveButton').html('<i class="fas fa-spinner fa-spin"></i> Đang lưu...').prop('disabled', true);
        }
    });
});
</script>
