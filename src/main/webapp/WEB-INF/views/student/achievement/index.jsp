<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid p-0">
    
    <div class="welcome-banner mb-4 p-4 rounded-3" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white;">
        <div class="row align-items-center">
            <div class="col-md-8">
                <h2 class="mb-1">
                    <i class="bi bi-trophy me-2"></i>Hồ sơ thành tích
                </h2>
                <p class="mb-0 opacity-75">Tổng hợp kết quả học tập qua các năm của <strong>${student.fullName}</strong>.</p>
            </div>
            <div class="col-md-4 text-end">
                <i class="bi bi-mortarboard fs-1 opacity-50"></i>
            </div>
        </div>
    </div>

    <section class="section">
        <c:if test="${empty achievementList}">
            <div class="alert alert-info text-center py-5">
                <i class="bi bi-inbox fs-1 mb-3 d-block"></i>
                Chưa có dữ liệu thành tích nào được ghi nhận.
            </div>
        </c:if>

        <c:forEach var="item" items="${achievementList}">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3 border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="mb-0 text-primary fw-bold">
                        <i class="bi bi-calendar-check me-2"></i>Năm học: ${item.schoolYear}
                    </h5>
                    <c:if test="${item.titleYear != 'Không có' && item.titleYear != 'Chưa xếp loại'}">
                        <span class="badge bg-warning text-dark border border-warning">
                            <i class="bi bi-star-fill me-1"></i>${item.titleYear}
                        </span>
                    </c:if>
                </div>
                
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-bordered mb-0 align-middle text-center">
                            <thead class="bg-light text-secondary small">
                                <tr>
                                    <th class="py-2" style="width: 33%;">HỌC KỲ 1</th>
                                    <th class="py-2" style="width: 33%;">HỌC KỲ 2</th>
                                    <th class="py-2" style="width: 34%;">CẢ NĂM</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="py-3">
                                        <div class="h3 fw-bold mb-0 ${item.avgHK1 >= 8 ? 'text-success' : item.avgHK1 >= 5 ? 'text-primary' : 'text-danger'}">
                                            ${item.avgHK1 > 0 ? item.avgHK1 : '--'}
                                        </div>
                                        <small class="text-muted d-block mb-2">Điểm TB</small>
                                        <span class="badge ${item.rankHK1 == 'Giỏi' ? 'bg-success' : item.rankHK1 == 'Khá' ? 'bg-primary' : 'bg-secondary'} rounded-pill">
                                            ${item.rankHK1}
                                        </span>
                                    </td>
                                    
                                    <td class="py-3">
                                        <div class="h3 fw-bold mb-0 ${item.avgHK2 >= 8 ? 'text-success' : item.avgHK2 >= 5 ? 'text-primary' : 'text-danger'}">
                                            ${item.avgHK2 > 0 ? item.avgHK2 : '--'}
                                        </div>
                                        <small class="text-muted d-block mb-2">Điểm TB</small>
                                        <c:if test="${item.avgHK2 > 0}">
                                            <span class="badge ${item.rankHK2 == 'Giỏi' ? 'bg-success' : item.rankHK2 == 'Khá' ? 'bg-primary' : 'bg-secondary'} rounded-pill">
                                                ${item.rankHK2}
                                            </span>
                                        </c:if>
                                        <c:if test="${item.avgHK2 == 0}">
                                            <span class="text-muted">-</span>
                                        </c:if>
                                    </td>
                                    
                                    <td class="py-3">
                                        <div class="h2 fw-bold mb-0 ${item.avgYear >= 8 ? 'text-success' : item.avgYear >= 5 ? 'text-primary' : 'text-danger'}">
                                            ${item.avgYear > 0 ? item.avgYear : '--'}
                                        </div>
                                        <small class="text-dark fw-bold d-block mb-2">ĐTB Chung</small>
                                        <span class="badge ${item.rankYear == 'Giỏi' ? 'bg-success' : item.rankYear == 'Khá' ? 'bg-primary' : 'bg-danger'} rounded-pill px-3 py-2">
                                            ${item.rankYear}
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>
        
        <div class="text-end text-muted small fst-italic mt-3">
            * Dữ liệu được tính toán dựa trên bảng điểm hiện có.
        </div>
    </section>
</div>