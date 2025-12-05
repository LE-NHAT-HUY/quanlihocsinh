<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav>
  <ul>
    <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
    <c:choose>
      <c:when test="${sessionScope.user.roleId == 1}">
        <li><a href="${pageContext.request.contextPath}/admin/home">Admin</a></li>
      </c:when>
      <c:when test="${sessionScope.user.roleId == 2}">
        <li><a href="${pageContext.request.contextPath}/teacher/home">Giáo viên</a></li>
      </c:when>
      <c:when test="${sessionScope.user.roleId == 3}">
        <li><a href="${pageContext.request.contextPath}/student/home">Học sinh</a></li>
      </c:when>
    </c:choose>
    <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
  </ul>
</nav>
