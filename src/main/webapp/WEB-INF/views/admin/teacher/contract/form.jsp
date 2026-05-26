<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <c:set var="currentTeacherId" value="${not empty contract ? contract.teacherID : selectedTeacherId}" />
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0"><c:choose><c:when test="${mode == 'edit'}">Chỉnh sửa Hợp đồng</c:when><c:otherwise>Thêm Hợp đồng</c:otherwise></c:choose></h2>
        <a href="${pageContext.request.contextPath}/admin/teacher-contract<c:if test='${currentTeacherId > 0}'>?teacherID=${currentTeacherId}</c:if>" class="btn btn-secondary">Quay lại</a>
    </div>

    <c:if test="${not empty flashSuccess}">
        <div class="alert alert-success">${flashSuccess}</div>
    </c:if>
    <c:if test="${not empty flashError}">
        <div class="alert alert-danger">${flashError}</div>
    </c:if>

    <section class="section">
        <div class="row">
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/admin/teacher-contract" method="post" class="mt-3">
                            <input type="hidden" name="action" value="${mode}" />
                            <c:if test="${mode == 'edit'}">
                                <input type="hidden" name="id" value="${contract.contractID}" />
                            </c:if>

                            <div class="row g-3">
                                <div class="col-md-6">
                                    <label class="form-label">Giáo viên</label>
                                    <select name="teacherID" class="form-select" required>
                                        <option value="">-- Chọn giáo viên --</option>
                                        <c:forEach var="teacher" items="${teachers}">
                                            <option value="${teacher.id}" ${teacher.id == currentTeacherId ? 'selected' : ''}>${teacher.fullName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Số hợp đồng</label>
                                    <input type="text" name="contractNumber" value="${contract.contractNumber}" class="form-control" required />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Loại hợp đồng</label>
                                    <input type="text" name="contractType" value="${contract.contractType}" class="form-control" placeholder="Ví dụ: Hợp đồng 12 tháng" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Ngày ký</label>
                                    <input type="date" name="signDate" value="${contract.signDate}" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Ngày hiệu lực</label>
                                    <input type="date" name="startDate" value="${contract.startDate}" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Ngày hết hạn</label>
                                    <input type="date" name="endDate" value="${contract.endDate}" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Hệ số lương</label>
                                    <input type="number" step="0.01" name="salaryCoefficient" value="${contract.salaryCoefficient}" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Lương cơ bản</label>
                                    <input type="number" step="0.01" name="baseSalary" value="${contract.baseSalary}" class="form-control" />
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Trạng thái</label>
                                    <input type="text" name="contractStatus" value="${contract.contractStatus}" class="form-control" placeholder="Hiệu lực / Hết hạn / Tạm dừng" />
                                </div>
                            </div>

                            <div class="mt-4">
                                <button type="submit" class="btn btn-primary">${mode == 'edit' ? 'Cập nhật' : 'Thêm mới'}</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
