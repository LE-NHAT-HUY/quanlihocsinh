<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách điểm</h2>
        <a href="${pageContext.request.contextPath}/admin/scores/add" class="btn btn-success mb-2">
            <i class="bi bi-plus-circle"></i> Thêm mới
        </a>
    </div>

    <section class="section dashboard">
        <div class="row">
            <div class="col-12">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">

                        <table class="table table-borderless datatable">
                            <thead>
                                <tr>
                                    <th class="text-center">STT</th>
                                    <th class="text-center">Mã điểm</th>
                                    <th class="text-center">Mã học sinh</th>
                                    <th class="text-center">Mã môn học</th>
                                    <th class="text-center">Điểm trung bình</th>
                                    <th class="text-center">Xếp loại</th>
                                    <th class="text-center">Ngày tạo</th>
                                    <th class="text-center">Trạng thái</th>
                                    <th class="text-center">Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="stt" value="0"/>
                                <c:forEach var="s" items="${scores}">
                                    <c:set var="stt" value="${stt + 1}"/>
                                    <tr>
                                        <td class="text-center">${stt}</td>
                                        <td class="text-center">${s.scoreID}</td>
                                        <td class="text-center">${s.studentID}</td>
                                        <td class="text-center">${s.subjectID}</td>
                                        <td class="text-center">
                                            <c:out value="${s.averageScore}" />
                                        </td>
                                        <td class="text-center">
                                            <c:out value="${s.academicRating}" />
                                        </td>
                                        <td class="text-center">
                                            <fmt:formatDate value="${s.createDate}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${s.active}">
                                                    <span class="badge bg-success">Hoạt động</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary">Ngưng</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/admin/scores/edit?id=${s.scoreID}"
                                               class="btn btn-primary btn-sm">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- DataTables -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<script>
$(document).ready(function () {
    $('.datatable').DataTable({
        pageLength: 10,
        lengthMenu: [5, 10, 25, 50, 100],
        order: [],
        columnDefs: [
            { orderable: false, targets: [8] }
        ],
        language: {
            search: "Tìm kiếm:",
            lengthMenu: "Hiển thị _MENU_ dòng",
            info: "Hiển thị _START_ đến _END_ của _TOTAL_ dòng",
            paginate: {
                previous: "Trước",
                next: "Sau"
            }
        }
    });
});
</script>
