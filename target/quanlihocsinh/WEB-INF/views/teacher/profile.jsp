<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/student-profile.css" />

<div class="profile-page student-profile">
    <div class="header-nav mb-3 d-flex justify-content-end gap-1">
        <c:forEach var="i" begin="1" end="4">
            <button type="button" class="nav-btn" onclick="scrollToAndOpen(<c:out value='${i}'/>)">
                <c:out value="${i}"/>
            </button>
        </c:forEach>
        <button type="button" class="all-btn" onclick="toggleAll()">Tất cả</button>
    </div>

    <div class="accordion-list">
        <div id="section-1" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(1)">
                <span id="icon-1" class="rotate-icon rotate">▶</span>
                <span class="num-badge">1</span>
                <strong>Thông tin chung giáo viên</strong>
            </button>
            <div id="content-1" class="accordion-content active">
                <div class="profile-top">
                    <div class="profile-avatar">
                        <c:choose>
                            <c:when test="${not empty person and not empty person.images}">
                                <img src="${pageContext.request.contextPath}/assets/img/${person.images}" 
                                     onerror="this.src='https://congsv.vinhuni.edu.vn/assets/layout/images/male.png'" />
                            </c:when>
                            <c:otherwise>
                                <img src="https://congsv.vinhuni.edu.vn/assets/layout/images/male.png" />
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="profile-basic">
                        <div class="field-row small">
                            <div class="form-group">
                                <label>Mã giáo viên/Cán bộ</label>
                                <%-- Đã sửa personID thành personId để khớp với Model --%>
                                <div class="info-display">
                                    <c:out value="${person.personId != null ? person.personId : 'N/A'}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Họ và tên</label>
                                <div class="info-display"><c:out value="${person.fullName}" /></div>
                            </div>
                        </div>
                        <div class="field-row small">
                            <div class="form-group">
                                <label>Ngày sinh</label>
                                <div class="info-display">
                                    <fmt:formatDate value="${person.birth}" pattern="dd/MM/yyyy" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Giới tính</label>
                                <div class="info-display"><c:out value="${person.gender}" /></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="section-block mt-3">
                    <div class="field-row">
                        <div class="form-group">
                            <label>Dân tộc</label>
                            <div class="info-display">Kinh</div>
                        </div>
                        <div class="form-group">
                            <label>Tôn giáo</label>
                            <div class="info-display">Không</div>
                        </div>
                        <div class="form-group">
                            <label>Trình độ chuyên môn</label>
                            <div class="info-display">Đại học Sư phạm</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="section-2" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(2)">
                <span id="icon-2" class="rotate-icon">▶</span>
                <span class="num-badge">2</span>
                <strong>Thông tin liên hệ & Công tác</strong>
            </button>
            <div id="content-2" class="accordion-content">
                <div class="field-row">
                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <div class="info-display"><c:out value="${person.phone}" /></div>
                    </div>
                    <div class="form-group">
                        <label>Địa chỉ hiện nay</label>
                        <div class="info-display"><c:out value="${person.address}" /></div>
                    </div>
                </div>
            </div>
        </div>

        <div id="section-3" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(3)">
                <span id="icon-3" class="rotate-icon">▶</span>
                <span class="num-badge">3</span>
                <strong>Cập nhật hồ sơ cá nhân</strong>
            </button>
            <div id="content-3" class="accordion-content">
                <form action="${pageContext.request.contextPath}/user/updateProfile" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Họ và tên</label>
                            <input type="text" name="fullName" class="form-control" value="${person.fullName}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Số điện thoại</label>
                            <input type="text" name="phone" class="form-control" value="${person.phone}">
                        </div>
                        <div class="col-12 text-end">
                            <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="section-4" class="accordion-item shadow-sm">
            <button class="accordion-header" onclick="toggleAccordion(4)">
                <span id="icon-4" class="rotate-icon">▶</span>
                <span class="num-badge">4</span>
                <strong>Bảo mật tài khoản</strong>
            </button>
            <div id="content-4" class="accordion-content">
                <form class="row g-3">
                    <div class="col-12">
                        <label class="form-label">Mật khẩu mới</label>
                        <input type="password" class="password-field form-control" placeholder="Nhập mật khẩu mới">
                    </div>
                    <div class="col-12 text-end">
                        <button type="submit" class="btn btn-danger">Cập nhật mật khẩu</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function toggleAccordion(num) {
        var content = document.getElementById('content-' + num);
        var icon = document.getElementById('icon-' + num);
        if (!content || !icon) return;
        
        var isOpen = content.classList.contains('active');
        if (isOpen) {
            content.classList.remove('active');
            icon.classList.remove('rotate');
        } else {
            content.classList.add('active');
            icon.classList.add('rotate');
        }
    }

    function scrollToAndOpen(num) {
        var section = document.getElementById('section-' + num);
        var content = document.getElementById('content-' + num);
        var icon = document.getElementById('icon-' + num);
        
        if (content && section) {
            content.classList.add('active');
            if(icon) icon.classList.add('rotate');
            section.scrollIntoView({ behavior: 'smooth', block: 'start' });
            
            var navButtons = document.querySelectorAll('.nav-btn');
            for(var i = 0; i < navButtons.length; i++) {
                if ((i + 1) === num) navButtons[i].classList.add('active');
                else navButtons[i].classList.remove('active');
            }
        }
    }

    function toggleAll() {
        var contents = document.querySelectorAll('.accordion-content');
        var icons = document.querySelectorAll('.rotate-icon');
        var isAnyClosed = false;
        for (var i = 0; i < contents.length; i++) {
            if (!contents[i].classList.contains('active')) {
                isAnyClosed = true;
                break;
            }
        }
        for (var j = 0; j < contents.length; j++) {
            if (isAnyClosed) {
                contents[j].classList.add('active');
                if(icons[j]) icons[j].classList.add('rotate');
            } else {
                contents[j].classList.remove('active');
                if(icons[j]) icons[j].classList.remove('rotate');
            }
        }
    }
</script>