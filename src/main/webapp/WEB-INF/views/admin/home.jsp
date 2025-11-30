<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ quản trị - Quản lý học sinh</title>

    <!-- Bootstrap & Icons -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" />

    <style>
        :root {
            --primary: #1976d2;
            --primary-light: #e3f2fd;
            --sidebar-bg: #ffffff;
            --sidebar-border: #e0e0e0;
            --text-muted: #6c757d;
        }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background-color: #f5f7fb;
            color: #1f2933;
        }
        .layout {
            display: flex;
            min-height: 100vh;
        }

        /* SIDEBAR */
        .sidebar {
            width: 260px;
            background: var(--sidebar-bg);
            border-right: 1px solid var(--sidebar-border);
            display: flex;
            flex-direction: column;
            padding: 16px 12px;
            position: sticky;
            top: 0;
            height: 100vh;
        }
        .brand {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 24px;
            padding: 4px 10px;
        }
        .brand-logo {
            width: 36px;
            height: 36px;
            border-radius: 50%;
            background: linear-gradient(135deg, #1565c0, #42a5f5);
            display: flex;
            align-items: center;
            justify-content: center;
            color: #fff;
            font-weight: 700;
            font-size: 18px;
        }
        .brand-title {
            display: flex;
            flex-direction: column;
        }
        .brand-title span:first-child {
            font-weight: 700;
            font-size: 14px;
            text-transform: uppercase;
        }
        .brand-title span:last-child {
            font-size: 12px;
            color: var(--text-muted);
        }
        .sidebar-section-title {
            font-size: 11px;
            text-transform: uppercase;
            font-weight: 600;
            color: var(--text-muted);
            padding: 10px 10px 4px;
        }
        .sidebar-nav {
            list-style: none;
            padding: 0;
            margin: 0;
            flex: 1;
            overflow-y: auto;
        }
        .sidebar-link {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 8px 10px;
            border-radius: 8px;
            color: #374151;
            text-decoration: none;
            font-size: 14px;
        }
        .sidebar-link i {
            font-size: 16px;
            width: 18px;
            text-align: center;
        }
        .sidebar-link:hover {
            background: var(--primary-light);
            color: var(--primary);
        }
        .sidebar-link.active {
            background: var(--primary);
            color: #fff;
            font-weight: 600;
        }

        /* MAIN */
        .main {
            flex: 1;
            display: flex;
            flex-direction: column;
        }
        .topbar {
            height: 64px;
            background: #ffffff;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            align-items: center;
            padding: 0 24px;
            justify-content: space-between;
        }
        .topbar-left {
            display: flex;
            align-items: center;
            gap: 16px;
        }
        .current-config {
            font-size: 13px;
            color: var(--text-muted);
        }
        .search-box {
            position: relative;
            width: 260px;
        }
        .search-box input {
            width: 100%;
            padding-left: 32px;
            border-radius: 999px;
        }
        .search-box i {
            position: absolute;
            top: 50%;
            left: 10px;
            transform: translateY(-50%);
            color: var(--text-muted);
            font-size: 16px;
        }
        .topbar-right {
            display: flex;
            align-items: center;
            gap: 16px;
        }
        .user-avatar {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: #e3f2fd;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            color: var(--primary);
        }
        .user-name {
            font-size: 14px;
            font-weight: 500;
        }

        .content {
            padding: 20px 24px 24px;
        }
        .card-shadow {
            box-shadow: 0 1px 3px rgba(15, 23, 42, 0.08);
            border: none;
            border-radius: 12px;
        }
        .intro-img {
            border-radius: 12px;
            object-fit: cover;
            width: 100%;
            max-height: 200px;
        }
        .badge-chip {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 4px 8px;
            border-radius: 999px;
            background: #e3f2fd;
            color: #0d47a1;
            font-size: 11px;
        }
        .stat-value {
            font-size: 24px;
            font-weight: 700;
        }
        .stat-label {
            font-size: 12px;
            color: var(--text-muted);
        }
        .map-placeholder {
            width: 100%;
            border-radius: 12px;
            background: #e0f2f1;
            border: 1px solid #b2dfdb;
            padding: 12px;
            min-height: 220px;
            position: relative;
            overflow: hidden;
        }
        .map-placeholder-header {
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 14px;
        }
        .map-fake {
            width: 130%;
            height: 140px;
            border-radius: 10px;
            background: linear-gradient(135deg, #b3e5fc, #4fc3f7);
            position: relative;
            overflow: hidden;
        }
        .map-road {
            position: absolute;
            left: -10%;
            top: 50%;
            width: 120%;
            height: 32px;
            background: #90a4ae;
            transform: rotate(-6deg);
        }
        .map-marker {
            position: absolute;
            top: 30%;
            left: 45%;
            font-size: 22px;
            color: #d50000;
        }

        @media (max-width: 992px) {
            .layout {
                flex-direction: column;
            }
            .sidebar {
                position: relative;
                width: 100%;
                height: auto;
                flex-direction: row;
                overflow-x: auto;
            }
            .sidebar-nav {
                display: flex;
                flex-direction: row;
            }
            .sidebar-section-title {
                display: none;
            }
            .sidebar-link {
                white-space: nowrap;
            }
        }
    </style>
</head>
<body>

<div class="layout">

    <!-- SIDEBAR -->
    <aside class="sidebar">
        <div class="brand">
            <div class="brand-logo">AD</div>
            <div class="brand-title">
                <span>Quản trị hệ thống</span>
                <span>Quản lý học sinh</span>
            </div>
        </div>

        <div class="sidebar-section-title">Quản lý</div>
        <ul class="sidebar-nav">
            <li>
                <a href="${pageContext.request.contextPath}/admin/home" class="sidebar-link active">
                    <i class="bi bi-speedometer2"></i>Trang chủ
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/cohort" class="sidebar-link">
                    <i class="bi bi-calendar"></i>Khóa học
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/grade" class="sidebar-link">
                    <i class="bi bi-layers"></i>Khối
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/class" class="sidebar-link">
                    <i class="bi bi-building"></i>Lớp học
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/student" class="sidebar-link">
                    <i class="bi bi-people"></i>Học sinh
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/studentclass" class="sidebar-link">
                    <i class="bi bi-people-fill"></i>Học sinh - Lớp học
                </a>
            </li>
        </ul>

        <div class="sidebar-section-title">Môn học & Tổ chuyên môn</div>
        <ul class="sidebar-nav">
            <li>
                <a href="${pageContext.request.contextPath}/admin/department" class="sidebar-link">
                    <i class="bi bi-bookmark"></i>Bộ môn
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/subject" class="sidebar-link">
                    <i class="bi bi-book"></i>Môn học
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/teacher" class="sidebar-link">
                    <i class="bi bi-person-badge"></i>Giáo viên
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/yearsemester" class="sidebar-link">
                    <i class="bi bi-calendar-event"></i>Năm học - Học kỳ
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/menu" class="sidebar-link">
                    <i class="bi bi-list-ul"></i>Quản lý menu
                </a>
            </li>
        </ul>
    </aside>

    <!-- MAIN -->
    <div class="main">

        <!-- TOPBAR -->
        <header class="topbar">
            <div class="topbar-left">
                <div class="current-config">
                    Trang tổng quan hệ thống quản lý học sinh
                </div>
            </div>

            <div class="search-box">
                <input type="text" class="form-control form-control-sm" placeholder="Tìm kiếm nhanh..." />
                <i class="bi bi-search"></i>
            </div>

            <div class="topbar-right">
                <i class="bi bi-bell"></i>
                <div class="user-avatar">
                    <i class="bi bi-person-fill"></i>
                </div>
                <span class="user-name">
                    <c:out value="${sessionScope.adminName}" default="Admin" />
                </span>
            </div>
        </header>

        <!-- CONTENT -->
        <main class="content">
            <div class="row g-3">

                <!-- CỘT TRÁI: GIỚI THIỆU + BẢN ĐỒ -->
                <div class="col-lg-8">

                    <!-- Giới thiệu trường -->
                    <div class="card card-shadow mb-3">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <h5 class="card-title mb-0">Giới thiệu Trường THPT Anh Sơn I</h5>
                                <span class="badge-chip">
                                    <i class="bi bi-award-fill"></i> Trường chuẩn quốc gia
                                </span>
                            </div>

                            <div class="row mt-2">
                                <div class="col-md-7">
                                    <p class="mb-1">
                                        <strong>Gần 60 năm phát triển</strong>, nhà trường đã đào tạo nhiều thế hệ học sinh
                                        thành đạt, góp phần xây dựng quê hương Nghệ An.
                                    </p>
                                    <ul class="mb-2">
                                        <li>Tỷ lệ tốt nghiệp THPT trên 98% hằng năm.</li>
                                        <li>Nhiều học sinh đạt giải cấp tỉnh, cấp quốc gia.</li>
                                        <li>Cơ sở vật chất hiện đại, môi trường thân thiện.</li>
                                        <li>Đội ngũ giáo viên tâm huyết, chuyên môn cao.</li>
                                    </ul>
                                    <p class="mb-0 text-muted" style="font-size: 13px;">
                                        Tầm nhìn: Trở thành trường chuẩn quốc gia, tiên phong đổi mới giáo dục miền Trung.
                                    </p>
                                </div>
                                <div class="col-md-5">
                                    <img
                                        src="https://images.pexels.com/photos/256395/pexels-photo-256395.jpeg?auto=compress&cs=tinysrgb&w=800"
                                        alt="Trường THPT Anh Sơn I"
                                        class="intro-img" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bản đồ -->
                    <div class="card card-shadow">
                        <div class="card-body">
                            <h6 class="card-title mb-2">Vị trí trường trên bản đồ</h6>
                            <div class="map-placeholder">
                                <div class="map-placeholder-header">
                                    18°55'37.5"N 105°12'27.5"E — THPT Anh Sơn I, Nghệ An
                                </div>
                                <div class="map-fake mt-2 mb-2">
                                    <div class="map-road"></div>
                                    <div class="map-marker">
                                        <i class="bi bi-geo-alt-fill"></i>
                                    </div>
                                </div>
                                <small class="text-muted">
                                    (Có thể nhúng Google Map thật bằng &lt;iframe&gt; tại khu vực này.)
                                </small>
                            </div>
                        </div>
                    </div>

                </div>

                <!-- CỘT PHẢI: THỐNG KÊ + TỔ CHUYÊN MÔN -->
                <div class="col-lg-4">

                    <!-- Thống kê nhanh -->
                    <div class="card card-shadow mb-3">
                        <div class="card-body">
                            <h6 class="card-title mb-3">Thống kê nhanh</h6>
                            <div class="row g-2">
                                <div class="col-6">
                                    <div class="p-2 bg-white rounded-3 border">
                                        <div class="stat-value">
                                            <c:out value="${totalStudents}" default="--" />
                                        </div>
                                        <div class="stat-label">Học sinh</div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="p-2 bg-white rounded-3 border">
                                        <div class="stat-value">
                                            <c:out value="${totalTeachers}" default="--" />
                                        </div>
                                        <div class="stat-label">Giáo viên</div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="p-2 bg-white rounded-3 border">
                                        <div class="stat-value">
                                            <c:out value="${totalClasses}" default="--" />
                                        </div>
                                        <div class="stat-label">Lớp học</div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="p-2 bg-white rounded-3 border">
                                        <div class="stat-value">
                                            <c:out value="${totalSubjects}" default="--" />
                                        </div>
                                        <div class="stat-label">Môn học</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Tổ chuyên môn -->
                    <div class="card card-shadow">
                        <div class="card-body">
                            <h6 class="card-title mb-2">Thông tin tổ chuyên môn</h6>

                            <div class="mb-2">
                                <div class="fw-semibold">Ban giám hiệu</div>
                                <ul class="ps-3 mb-1" style="font-size: 13px;">
                                    <li>Ngô Đình Anh, SĐT: 0937 858 693</li>
                                    <li>Nguyễn Thanh Hằng, SĐT: 0986 557 575</li>
                                    <li>Nguyễn Thị Hương, SĐT: 0885 858 586</li>
                                </ul>
                            </div>

                            <div class="mb-2">
                                <div class="fw-semibold">Tổ Ngoại ngữ</div>
                                <ul class="ps-3 mb-1" style="font-size: 13px;">
                                    <li>Phạm Thế Báo, SĐT: 0382 737 481</li>
                                    <li>Nguyễn Thị Đào, SĐT: 0994 748 423</li>
                                </ul>
                            </div>

                            <div class="mb-2">
                                <div class="fw-semibold">Khoa học Xã hội</div>
                                <ul class="ps-3 mb-1" style="font-size: 13px;">
                                    <li>Nguyễn Thị Sương, SĐT: 0928 978 287</li>
                                </ul>
                            </div>

                            <div class="mb-2">
                                <div class="fw-semibold">Tổ Toán</div>
                                <ul class="ps-3 mb-1" style="font-size: 13px;">
                                    <li>Thành Công, SĐT: 0576 868 686</li>
                                </ul>
                            </div>

                            <div class="mb-2">
                                <div class="fw-semibold">Tổ Giáo dục chính trị</div>
                                <ul class="ps-3 mb-1" style="font-size: 13px;">
                                    <li>Nguyễn Thị Minh Anh, SĐT: 0585 959 584</li>
                                    <li>Nguyễn Văn Tiến, SĐT: 0986 546 574</li>
                                </ul>
                            </div>

                            <div>
                                <div class="fw-semibold">Tổ Giáo dục thể chất</div>
                                <ul class="ps-3 mb-0" style="font-size: 13px;">
                                    <li>Phạm Đình Toàn, SĐT: 0385 858 484</li>
                                </ul>
                            </div>

                        </div>
                    </div>

                </div>

            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>