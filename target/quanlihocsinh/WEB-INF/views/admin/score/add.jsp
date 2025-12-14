<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main class="main">
  <div class="pagetitle">
    <h2>Thêm điểm - Lớp: <c:out value="${classID}" /></h2>
  </div>

  <section class="section">
    <form method="post" action="${pageContext.request.contextPath}/scores/saveBulk">
      <input type="hidden" name="classID" value="${classID}" />
      <input type="hidden" name="yearSemesterID" value="${yearSemesterID}" />

      <div class="mb-3">
        <label>Môn</label>
        <select name="subjectID" class="form-select" required>
          <option value="">-- Chọn môn --</option>
          <c:forEach var="sub" items="${subjects}">
            <option value="${sub.subjectID}">${sub.subjectName}</option>
          </c:forEach>
        </select>
      </div>

      <div class="table-responsive">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>STT</th><th>Mã HS</th><th>Họ tên</th>
              <th>Oral1</th><th>Oral2</th><th>15'1</th><th>15'2</th>
              <th>Mid</th><th>Final</th><th>Average</th><th>Rating</th><th>Ghi chú</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="sc" items="${studentsInClass}" varStatus="loop">
              <tr>
                <td>${loop.index + 1}</td>
                <td><strong>${sc.student.studentID}</strong></td>
                <td>${sc.student.fullName}</td>

                <!-- dùng studentClassID làm hậu tố để dễ parse -->
                <td><input class="form-control input-small" name="oral1_${sc.studentClassID}" id="oral1_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" name="oral2_${sc.studentClassID}" id="oral2_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" name="s15_1_${sc.studentClassID}" id="s15_1_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" name="s15_2_${sc.studentClassID}" id="s15_2_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" name="mid_${sc.studentClassID}" id="mid_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" name="fin_${sc.studentClassID}" id="fin_${sc.studentClassID}" /></td>

                <td><input class="form-control input-small" readonly id="avg_${sc.studentClassID}" /></td>
                <td><input class="form-control input-small" readonly id="rate_${sc.studentClassID}" /></td>

                <td><input class="form-control" name="notes_${sc.studentClassID}" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>

      <div class="mt-3">
        <button type="submit" class="btn btn-primary">Lưu tất cả</button>
        <a href="${pageContext.request.contextPath}/scores?classID=${classID}&yearSemesterID=${yearSemesterID}" class="btn btn-secondary">Hủy</a>
      </div>
    </form>
  </section>
</main>

<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<script>
  // Khi bất kỳ input điểm thay đổi -> gọi ajax để tính average+rating (sử dụng /scores/ajaxCalc)
  function setupCalcFor(idSuffix) {
    ['oral1','oral2','s15_1','s15_2','mid','fin'].forEach(function(prefix){
      var el = $('#'+ prefix + '_' + idSuffix);
      el.on('input', function(){
        var payload = {
          OralScore1: parseFloat($('#oral1_' + idSuffix).val()) || null,
          OralScore2: parseFloat($('#oral2_' + idSuffix).val()) || null,
          Score15Minute1: parseFloat($('#s15_1_' + idSuffix).val()) || null,
          Score15Minute2: parseFloat($('#s15_2_' + idSuffix).val()) || null,
          MidtermScore: parseFloat($('#mid_' + idSuffix).val()) || null,
          FinalScore: parseFloat($('#fin_' + idSuffix).val()) || null
        };
        $.ajax({
          url: '${pageContext.request.contextPath}/scores/ajaxCalc',
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(payload),
          success: function(res) {
            $('#avg_' + idSuffix).val(res.averageScore == null ? '' : res.averageScore);
            $('#rate_' + idSuffix).val(res.academicRating == null ? '' : res.academicRating);
          }
        });
      });
    });
  }

  $(function(){
    // khởi tạo cho mỗi hàng
    <c:forEach var="sc" items="${studentsInClass}">
      setupCalcFor('${sc.studentClassID}');
    </c:forEach>
  });
</script>
