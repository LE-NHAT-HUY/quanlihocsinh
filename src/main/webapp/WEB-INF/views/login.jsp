<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập tài khoản</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />

    <!-- Bootstrap + Font Awesome -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #f8fafc, #e0f2fe);
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', system-ui, sans-serif;
        }

        .login-wrapper {
            display: flex;
            max-width: 900px;
            width: 100%;
            background: #ffffff;
            border-radius: 24px;
            box-shadow: 0 40px 90px rgba(0,0,0,.12);
            overflow: hidden;
            animation: fadeUp .8s ease;
        }

        @keyframes fadeUp {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* CỘT TRÁI */
        .login-left {
            flex: 1;
            background: linear-gradient(135deg, #2563eb, #22c55e);
            color: #fff;
            padding: 60px 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
        }

        .login-left img {
            width: 120px;
            height: auto;
            margin-bottom: 20px;
            border-radius: 0; /* bỏ bo góc */
            border: none; /* xóa khung */
        }

        .login-left h2 { font-weight: 700; margin-bottom: 15px; }
        .login-left p { opacity: .9; line-height: 1.6; }
        .login-left i { font-size: 64px; margin-bottom: 20px; opacity: .95; }

        /* CỘT PHẢI */
        .login-right { flex: 1; padding: 50px 45px; }
        .login-right h3 { font-weight: 700; margin-bottom: 25px; text-align: center; }

        .form-label { font-weight: 600; color: #334155; }
        .form-control {
            border-radius: 14px;
            padding: 14px;
            background: #f1f5f9;
            border: 2px solid transparent;
            transition: .3s;
        }
        .form-control:focus {
            background: #ffffff;
            border-color: #2563eb;
            box-shadow: none;
        }

        .btn-login {
            background: linear-gradient(135deg, #2563eb, #22c55e);
            border: none;
            border-radius: 16px;
            padding: 14px;
            font-weight: 600;
            transition: all .3s ease;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 18px 40px rgba(37,99,235,.35);
        }

        .error-text {
            background: #fee2e2;
            border-left: 4px solid #ef4444;
            color: #991b1b;
            padding: 12px;
            border-radius: 12px;
            font-size: 14px;
            margin-bottom: 15px;
        }

        .extra-links {
            text-align: center;
            margin-top: 15px;
        }
        .extra-links a {
            text-decoration: none;
            font-size: 14px;
            color: #ffffff;
        }
        .extra-links a:hover { text-decoration: underline; }

        @media (max-width: 768px) {
            .login-left { display: none; }
        }
    </style>
</head>

<body>
    <div class="login-wrapper">

        <!-- BÊN TRÁI -->
        <div class="login-left">
            <img src="${pageContext.request.contextPath}/assets/img/logo-truong-anh-son-1.png" alt="Logo Trường">
            <h2>Chào mừng quay lại</h2>
            <p>
                Chào mừng đến với THCS Anh Sơn I
            </p>
        </div>

        <!-- BÊN PHẢI -->
        <div class="login-right">
            <h3>Đăng nhập tài khoản</h3>

            <c:if test="${not empty error}">
                <div class="error-text">
                    <i class="fas fa-circle-exclamation"></i>
                    ${error}
                </div>
            </c:if>

            <form method="post" action="login">
                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" class="form-control" placeholder="Nhập tên đăng nhập" required />
                </div>

                <div class="mb-4">
                    <label class="form-label">Mật khẩu</label>
                    <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required />
                </div>

                <button type="submit" class="btn btn-login w-100 text-white">
                    <i class="fas fa-right-to-bracket"></i>
                    Đăng nhập
                </button>
            </form>

            <div class="extra-links">
                <a href="register">Chưa có tài khoản? Đăng ký ngay</a>
            </div>
        </div>

    </div>
</body>
</html>
