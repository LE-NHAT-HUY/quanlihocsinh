<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${pageTitle}" default="Quản lý học sinh"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css" />
</head>
<body>

<div class="layout">

    <!-- SIDEBAR -->
    <aside class="sidebar">
        <div class="brand">
            <div class="brand-title">
                <span>Trường THPT Anh Sơn I</span>
            </div>
        </div>

        <!-- Sidebar động từ menuList -->
        <ul class="sidebar-nav">
            <c:forEach var="menu" items="${menuList}">
                <li>
                    <a href="${pageContext.request.contextPath}${menu.controllerName}/${menu.actionName}" class="sidebar-link">
                        <i class="${menu.icon}"></i> ${menu.menuName}
                    </a>
                </li>
            </c:forEach>
        </ul>
        
        <!-- Logout button -->
        <div class="sidebar-footer mt-auto p-3">
        </div>
    </aside>

    <!-- MAIN -->
    <div class="main">

        <!-- TOPBAR -->
        <header class="topbar">
            <div class="topbar-left">
                <h5 class="mb-0"><c:out value="${pageTitle}" default="Trang chủ"/></h5>
            </div>

            <div class="search-box" style="position: relative; width: 260px;">
                <input type="text" class="form-control form-control-sm" placeholder="Tìm kiếm nhanh..." style="padding-left: 30px;">
                <i class="bi bi-search" style="position: absolute; left: 8px; top: 50%; transform: translateY(-50%); color: #6c757d;"></i>
            </div>

            <div class="topbar-right">
                <i class="bi bi-bell"></i>
                
                <!-- Avatar với hình ảnh nếu có -->
                <c:choose>
                    <c:when test="${not empty sessionScope.user.profile.images}">
                        <img src="${pageContext.request.contextPath}/${sessionScope.user.profile.images}" 
                             alt="Avatar" class="user-avatar-img rounded-circle" 
                             style="width: 32px; height: 32px; object-fit: cover;">
                    </c:when>
                    <c:otherwise>
                        <div class="user-avatar"><i class="bi bi-person-fill"></i></div>
                    </c:otherwise>
                </c:choose>
                
                <!-- Hiển thị tên người dùng -->
                <span class="user-name">
                    <c:choose>
                        <c:when test="${not empty sessionScope.user.profile.fullname}">
                            <c:out value="${sessionScope.user.profile.fullname}" />
                        </c:when>
                        <c:otherwise>
                            <c:out value="${sessionScope.user.username}" default="Người dùng" />
                        </c:otherwise>
                    </c:choose>
                </span>
                
                <!-- Dropdown menu -->
                <div class="dropdown">
                    <button class="btn btn-link dropdown-toggle p-0 ms-2" type="button" data-bs-toggle="dropdown">
                        <i class="bi bi-chevron-down"></i>
                    </button>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile">
                            <i class="bi bi-person"></i> Hồ sơ cá nhân
                        </a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/settings">
                            <i class="bi bi-gear"></i> Cài đặt
                        </a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                            <i class="bi bi-box-arrow-right"></i> Đăng xuất
                        </a></li>
                    </ul>
                </div>
            </div>
        </header>

        <!-- CONTENT -->
        <main class="content">
            <jsp:include page="${contentPage}" />
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>