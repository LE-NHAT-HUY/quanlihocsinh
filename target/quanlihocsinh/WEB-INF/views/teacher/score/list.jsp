<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="container-fluid p-0">
    
    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-1">
                    <i class="bi bi-journal-check me-2"></i>Lớp giảng dạy - Bảng điểm
                </h2>
                <p class="mb-0 opacity-75">Xem và quản lý bảng điểm chi tiết của từng lớp học.</p>
            </div>
            <div class="col-md-4 text-end">
                <i class="bi bi-bar-chart-steps fs-1 opacity-50"></i>
            </div>
        </div>
    </div>

    <section class="section dashboard">
        
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-white border-bottom py-3">
                <h5 class="card-title mb-0 text-primary">
                    <i class="bi bi-funnel me-2"></i>Bộ lọc tìm kiếm
                </h5>
            </div>
            <div class="card-body pt-4">
                <form method="get" action="${pageContext.request.contextPath}/teacher/scores">
                    <div class="row g-3">
                        <div class="col-md-3">
                            <label class="form-label fw-bold small text-muted">Lớp học</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="bi bi-door-open"></i></span>
                                <select name="classID" class="form-select" required>
                                    <option value="">-- Chọn lớp --</option>
                                    <c:forEach var="cls" items="${classes}">
                                        <option value="${cls.classID}" ${classID == cls.classID ? 'selected' : ''}>
                                            ${cls.gradeID}${cls.className} (${cls.currentStudents}/${cls.maxStudents})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small text-muted">Môn học</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="bi bi-book"></i></span>
                                <select name="subjectID" class="form-select" required>
                                    <option value="">-- Chọn môn --</option>
                                    <c:forEach var="s" items="${subjects}">
                                        <option value="${s.subjectID}" <c:if test="${s.subjectID == subjectID}">selected</c:if>>
                                            ${s.subjectName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label fw-bold small text-muted">Học kỳ</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="bi bi-calendar-event"></i></span>
                                <select name="yearSemesterID" class="form-select" required>
                                    <option value="">-- Chọn học kỳ --</option>
                                    <c:forEach var="ys" items="${yearSemesters}">
                                        <option value="${ys.yearSemesterID}" <c:if test="${ys.yearSemesterID == yearSemesterID}">selected</c:if>>
                                            ${ys.semesterName} - ${ys.schoolYear}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-3 d-flex align-items-end">
                            <div class="d-grid gap-2 w-100 d-md-flex">
                                <button class="btn btn-primary flex-grow-1">
                                    <i class="bi bi-search me-1"></i> Xem điểm
                                </button>
                                <c:if test="${classID > 0 && subjectID > 0 && yearSemesterID > 0}">
                                    <a class="btn btn-success flex-grow-1"
                                       href="${pageContext.request.contextPath}/teacher/scores/add?classID=${classID}&subjectID=${subjectID}&yearSemesterID=${yearSemesterID}">
                                       <i class="bi bi-pencil-square me-1"></i> Nhập điểm
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty studentsInClass}">
            <div class="card shadow-sm">
                <div class="card-header bg-white border-bottom py-3 d-flex justify-content-between align-items-center">
                    <h5 class="card-title mb-0 text-success">
                        <i class="bi bi-table me-2"></i>Bảng điểm chi tiết
                    </h5>
                    <span class="badge bg-light text-dark border">
                        <i class="bi bi-people me-1"></i>Sĩ số: ${studentsInClass.size()}
                    </span>
                </div>
                <div class="card-body mt-3">
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered datatable align-middle">
                            <thead class="table-light text-center">
                                <tr>
                                    <th>STT</th>
                                    <th>Mã HS</th>
                                    <th>Họ tên</th>
                                    <th>Miệng 1</th>
                                    <th>Miệng 2</th>
                                    <th>15p (1)</th>
                                    <th>15p (2)</th>
                                    <th>Giữa kỳ</th>
                                    <th>Cuối kỳ</th>
                                    <th>T.Bình</th>
                                    <th>Xếp loại</th>
                                    <th>Ghi chú</th>
                                    <th>Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="st" items="${studentsInClass}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <c:set var="sc" value="${scoreMap[st.student.studentID]}" />
                                    <tr>
                                        <td class="text-center text-muted fw-bold">${stt}</td>
                                        <td class="text-center text-primary fw-semibold">${st.student.studentID}</td>
                                        <td class="fw-bold">${st.student.fullName}</td>

                                        <td class="text-center">${sc != null ? sc.oralScore1 : '-'}</td>
                                        <td class="text-center">${sc != null ? sc.oralScore2 : '-'}</td>
                                        <td class="text-center">${sc != null ? sc.score15Minute1 : '-'}</td>
                                        <td class="text-center">${sc != null ? sc.score15Minute2 : '-'}</td>
                                        <td class="text-center fw-semibold text-primary">${sc != null ? sc.midtermScore : '-'}</td>
                                        <td class="text-center fw-bold text-success">${sc != null ? sc.finalScore : '-'}</td>
                                        
                                        <td class="text-center">
                                            <c:if test="${sc != null && sc.averageScore != null}">
                                                <span class="badge ${sc.averageScore >= 5.0 ? 'bg-success' : 'bg-danger'} rounded-pill">
                                                    ${sc.averageScore}
                                                </span>
                                            </c:if>
                                            <c:if test="${sc == null || sc.averageScore == null}">-</c:if>
                                        </td>
                                        
                                        <td class="text-center">
                                            <c:if test="${sc != null && sc.academicRating != null}">
                                                <span class="badge ${sc.academicRating == 'Giỏi' ? 'bg-success' :
                                                                    sc.academicRating == 'Khá' ? 'bg-primary' :
                                                                    sc.academicRating == 'Trung bình' ? 'bg-warning text-dark' :
                                                                    'bg-danger'}">
                                                    ${sc.academicRating}
                                                </span>
                                            </c:if>
                                        </td>
                                        
                                        <td class="small text-muted fst-italic text-center">${sc != null ? sc.notes : ''}</td>
                                        
                                        <td class="text-center">
                                            <c:if test="${sc != null}">
                                                <form method="post" action="${pageContext.request.contextPath}/teacher/scores/delete" style="display:inline">
                                                    <input type="hidden" name="scoreID" value="${sc.scoreID}" />
                                                    <input type="hidden" name="classID" value="${classID}" />
                                                    <input type="hidden" name="subjectID" value="${subjectID}" />
                                                    <input type="hidden" name="yearSemesterID" value="${yearSemesterID}" />
                                                    <button type="submit" class="btn btn-sm btn-outline-danger border-0" onclick="return confirm('Bạn có chắc muốn xóa điểm này?')" title="Xóa điểm">
                                                        <i class="bi bi-trash"></i>
                                                    </button>
                                                </form>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty studentsInClass && (empty classID || classID == 0)}">
             <div class="text-center py-5 opacity-50">
                <i class="bi bi-search fs-1 mb-3 d-block"></i>
                <p>Vui lòng chọn Lớp, Môn học và Học kỳ để xem bảng điểm.</p>
            </div>
        </c:if>
    </section>
</div>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
    $('.datatable').DataTable({
        "pageLength": 25,
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/vi.json"
        },
        "columnDefs": [ { "orderable": false, "targets": [12] } ]
    });
});
</script>