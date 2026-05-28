<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Lịch sử thay đổi điểm số</h1>
    </div>

    <section class="section dashboard">
        <style>
            .badge-update { background-color: #ffc107; color: #000; }
            .badge-insert { background-color: #28a745; color: #fff; }
            .content-cell {
                max-width: 420px;
                word-wrap: break-word;
                white-space: pre-wrap;
                font-size: 0.85rem;
            }
            .filter-card .form-label { font-weight: 600; }
            .dataTables_wrapper .dataTables_length,
            .dataTables_wrapper .dataTables_filter,
            .dataTables_wrapper .dataTables_info,
            .dataTables_wrapper .dataTables_paginate {
                margin-bottom: 15px;
                font-size: 0.9rem;
            }
        </style>

        <div class="card mb-4 filter-card">
            <div class="card-body mt-4">
                <form method="get" action="${pageContext.request.contextPath}/admin/scores/history">
                    <input type="hidden" name="action" value="viewHistory" />
                    <div class="row g-3 mb-3">
                        <div class="col-md-3">
                            <label class="form-label">Khối</label>
                            <select id="gradeSelect" name="gradeID" class="form-select">
                                <option value="">-- Chọn khối --</option>
                                <c:forEach var="grade" items="${grades}">
                                    <c:if test="${grade.isActive && (grade.gradeName == '6' || grade.gradeName == '7' || grade.gradeName == '8' || grade.gradeName == '9')}">
                                        <option value="${grade.gradeID}" ${gradeID == grade.gradeID ? 'selected' : ''}>
                                            Khối ${grade.gradeName}
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Lớp</label>
                            <select id="classSelect" name="classID" class="form-select">
                                <option value="">-- Chọn lớp --</option>
                                <c:forEach var="cls" items="${classes}">
                                    <option value="${cls.classID}" ${classID == cls.classID ? 'selected' : ''}>
                                        ${cls.gradeID}${cls.className}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Môn học</label>
                            <select id="subjectSelect" name="subjectID" class="form-select">
                                <option value="">-- Chọn môn --</option>
                                <c:forEach var="s" items="${subjects}">
                                    <option value="${s.subjectID}" ${subjectID == s.subjectID ? 'selected' : ''}>
                                        ${s.subjectName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Học kỳ</label>
                            <select name="yearSemesterID" class="form-select">
                                <option value="">-- Chọn học kỳ --</option>
                                <c:forEach var="ys" items="${yearSemesters}">
                                    <option value="${ys.yearSemesterID}" ${yearSemesterID == ys.yearSemesterID ? 'selected' : ''}>
                                        ${ys.semesterName} - ${ys.schoolYear}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end gap-2">
                        <a href="${pageContext.request.contextPath}/admin/scores/history" class="btn btn-outline-secondary">Làm mới</a>
                        <button type="submit" class="btn btn-primary">Xem lịch sử</button>
                    </div>
                </form>
            </div>
        </div>

        <c:choose>
            <c:when test="${showHistory}">
                <div class="card recent-sales overflow-auto">
                    <div class="card-body mt-4">
                        <div class="d-flex justify-content-end mb-3">
                            <a href="${pageContext.request.contextPath}/admin/scores" class="btn btn-secondary btn-sm">
                                <i class="bi bi-arrow-left"></i> Quay lại
                            </a>
                        </div>

                        <table class="table table-borderless datatable" id="historyTable">
                            <thead>
                                <tr>
                                    <th class="text-center">Thời gian</th>
                                    <th class="text-center">Người thay đổi</th>
                                    <th class="text-center">Học sinh</th>
                                    <th class="text-center">Môn / Kỳ</th>
                                    <th class="text-center">Hành động</th>
                                    <th class="text-center">Nội dung thay đổi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="log" items="${logs}">
                                    <tr>
                                        <td class="text-center">
                                            <fmt:formatDate value="${log.changeDate}" pattern="dd/MM/yyyy" /><br>
                                            <small class="text-muted"><fmt:formatDate value="${log.changeDate}" pattern="HH:mm:ss" /></small>
                                        </td>
                                        <td><i class="bi bi-person-badge"></i> ${log.teacherName}</td>
                                        <td><i class="bi bi-person"></i> ${log.studentName}</td>
                                        <td>
                                            <strong>${log.subjectName}</strong><br>
                                            <small class="text-info">${log.semesterName}</small>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${log.actionType == 'INSERT'}">
                                                    <span class="badge badge-insert">Nhập điểm</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-update">Sửa điểm</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="content-cell">${log.formattedChangeContent}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">Chọn bộ lọc và bấm <strong>Xem lịch sử</strong> để tải dữ liệu.</div>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<script>
const contextPath = '${pageContext.request.contextPath}';

function buildOptions($select, items, valueKey, labelBuilder, placeholder) {
    $select.empty();
    $select.append($('<option>', { value: '', text: placeholder }));
    items.forEach(function(item) {
        $select.append($('<option>', {
            value: item[valueKey],
            text: labelBuilder(item)
        }));
    });
}

function loadDependentDropdowns(gradeID, selectedClassID, selectedSubjectID) {
    const $classSelect = $('#classSelect');
    const $subjectSelect = $('#subjectSelect');

    if (!gradeID) {
        buildOptions($classSelect, [], 'classID', function() { return ''; }, '-- Chọn lớp --');
        buildOptions($subjectSelect, [], 'subjectID', function() { return ''; }, '-- Chọn môn --');
        return;
    }

    $.getJSON(contextPath + '/admin/api/classes', { gradeID: gradeID })
        .done(function(data) {
            buildOptions($classSelect, data, 'classID', function(item) {
                return item.gradeID + item.className;
            }, '-- Chọn lớp --');
            if (selectedClassID) {
                $classSelect.val(String(selectedClassID));
            }
        });

    $.getJSON(contextPath + '/admin/api/subjects', { gradeID: gradeID })
        .done(function(data) {
            buildOptions($subjectSelect, data, 'subjectID', function(item) {
                return item.subjectName;
            }, '-- Chọn môn --');
            if (selectedSubjectID) {
                $subjectSelect.val(String(selectedSubjectID));
            }
        });
}

$(document).ready(function() {
    const initialGradeID = $('#gradeSelect').val();
    const initialClassID = $('#classSelect').val();
    const initialSubjectID = $('#subjectSelect').val();

    $('#gradeSelect').on('change', function() {
        loadDependentDropdowns($(this).val(), '', '');
    });

    if (initialGradeID) {
        loadDependentDropdowns(initialGradeID, initialClassID, initialSubjectID);
    }

    if ($('#historyTable').length) {
        $('#historyTable').DataTable({
            pageLength: 10,
            lengthMenu: [5, 10, 25, 50],
            order: [[0, 'desc']],
            language: {
                search: 'Tìm kiếm:',
                lengthMenu: 'Hiển thị _MENU_ dòng',
                info: 'Đang hiển thị _START_ đến _END_ của _TOTAL_ dòng',
                paginate: {
                    first: 'Đầu',
                    last: 'Cuối',
                    next: 'Tiếp',
                    previous: 'Trước'
                },
                emptyTable: 'Chưa có dữ liệu lịch sử nào'
            },
            columnDefs: [
                { orderable: false, targets: [5] }
            ]
        });
    }
});
</script>