<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
    <div class="pagetitle">
        <h2>Danh sách điểm theo lớp học - môn học - học kỳ</h2>
    </div>

    <section class="section dashboard">
        <form method="get" action="${pageContext.request.contextPath}/admin/scores">
            <div class="row mb-3">
                <div class="col-md-3">
                    <label class="form-label">Lớp</label>
                    <select name="classID" class="form-select" required>
                        <option value="">-- Chọn lớp --</option>
                        <c:forEach var="cls" items="${classes}">
                                <option value="${cls.classID}" ${classID == cls.classID ? 'selected' : ''}>
                                    ${cls.gradeID}${cls.className} (${cls.currentStudents}/${cls.maxStudents})
                                </option>
                            </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Môn học</label>
                    <select name="subjectID" class="form-select" required>
                        <option value="">-- Chọn môn --</option>
                        <c:forEach var="s" items="${subjects}">
                            <option value="${s.subjectID}" <c:if test="${s.subjectID == subjectID}">selected</c:if>>
                                ${s.subjectName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Học kỳ</label>
                    <select name="yearSemesterID" class="form-select" required>
                        <option value="">-- Chọn học kỳ --</option>
                        <c:forEach var="ys" items="${yearSemesters}">
                            <option value="${ys.yearSemesterID}" <c:if test="${ys.yearSemesterID == yearSemesterID}">selected</c:if>>
                                ${ys.semesterName} - ${ys.schoolYear}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3 align-self-end">
                    <button class="btn btn-primary">Xem</button>
                    <c:if test="${classID > 0 && subjectID > 0 && yearSemesterID > 0}">
                        <a class="btn btn-success"
                           href="${pageContext.request.contextPath}/admin/scores/add?classID=${classID}&subjectID=${subjectID}&yearSemesterID=${yearSemesterID}">
                           Thêm/Sửa điểm
                        </a>
                    </c:if>
                </div>
            </div>
        </form>

        <c:if test="${not empty studentsInClass}">
            <div class="card recent-sales overflow-auto">
                <div class="card-body mt-4">
                    <table class="table table-borderless datatable">
                        <thead>
                            <tr>
                                <th class="text-center">STT</th>
                                <th class="text-center">Mã HS</th>
                                <th class="text-center">Họ tên</th>
                                <th class="text-center">Miệng1</th>
                                <th class="text-center">Miệng2</th>
                                <th class="text-center">15p1</th>
                                <th class="text-center">15p2</th>
                                <th class="text-center">Giữa kỳ</th>
                                <th class="text-center">Cuối kỳ</th>
                                <th class="text-center">TB</th>
                                <th class="text-center">Xếp loại</th>
                                <th class="text-center">Ghi chú</th>
                                <th class="text-center">Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="stt" value="0"/>
                            <c:forEach var="st" items="${studentsInClass}">
                                <c:set var="stt" value="${stt + 1}" />
                                <tr>
                                    <td class="text-center">${stt}</td>
                                    <td class="text-center">${st.student.studentID}</td>
                                    <td class="text-center">${st.student.fullName}</td>

                                    <c:set var="sc" value="${scoreMap[st.student.studentID]}" />

                                    <td class="text-center">${sc != null ? sc.oralScore1 : ''}</td>
                                    <td class="text-center">${sc != null ? sc.oralScore2 : ''}</td>
                                    <td class="text-center">${sc != null ? sc.score15Minute1 : ''}</td>
                                    <td class="text-center">${sc != null ? sc.score15Minute2 : ''}</td>
                                    <td class="text-center">${sc != null ? sc.midtermScore : ''}</td>
                                    <td class="text-center">${sc != null ? sc.finalScore : ''}</td>
                                    <td class="text-center">${sc != null ? sc.averageScore : ''}</td>
                                    <td class="text-center">${sc != null ? sc.academicRating : ''}</td>
                                    <td class="text-center">${sc != null ? sc.notes : ''}</td>
                                    <td class="text-center">
                                        <c:if test="${sc != null}">
                                            <form method="post" action="${pageContext.request.contextPath}/admin/scores/delete" style="display:inline">
                                                <input type="hidden" name="scoreID" value="${sc.scoreID}" />
                                                <input type="hidden" name="classID" value="${classID}" />
                                                <input type="hidden" name="subjectID" value="${subjectID}" />
                                                <input type="hidden" name="yearSemesterID" value="${yearSemesterID}" />
                                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Xóa điểm này?')">Xóa</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
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
        "columnDefs": [ { "orderable": false, "targets": [12] } ]
    });
});
</script>
