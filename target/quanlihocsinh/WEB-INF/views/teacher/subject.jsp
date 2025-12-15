<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Môn học tôi dạy</h2>

<ul>
    <c:forEach var="s" items="${subjects}">
        <li>
            <a href="${pageContext.request.contextPath}/teacher/subjects/${s.subjectID}/students">
                ${s.subjectName}
            </a>
        </li>
    </c:forEach>
</ul>
