<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid p-0">

    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #FF9966 0%, #FF5E62 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-1">
                    <i class="bi bi-trophy me-2"></i>Thành tích học tập
                </h2>
                <p class="mb-0 opacity-75">Bảng điểm cá nhân của học sinh <strong>${student.fullName}</strong>.</p>
            </div>
            <div class="col-md-4 text-end">
                <i class="bi bi-graph-up-arrow fs-1 opacity-50"></i>
            </div>
        </div>
    </div>

    <section class="section dashboard">
        
        <div class="card shadow-sm mb-4">
            <div class="card-body pt-4">
                <form method="get" action="${pageContext.request.contextPath}/student/grades">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label fw-bold small text-muted">Lớp học hiện tại</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light text-primary"><i class="bi bi-door-open-fill"></i></span>
                                <input type="text" class="form-control fw-bold text-primary bg-white" 
                                       value="${currentClass != null ? currentClass.className : 'Chưa xếp lớp'}" readonly>
                            </div>
                        </div>

                        <div class="col-md-4">
                            <label class="form-label fw-bold small text-muted">Học kỳ / Năm học</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="bi bi-calendar-event"></i></span>
                                <select name="yearSemesterID" class="form-select" onchange="this.form.submit()">
                                    <c:forEach var="ys" items="${yearSemesters}">
                                        <option value="${ys.yearSemesterID}" <c:if test="${ys.yearSemesterID == yearSemesterID}">selected</c:if>>
                                            ${ys.semesterName} - ${ys.schoolYear}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="alert alert-light border mb-0 py-2 small">
                                <i class="bi bi-info-circle me-1"></i>
                                MSSV: <strong>${student.studentID}</strong>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header bg-white border-bottom py-3">
                <h5 class="card-title mb-0 text-success">
                    <i class="bi bi-table me-2"></i>Bảng điểm chi tiết
                </h5>
            </div>
            <div class="card-body mt-3">
                <c:choose>
                    <c:when test="${empty transcript}">
                        <div class="text-center py-5">
                            <img src="https://cdn-icons-png.flaticon.com/512/7486/7486744.png" width="100" class="mb-3 opacity-50">
                            <p class="text-muted">Chưa có dữ liệu điểm cho học kỳ này.</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered align-middle">
                                <thead class="table-light text-center">
                                    <tr>
                                        <th style="width: 50px;">STT</th>
                                        <th class="text-start">Môn học</th>
                                        <th>Miệng 1</th>
                                        <th>Miệng 2</th>
                                        <th>15p (1)</th>
                                        <th>15p (2)</th>
                                        <th>Giữa kỳ</th>
                                        <th>Cuối kỳ</th>
                                        <th class="table-primary">Trung bình</th>
                                        <th>Xếp loại</th>
                                        <th>Ghi chú</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="s" items="${transcript}" varStatus="loop">
                                        <tr>
                                            <td class="text-center text-muted fw-bold">${loop.index + 1}</td>
                                            <td class="fw-bold text-primary">${s.subjectName}</td>
                                            
                                            <td class="text-center">${s.oralScore1 != null ? s.oralScore1 : '-'}</td>
                                            <td class="text-center">${s.oralScore2 != null ? s.oralScore2 : '-'}</td>
                                            <td class="text-center">${s.score15Minute1 != null ? s.score15Minute1 : '-'}</td>
                                            <td class="text-center">${s.score15Minute2 != null ? s.score15Minute2 : '-'}</td>
                                            <td class="text-center fw-semibold">${s.midtermScore != null ? s.midtermScore : '-'}</td>
                                            <td class="text-center fw-bold text-success">${s.finalScore != null ? s.finalScore : '-'}</td>
                                            
                                            <td class="text-center table-primary fw-bold">
                                                <c:if test="${s.averageScore != null}">
                                                    <span class="${s.averageScore >= 5.0 ? 'text-success' : 'text-danger'}">
                                                        ${s.averageScore}
                                                    </span>
                                                </c:if>
                                                <c:if test="${s.averageScore == null}">-</c:if>
                                            </td>
                                            
                                            <td class="text-center">
                                                <c:if test="${s.academicRating != null}">
                                                    <span class="badge ${s.academicRating == 'Giỏi' ? 'bg-success' : 
                                                                        s.academicRating == 'Khá' ? 'bg-primary' : 
                                                                        s.academicRating == 'Trung bình' ? 'bg-warning text-dark' : 'bg-danger'}">
                                                        ${s.academicRating}
                                                    </span>
                                                </c:if>
                                            </td>
                                            
                                            <td class="small text-muted fst-italic">${s.notes}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </section>
</div>