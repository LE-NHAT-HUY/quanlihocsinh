<%-- filepath: c:\Users\LENOVO\Projects\quanlyhocsinh\src\main\webapp\WEB-INF\views\admin\studentclass\index.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách học sinh theo lớp</h2>
    </div>

    <section class="section dashboard">
        <!-- Bộ lọc lớp học -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/admin/studentclass/list" class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <select name="classID" class="form-select" onchange="this.form.submit()">
                            <option value="">-- Tất cả lớp --</option>
                            <c:forEach var="cls" items="${classes}">
                                <option value="${cls.classID}" ${classID == cls.classID ? 'selected' : ''}>
                                    ${cls.gradeID}${cls.className} (${cls.currentStudents}/${cls.maxStudents})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 d-flex gap-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search me-1"></i> Lọc
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card recent-sales overflow-auto">
            <div class="card-header bg-white border-bottom">
                <div class="d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">
                        <i class="bi bi-people me-2 text-primary"></i>
                        Danh sách học sinh
                        <c:if test="${classID > 0}">
                            <c:forEach var="cls" items="${classes}">
                                <c:if test="${cls.classID == classID}">
                                    <span class="badge bg-info ms-2">${cls.gradeID}${cls.className}</span>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </h5>
                    <c:if test="${classID > 0}">
                        <a href="${pageContext.request.contextPath}/admin/studentclass/add?classID=${classID}"
                           class="btn btn-primary btn-sm">
                            <i class="bi bi-person-plus me-1"></i> Thêm học sinh
                        </a>
                    </c:if>
                </div>
            </div>

            <div class="card-body p-0">
                <div class="table-responsive">
                    <table class="table table-borderless mb-0 datatable">
                        <thead>
                            <tr>
                                <th class="text-center">STT</th>
                                <th>Mã học sinh</th>
                                <th>Họ và tên</th>
                                <th>Giới tính</th>
                                <th>Ngày sinh</th>
                                <th class="text-center">Trạng thái</th>
                                <th class="text-center" style="width: 150px;">Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sc" items="${studentsInClass}" varStatus="loop">
                                <tr>
                                    <td class="text-center">${loop.index + 1}</td>
                                    <td><strong>${sc.studentID}</strong></td>
                                    <td>${sc.student.fullName}</td>
                                    <td>${sc.student.gender}</td>
                                    <td><fmt:formatDate value="${sc.student.birth}" pattern="dd/MM/yyyy"/></td>
                                    <td class="text-center">
                                        <span class="badge ${sc.active ? 'bg-success' : 'bg-secondary'}">
                                            ${sc.active ? 'Đang học' : 'Nghỉ'}
                                        </span>
                                    </td>
                                    <td class="text-center">
                                        <div class="d-flex gap-1 justify-content-center">
                                            <!-- NÚT CHUYỂN LỚP -->
                                            <button type="button" class="btn btn-warning btn-sm" 
                                                    data-bs-toggle="modal" 
                                                    data-bs-target="#transferModal-${sc.studentID}" 
                                                    title="Chuyển lớp">
                                                <i class="bi bi-arrow-left-right"></i>
                                            </button>

                                            <!-- NÚT XÓA -->
                                            <a href="${pageContext.request.contextPath}/admin/studentclass/delete?studentClassID=${sc.studentClassID}&classID=${sc.classID}&yearSemesterID=${yearSemesterID}" 
                                               class="btn btn-danger btn-sm" 
                                               onclick="return confirm('Xóa học sinh này khỏi lớp?')">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </div>

                                        <!-- MODAL CHUYỂN LỚP (Phải nằm trong vòng lặp) -->
                                        <div class="modal fade" id="transferModal-${sc.studentID}" tabindex="-1" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <form method="post" action="${pageContext.request.contextPath}/admin/studentclass/transfer">
                                                    <input type="hidden" name="studentId" value="${sc.studentID}"/>
                                                    <input type="hidden" name="fromClassId" value="${classID}"/>
                                                    <input type="hidden" name="yearSemesterID" value="${yearSemesterID}"/>
                                                    
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title text-dark">Chuyển lớp: ${sc.student.fullName}</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                        </div>
                                                        <div class="modal-body text-start">
                                                            <div class="mb-3">
                                                                <label class="form-label">Chọn lớp cùng khối (${classID})</label>
                                                                <select name="toClassId" class="form-select" required>
                                                                    <option value="">-- Chọn lớp đến --</option>
                                                                    <%-- ĐỔI: items="${classes}" THÀNH items="${transferableClasses}" --%>
                                                                    <c:forEach var="c" items="${transferableClasses}">
                                                                        <option value="${c.classID}">
                                                                            ${c.gradeID}${c.className} (Sĩ số: ${c.currentStudents}/${c.maxStudents})
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="submit" class="btn btn-primary">Xác nhận chuyển</button>
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </section>
</main>