<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <div>
            <h2 class="mb-1">Quản lý môn học theo khối lớp</h2>
            <p class="text-muted mb-0">Phân tách danh sách môn theo từng khối 6, 7, 8, 9 và cấu hình số tiết riêng.</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/subject" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Quay lại danh sách môn
        </a>
    </div>

     <div class="btn-group w-100 mb-3 flex-wrap" role="group" aria-label="Chọn khối lớp">
          <c:forEach var="grade" items="${grades}">
                <a href="${pageContext.request.contextPath}/admin/subject-grade?gradeID=${grade.gradeID}"
                    class="btn ${currentGrade == grade.gradeID ? 'btn-primary active' : 'btn-outline-primary'}">
                     Khối ${grade.gradeName}
                </a>
          </c:forEach>
     </div>

    <c:if test="${not empty flashSuccess}">
        <div class="alert alert-success">${flashSuccess}</div>
    </c:if>
    <c:if test="${not empty flashError}">
        <div class="alert alert-danger">${flashError}</div>
    </c:if>

    <section class="section dashboard">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h5 class="mb-1">Danh sách môn của khối ${currentGradeName}</h5>
                <div class="text-muted">Mỗi môn có thể cấu hình số tiết riêng cho khối hiện tại.</div>
            </div>
            <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addSubjectModal">
                <i class="bi bi-plus-lg me-1"></i> Thêm môn vào khối
            </button>
        </div>

        <div class="card recent-sales overflow-auto">
            <div class="card-body mt-4">
                <table class="table table-borderless table-hover align-middle">
                    <thead>
                        <tr>
                            <th class="text-center">STT</th>
                            <th>Môn học</th>
                            <th class="text-center">Số tiết</th>
                            <th class="text-center">Học kỳ</th>
                            <th class="text-center">Trạng thái</th>
                            <th class="text-center">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty assignedSubjects}">
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">Khối này chưa được gán môn nào.</td>
                            </tr>
                        </c:if>
                        <c:set var="stt" value="0" />
                        <c:forEach var="item" items="${assignedSubjects}">
                            <c:set var="stt" value="${stt + 1}" />
                            <tr>
                                <td class="text-center">${stt}</td>
                                <td>
                                    <div class="fw-semibold">${item.subjectName}</div>
                                    <div class="small text-muted">Mã môn: ${item.subjectID}</div>
                                </td>
                                <td class="text-center" style="max-width: 140px;">
                                    <form method="post" action="${pageContext.request.contextPath}/admin/subject-grade" class="d-flex gap-2 justify-content-center">
                                        <input type="hidden" name="action" value="save" />
                                        <input type="hidden" name="gradeID" value="${currentGrade}" />
                                        <input type="hidden" name="subjectID" value="${item.subjectID}" />
                                        <input type="number" name="periods" class="form-control form-control-sm text-center" min="1" value="${item.periods}" style="max-width: 100px;" />
                                        <button type="submit" class="btn btn-sm btn-success">Lưu</button>
                                    </form>
                                </td>
                                <td class="text-center">${item.semester}</td>
                                <td class="text-center">
                                    <span class="badge ${item.isActive ? 'bg-success' : 'bg-secondary'}">
                                        ${item.isActive ? 'Đang dùng' : 'Ẩn'}
                                    </span>
                                </td>
                                <td class="text-center">
                                    <form method="post" action="${pageContext.request.contextPath}/admin/subject-grade" onsubmit="return confirm('Xóa môn này khỏi khối ${currentGrade}?');" class="d-inline">
                                        <input type="hidden" name="action" value="delete" />
                                        <input type="hidden" name="gradeID" value="${currentGrade}" />
                                        <input type="hidden" name="subjectID" value="${item.subjectID}" />
                                        <button type="submit" class="btn btn-sm btn-danger">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </section>
</main>

<div class="modal fade" id="addSubjectModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Thêm môn vào khối ${currentGradeName}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <c:if test="${fallbackAllSubjects}">
                    <div class="alert alert-warning">Khối này hiện không còn môn chưa gán riêng. Danh sách dưới đây hiển thị toàn bộ môn học active để bạn thêm hoặc cập nhật số tiết.</div>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/admin/subject-grade" class="row g-3">
                    <input type="hidden" name="action" value="save" />
                    <input type="hidden" name="gradeID" value="${currentGrade}" />
                    <div class="col-md-8">
                        <label class="form-label">Chọn môn học</label>
                        <select name="subjectID" class="form-select" required>
                            <option value="">-- Chọn môn --</option>
                            <c:forEach var="subject" items="${unassignedSubjects}">
                                <option value="${subject.subjectID}">${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">Số tiết</label>
                        <input type="number" name="periods" class="form-control" min="1" required />
                    </div>
                    <div class="col-12 d-flex justify-content-end gap-2">
                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-primary">Lưu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
