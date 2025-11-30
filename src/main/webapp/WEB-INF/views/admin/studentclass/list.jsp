<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">
  <div class="pagetitle">
    <h2>Danh sách học sinh trong lớp</h2>
    <a href="${pageContext.request.contextPath}/admin/student-class/add?classID=${classID}&yearSemesterID=${yearSemesterID}" 
       class="btn btn-success mb-2">
      <i class="bi bi-plus-circle"></i> Thêm học sinh
    </a>
  </div>

  <section class="section dashboard">
    <div class="card recent-sales overflow-auto">
      <div class="card-body mt-4">
        <table class="table table-borderless datatable">
          <thead>
            <tr>
              <th class="text-center">STT</th>
              <th class="text-center">ID</th>
              <th class="text-center">Mã HS</th>
              <th class="text-center">Tên lớp</th>
              <th class="text-center">Khoá</th>
              <th class="text-center">Hoạt động</th>
              <th class="text-center">Chức năng</th>
            </tr>
          </thead>
          <tbody>
            <c:set var="stt" value="0"/>
            <c:forEach var="s" items="${studentsInClass}">
              <c:set var="stt" value="${stt + 1}" />
              <tr>
                <td class="text-center">${stt}</td>
                <td class="text-center">${s.studentClassID}</td>
                <td class="text-center">${s.studentID}</td>
                <td class="text-center">${s.className}</td> <!-- className phải được join trong DAO nếu muốn hiển thị -->
                <td class="text-center">${s.cohortID}</td>
                <td class="text-center">
                  <input type="checkbox" disabled ${s.active ? "checked" : ""} />
                </td>
                <td class="text-center">
                  <a href="${pageContext.request.contextPath}/admin/student-class/delete?studentClassID=${s.studentClassID}&classID=${classID}&yearSemesterID=${yearSemesterID}" 
                     class="btn btn-danger btn-sm"
                     onclick="return confirm('Bạn có chắc muốn xóa học sinh này khỏi lớp?');">
                    <i class="bi bi-trash"></i>
                  </a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </section>
</main>

<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<script>
$(document).ready(function() {
  $('.datatable').DataTable({
    "pageLength": 10,
    "lengthMenu": [5,10,25,50,100],
    "order": [],
    "columnDefs": [ { "orderable": false, "targets": [5,6] } ]
  });
});
</script>
