<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <c:set var="currentTeacherId" value="${not empty degree ? degree.teacherID : selectedTeacherId}" />
    <div class="pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2">
        <h2 class="mb-0"><c:choose><c:when test="${mode == 'edit'}">Chỉnh sửa Bằng cấp</c:when><c:otherwise>Thêm Bằng cấp</c:otherwise></c:choose></h2>
        <a href="${pageContext.request.contextPath}/admin/teacher-degree<c:if test='${currentTeacherId > 0}'>?teacherID=${currentTeacherId}</c:if>" class="btn btn-secondary">Quay lại</a>
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
                        <form action="${pageContext.request.contextPath}/admin/teacher-degree" method="post" class="mt-3">
                            <input type="hidden" name="action" value="${mode}" />
                            <c:if test="${mode == 'edit'}">
                                <input type="hidden" name="id" value="${degree.degreeID}" />
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
                                    <label class="form-label">Tên bằng cấp</label>
                                    <input type="text" name="degreeName" value="${degree.degreeName}" class="form-control" placeholder="Ví dụ: Cử nhân Sư phạm Toán" required />
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Chuyên ngành</label>
                                    <input type="text" name="major" value="${degree.major}" class="form-control" placeholder="Ví dụ: Toán học" />
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Năm tốt nghiệp</label>
                                    <input type="number" name="graduationYear" value="${degree.graduationYear}" class="form-control" min="1950" max="2100" />
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Trường đào tạo</label>
                                    <input type="text" name="graduationSchool" value="${degree.graduationSchool}" class="form-control" />
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Tệp đính kèm</label>
                                    <input type="text" name="attachmentPath" value="${degree.attachmentPath}" class="form-control" placeholder="Đường dẫn file hoặc URL" />
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
