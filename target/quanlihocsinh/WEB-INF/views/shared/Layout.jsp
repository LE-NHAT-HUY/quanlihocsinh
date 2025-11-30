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

    </aside>

    <!-- MAIN -->
    <div class="main">

        <!-- TOPBAR -->
        <header class="topbar">
            <div class="topbar-left">
            </div>

            <div class="search-box" style="position: relative; width: 260px;">
                <input type="text" class="form-control form-control-sm" placeholder="Tìm kiếm nhanh..." style="padding-left: 30px;">
                <i class="bi bi-search" style="position: absolute; left: 8px; top: 50%; transform: translateY(-50%); color: #6c757d;"></i>
            </div>

            <div class="topbar-right">
                <i class="bi bi-bell"></i>
                <div class="user-avatar"><i class="bi bi-person-fill"></i></div>
                <span class="user-name">
                    <c:out value="${sessionScope.adminName}" default="Admin" />
                </span>
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
