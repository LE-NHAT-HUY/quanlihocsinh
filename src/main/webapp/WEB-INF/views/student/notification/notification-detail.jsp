<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="container-fluid p-0">
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4 p-lg-5">
            <div class="d-flex justify-content-between align-items-start gap-3 flex-wrap mb-3">
                <div>
                    <div class="text-primary fw-bold text-uppercase small mb-2">Chi tiết thông báo</div>
                    <h2 class="fw-bold text-primary mb-2">${notification.title}</h2>
                    <div class="text-muted small">
                        <i class="bi bi-calendar3 me-1"></i>
                        <c:if test="${not empty notification.createdDate}">
                            <fmt:formatDate value="${notification.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                        </c:if>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/student/notifications" class="btn btn-outline-primary">
                    <i class="bi bi-arrow-left me-1"></i> Quay lại
                </a>
            </div>

            <div class="border-top pt-4" style="white-space: pre-wrap; line-height: 1.8;">
                ${notification.content}
            </div>

            <div class="mt-4 text-muted small">
                <i class="bi bi-person me-1"></i>${notification.senderFullName != null ? notification.senderFullName : notification.senderUsername}
                <span class="mx-2">|</span>
                <i class="bi bi-info-circle me-1"></i>${not empty notification.senderDepartment ? notification.senderDepartment : 'Chưa cập nhật'}
            </div>
        </div>
    </div>

    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            <h5 class="fw-bold mb-3">Tài liệu đính kèm</h5>
            <div class="table-responsive">
                <table class="table align-middle table-hover">
                    <thead class="table-light">
                        <tr>
                            <th style="width: 70px;">TT</th>
                            <th>Tên tài liệu</th>
                            <th style="width: 150px;">Kích thước</th>
                            <th style="width: 150px;">Chức năng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty attachments}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted py-4">Chưa có tài liệu đính kèm</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:set var="stt" value="0" />
                                <c:forEach var="file" items="${attachments}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${fn:endsWith(fn:toLowerCase(file.fileName), '.pdf')}">
                                                    <i class="bi bi-file-earmark-pdf-fill text-danger me-2"></i>
                                                </c:when>
                                                <c:when test="${fn:endsWith(fn:toLowerCase(file.fileName), '.doc') or fn:endsWith(fn:toLowerCase(file.fileName), '.docx')}">
                                                    <i class="bi bi-file-earmark-word-fill text-primary me-2"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="bi bi-file-earmark-text text-secondary me-2"></i>
                                                </c:otherwise>
                                            </c:choose>
                                            ${file.fileName}
                                        </td>
                                        <td class="text-center">${file.fileSizeKB != null ? file.fileSizeKB : 0} KB</td>
                                        <td class="text-center">
                                            <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/student/notifications/download?attachmentId=${file.attachmentID}">
                                                <i class="bi bi-download me-1"></i> Tải xuống
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>