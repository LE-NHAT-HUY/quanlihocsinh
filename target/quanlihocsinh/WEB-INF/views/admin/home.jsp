<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/shared/_LayoutAdmin.jsp" %>

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Bảng điều khiển</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/admin/home">Trang chủ</a></li>
                <li class="breadcrumb-item active">Thống kê</li>
            </ol>
        </nav>
    </div>

    <section class="section dashboard">
        <div class="row">
            
            <div class="col-xxl-4 col-md-4">
                <div class="card info-card sales-card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng số học sinh</h5>
                        <div class="d-flex align-items-center">
                            <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                <i class="bi bi-people"></i>
                            </div>
                            <div class="ps-3">
                                <h6>${totalStudents}</h6>
                                <span class="text-success small pt-1 fw-bold">Hoạt động</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xxl-4 col-md-4">
                <div class="card info-card revenue-card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng số giáo viên</h5>
                        <div class="d-flex align-items-center">
                            <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                <i class="bi bi-person-badge"></i>
                            </div>
                            <div class="ps-3">
                                <h6>${totalTeachers}</h6>
                                <span class="text-primary small pt-1 fw-bold">Hoạt động</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-xxl-4 col-md-4">
                <div class="card info-card customers-card">
                    <div class="card-body">
                        <h5 class="card-title">Tổng số lớp học</h5>
                        <div class="d-flex align-items-center">
                            <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                <i class="bi bi-building"></i>
                            </div>
                            <div class="ps-3">
                                <h6>${totalClasses}</h6>
                                <span class="text-danger small pt-1 fw-bold">Lớp mở</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            
            <div class="col-12">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Thống kê sĩ số học sinh</h5>
                        <canvas id="classChart" style="max-height: 400px;"></canvas>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Tỷ lệ học lực</h5>
                        <div style="min-height: 350px; display: flex; align-items: center; justify-content: center;">
                            <canvas id="ratingChart" style="max-height: 300px;"></canvas>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Phổ điểm trung bình</h5>
                        <div style="min-height: 350px; display: flex; align-items: center; justify-content: center; width: 100%;">
                            <canvas id="scoreChart" style="max-height: 300px; width: 100%;"></canvas>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </section>
</main>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        
        // --- 1. CHUẨN BỊ DỮ LIỆU TỪ JAVA (JSP) ---
        // Sử dụng JSTL forEach để render mảng Javascript từ List Java

        // Dữ liệu Sĩ số lớp
        const classLabels = [
            <c:forEach items="${classData}" var="item">"${item.label}",</c:forEach>
        ];
        const classValues = [
            <c:forEach items="${classData}" var="item">${item.value},</c:forEach>
        ];

        // Dữ liệu Học lực
        const ratingLabels = [
            <c:forEach items="${ratingData}" var="item">"${item.label}",</c:forEach>
        ];
        const ratingValues = [
            <c:forEach items="${ratingData}" var="item">${item.value},</c:forEach>
        ];

        // Dữ liệu Phổ điểm
        const scoreLabels = [
            <c:forEach items="${scoreData}" var="item">"${item.label}",</c:forEach>
        ];
        const scoreValues = [
            <c:forEach items="${scoreData}" var="item">${item.value},</c:forEach>
        ];

        // --- 2. VẼ BIỂU ĐỒ ---

        // A. Biểu đồ Sĩ số lớp
        const ctxClass = document.querySelector('#classChart');
        if (ctxClass) {
            new Chart(ctxClass, {
                type: 'bar',
                data: {
                    labels: classLabels,
                    datasets: [{
                        label: 'Sĩ số',
                        data: classValues,
                        backgroundColor: 'rgba(65, 84, 241, 0.6)', // Màu xanh chủ đạo
                        borderColor: 'rgba(65, 84, 241, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        }

        // B. Biểu đồ Học lực (Doughnut)
        const ctxRating = document.querySelector('#ratingChart');
        if (ctxRating) {
            new Chart(ctxRating, {
                type: 'doughnut',
                data: {
                    labels: ratingLabels,
                    datasets: [{
                        data: ratingValues,
                        // Mảng màu tương ứng: Xanh lá, Xanh dương, Vàng, Đỏ, Xám
                        backgroundColor: ['#2eca6a', '#4154f1', '#ffca2c', '#dc3545', '#a0a0a0'],
                        hoverOffset: 4
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: { position: 'bottom' }
                    }
                }
            });
        }

        // C. Biểu đồ Phổ điểm
        const ctxScore = document.querySelector('#scoreChart');
        if (ctxScore) {
            new Chart(ctxScore, {
                type: 'bar',
                data: {
                    labels: scoreLabels,
                    datasets: [{
                        label: 'Số lượng học sinh',
                        data: scoreValues,
                        backgroundColor: 'rgba(255, 119, 29, 0.6)', // Màu cam
                        borderColor: 'rgba(255, 119, 29, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: { beginAtZero: true }
                    }
                }
            });
        }
    });
</script>