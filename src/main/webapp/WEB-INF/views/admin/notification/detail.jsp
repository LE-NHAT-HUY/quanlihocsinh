<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle d-flex justify-content-between align-items-center">
        <div>
            <h2>${notification.title}</h2>
            <p class="text-muted mb-0">
                <c:if test="${not empty notification.createdDate}">
                    <fmt:formatDate value="${notification.createdDate}" pattern="dd/MM/yyyy HH:mm" />
                </c:if>
            </p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/notifications" class="btn btn-secondary">Quay lại</a>
    </div>

    <section class="section dashboard">
        <div class="card">
            <div class="card-body mt-3">
                <div class="mb-3">
                    <div class="text-muted small mb-2">
                        Người gửi: ${notification.senderFullName != null ? notification.senderFullName : notification.senderUsername}
                    </div>
                    <div class="text-muted small mb-2">
                        Đơn vị gửi: ${not empty notification.senderDepartment ? notification.senderDepartment : 'Chưa cập nhật'}
                    </div>
                    <div class="mb-3" style="white-space: pre-wrap;">
                        ${notification.content}
                    </div>
                </div>

                <c:if test="${not empty attachments}">
                    <h5 class="mt-4 mb-3">Tài liệu đính kèm</h5>
                    <div class="table-responsive">
                        <table class="table table-bordered align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th style="width: 70px;">TT</th>
                                    <th>Tên tài liệu</th>
                                    <th style="width: 140px;">Kích thước</th>
                                    <th style="width: 140px;">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0" />
                                <c:forEach var="file" items="${attachments}">
                                    <c:set var="stt" value="${stt + 1}" />
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td>
                                            <i class="bi bi-file-earmark-text me-2 text-primary"></i>
                                            ${file.fileName}
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty file.fileSizeKB}">${file.fileSizeKB} KB</c:when>
                                                <c:otherwise>0 KB</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/notifications/download?attachmentId=${file.attachmentID}">
                                                <i class="bi bi-download"></i> Download
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </section>
</main>
