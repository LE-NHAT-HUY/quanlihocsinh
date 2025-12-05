<jsp:include page="/WEB-INF/views/header.jsp" />

<h2>Chào ${sessionScope.user.username}</h2>

<c:if test="${not empty sessionScope.user.profile}">
    <p>Tên: ${sessionScope.user.profile.fullname}</p>
    <p>Loại: ${sessionScope.user.profile.personType}</p>
</c:if>
